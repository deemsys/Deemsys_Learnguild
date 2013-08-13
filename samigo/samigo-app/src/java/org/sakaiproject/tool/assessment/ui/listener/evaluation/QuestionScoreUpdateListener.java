/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/samigo-2.9.2/samigo-app/src/java/org/sakaiproject/tool/assessment/ui/listener/evaluation/QuestionScoreUpdateListener.java $
 * $Id: QuestionScoreUpdateListener.java 113421 2012-09-21 21:42:26Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2004, 2005, 2006, 2007, 2008, 2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/



package org.sakaiproject.tool.assessment.ui.listener.evaluation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math.util.MathUtils;
import org.sakaiproject.event.cover.EventTrackingService;
import org.sakaiproject.tool.assessment.data.dao.grading.ItemGradingData;
import org.sakaiproject.tool.assessment.data.ifc.grading.ItemGradingAttachmentIfc;
import org.sakaiproject.tool.assessment.facade.AgentFacade;
import org.sakaiproject.tool.assessment.services.GradebookServiceException;
import org.sakaiproject.tool.assessment.services.GradingService;
import org.sakaiproject.tool.assessment.ui.bean.evaluation.AgentResults;
import org.sakaiproject.tool.assessment.ui.bean.evaluation.QuestionScoresBean;
import org.sakaiproject.tool.assessment.ui.bean.evaluation.TotalScoresBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;
import org.sakaiproject.tool.assessment.util.TextFormat;

/**
 * <p>
 * This handles the updating of the Question Score page.
 *  </p>
 * <p>Description: Action Listener Evaluation Updating Question Score front door</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id: QuestionScoreUpdateListener.java 113421 2012-09-21 21:42:26Z ottenhoff@longsight.com $
 */

