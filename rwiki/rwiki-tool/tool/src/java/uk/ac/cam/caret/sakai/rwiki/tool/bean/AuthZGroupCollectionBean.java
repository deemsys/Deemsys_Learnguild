/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/rwiki/tags/sakai-2.9.2/rwiki-tool/tool/src/java/uk/ac/cam/caret/sakai/rwiki/tool/bean/AuthZGroupCollectionBean.java $
 * $Id: AuthZGroupCollectionBean.java 9108 2006-05-08 14:30:57Z ian@caret.cam.ac.uk $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006 The Sakai Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package uk.ac.cam.caret.sakai.rwiki.tool.bean;

import java.util.ArrayList;
import java.util.List;

public class AuthZGroupCollectionBean
{

	private ViewBean vb;

	private List currentRealms;

	public List getRealms()
	{
		if (currentRealms == null)
		{
			return new ArrayList();
		}
		return currentRealms;
	}

	public void setCurrentRealms(List currentRealms)
	{
		this.currentRealms = currentRealms;
	}

	public void setVb(ViewBean vb)
	{
		this.vb = vb;
	}

}
