/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/search/tags/search-1.4.2/search-impl/impl/src/test/org/sakaiproject/search/mock/MockSecurityService.java $
 * $Id: MockSecurityService.java 122224 2013-04-04 21:50:53Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.search.mock;

import java.util.Collection;
import java.util.List;

import org.sakaiproject.authz.api.SecurityAdvisor;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.user.api.User;

/**
 * @author ieb
 *
 */
public class MockSecurityService implements SecurityService
{

	/**
	 * 
	 */
	public MockSecurityService()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#clearAdvisors()
	 */
	public void clearAdvisors()
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#hasAdvisors()
	 */
	public boolean hasAdvisors()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#isSuperUser()
	 */
	public boolean isSuperUser()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#isSuperUser(java.lang.String)
	 */
	public boolean isSuperUser(String userId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#popAdvisor()
	 */
	public SecurityAdvisor popAdvisor()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#pushAdvisor(org.sakaiproject.authz.api.SecurityAdvisor)
	 */
	public void pushAdvisor(SecurityAdvisor advisor)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#unlock(java.lang.String, java.lang.String)
	 */
	public boolean unlock(String lock, String reference)
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#unlock(org.sakaiproject.user.api.User, java.lang.String, java.lang.String)
	 */
	public boolean unlock(User user, String lock, String reference)
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#unlock(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean unlock(String userId, String lock, String reference)
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#unlock(java.lang.String, java.lang.String, java.lang.String, java.util.Collection)
	 */
	public boolean unlock(String userId, String lock, String reference,
			Collection<String> authzGroupIds)
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sakaiproject.authz.api.SecurityService#unlockUsers(java.lang.String, java.lang.String)
	 */
	public List unlockUsers(String lock, String reference)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void clearUserEffectiveRole(String arg0) {
		return;
	}

	public void clearUserEffectiveRoles() {
		return;
	}

	public String getUserEffectiveRole(String arg0) {
		return null;
	}

	public boolean setUserEffectiveRole(String arg0, String arg1) {
		return false;
	}

	public SecurityAdvisor popAdvisor(SecurityAdvisor advisor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Session clearUserEffectiveRolesBySession(String sessionId) {
	    // TODO Auto-generated method stub
	    return null;
	}

}