public class QuestionScoreUpdateListener
  implements ActionListener
{
  private static Log log = LogFactory.getLog(QuestionScoreUpdateListener.class);
  //private static EvaluationListenerUtil util;
  //private static BeanSort bs;
  //private static ContextUtil cu;

  /**
   * Standard process action method.
   * @param ae ActionEvent
   * @throws AbortProcessingException
   */
  public void processAction(ActionEvent ae) throws
    AbortProcessingException
  {
    log.debug("Question Score Update LISTENER.");
    QuestionScoresBean bean = (QuestionScoresBean) ContextUtil.lookupBean("questionScores");
    TotalScoresBean tbean = (TotalScoresBean) ContextUtil.lookupBean("totalScores");
    log.debug("Calling saveQuestionScores.");
    tbean.setAssessmentGradingHash(tbean.getPublishedAssessment().getPublishedAssessmentId());
    try{
      if (!saveQuestionScores(bean, tbean))
      {
        throw new RuntimeException("failed to call saveQuestionScores.");
      }
    } catch (GradebookServiceException ge) {
       FacesContext context = FacesContext.getCurrentInstance();
       String err=(String)ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AuthorMessages", "gradebook_exception_error");
       context.addMessage(null, new FacesMessage(err));

    }

  }

  /**
   * Persist the results from the ActionForm in the question page.
   * @param bean QuestionScoresBean bean
   * @return true if successful
   */
  public boolean saveQuestionScores(QuestionScoresBean bean, TotalScoresBean tbean)
  {
    try
    {
      GradingService delegate = new GradingService();
      //String publishedId = ContextUtil.lookupParam("publishedId");
      String itemId = ContextUtil.lookupParam("itemId");
      String which = ContextUtil.lookupParam("allSubmissions");
      if (which == null)
        which = "false";
      Collection agents = bean.getAgents();
      //ArrayList items = new ArrayList();
      Iterator iter = agents.iterator();
      while (iter.hasNext())
      {
        // each agent has a list of modified itemGrading
        AgentResults ar = (AgentResults) iter.next();
        // Get the itemgradingdata list for this result
        ArrayList datas = (ArrayList) bean.getScoresByItem().get
          (ar.getAssessmentGradingId() + ":" + itemId);
        if (datas == null)
          datas = new ArrayList();
        Iterator iter2 = datas.iterator();
        while (iter2.hasNext()){
          Object obj = iter2.next();
          //log.info("Data = " + obj);
          ItemGradingData data = (ItemGradingData) obj;

          // check if there is differnce in score, if so, update. Otherwise, do nothing
          float newAutoScore = (Float.valueOf(ar.getTotalAutoScore())).floatValue() / (float) datas.size();
          String newComments = TextFormat.convertPlaintextToFormattedTextNoHighUnicode(log, ar.getComments());
          ar.setComments(newComments);
          if (newComments!=null) {
        	  newComments = newComments.trim();
          }
          else {
        	  newComments = "";
          }

          float oldAutoScore = 0;
          if (data.getAutoScore() !=null)
            oldAutoScore=data.getAutoScore().floatValue();
          
          String oldComments = data.getComments();
          if (oldComments!=null) {
        	  oldComments = oldComments.trim();
          }
          else {
        	  oldComments = "";
          }
                    
          StringBuffer logString = new StringBuffer();
          logString.append("gradedBy=");
          logString.append(AgentFacade.getAgentString());
          logString.append(", itemGradingId=");
          logString.append(data.getItemGradingId());
          
          // if newAutoScore != oldAutoScore
          if (!(MathUtils.equalsIncludingNaN(newAutoScore , oldAutoScore, 0.0001))) {
        	data.setAutoScore(Float.valueOf(newAutoScore));
        	logString.append(", newAutoScore=");
            logString.append(newAutoScore);
            logString.append(", oldAutoScore=");
            logString.append(oldAutoScore);
          }
          if (!newComments.equals(oldComments)) {
            data.setComments(ar.getComments());
            logString.append(", newComments=");
            logString.append(newComments);
            logString.append(", oldComments=");
            logString.append(oldComments);
          }
          
          // if newAutoScore != oldAutoScore or newComments != oldComments
          if (!(MathUtils.equalsIncludingNaN(newAutoScore, oldAutoScore, 0.0001)) || !newComments.equals(oldComments)){
            data.setGradedBy(AgentFacade.getAgentString());
            data.setGradedDate(new Date());
            EventTrackingService.post(EventTrackingService.newEvent("sam.question.score.update", "siteId=" + AgentFacade.getCurrentSiteId() + ", " + logString.toString(), true));
            delegate.updateItemScore(data, newAutoScore-oldAutoScore, tbean.getPublishedAssessment());
          }
          
          updateAttachment(data, ar, bean);
        }
      }

    } catch (GradebookServiceException ge) {
       FacesContext context = FacesContext.getCurrentInstance();
       String err=(String)ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AuthorMessages", "gradebook_exception_error");
       context.addMessage(null, new FacesMessage(err));

    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private void updateAttachment(ItemGradingData itemGradingData, AgentResults agentResults, QuestionScoresBean bean){
	  List oldList = itemGradingData.getItemGradingAttachmentList();
	  List newList = agentResults.getItemGradingAttachmentList();
	  if ((oldList == null || oldList.size() == 0 ) && (newList == null || newList.size() == 0)) return;
	  List attachmentList = new ArrayList();
	  HashMap map = getAttachmentIdHash(oldList);
	  for (int i=0; i<newList.size(); i++){
		  ItemGradingAttachmentIfc itemGradingAttachment = (ItemGradingAttachmentIfc) newList.get(i);
		  if (map.get(itemGradingAttachment.getAttachmentId()) != null){
			  // exist already, remove it from map
			  map.remove(itemGradingAttachment.getAttachmentId());
		  }
		  else{
			  // new attachments
			  itemGradingAttachment.setItemGrading(itemGradingData);
			  attachmentList.add(itemGradingAttachment);
		  }
	  }      
	  // save new ones
	  GradingService gradingService = new GradingService();
	  if (attachmentList.size() > 0) {
			gradingService.saveOrUpdateAttachments(attachmentList);
			EventTrackingService.post(EventTrackingService.newEvent("sam.student.score.update", 
					"siteId=" + AgentFacade.getCurrentSiteId() + ", Adding " + attachmentList.size() + " attachments for itemGradingData id = " + itemGradingData.getItemGradingId(), 
					true));
		}

	  // remove old ones
	  Set set = map.keySet();
	  Iterator iter = set.iterator();
	  while (iter.hasNext()){
		  Long attachmentId = (Long)iter.next();
		  gradingService.removeItemGradingAttachment(attachmentId.toString());
		  EventTrackingService.post(EventTrackingService.newEvent("sam.student.score.update", 
				  "siteId=" + AgentFacade.getCurrentSiteId() + ", Removing attachmentId = " + attachmentId, true));
	  }
	  bean.setIsAnyItemGradingAttachmentListModified(true);
  }

  private HashMap getAttachmentIdHash(List list){
    HashMap map = new HashMap();
    for (int i=0; i<list.size(); i++){
    	ItemGradingAttachmentIfc a = (ItemGradingAttachmentIfc)list.get(i);
      map.put(a.getAttachmentId(), a);
    }
    return map;
  }
}