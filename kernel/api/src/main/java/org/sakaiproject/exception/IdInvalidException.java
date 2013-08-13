/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/kernel/tags/kernel-1.3.2/api/src/main/java/org/sakaiproject/exception/IdInvalidException.java $
 * $Id: IdInvalidException.java 51317 2008-08-24 04:38:02Z csev@umich.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 Sakai Foundation
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

package org.sakaiproject.exception;

/**
 * <p>
 * IdInvalidException is thrown whenever a resource id is not made up of a valid set of characters.
 * </p>
 * <p>
 * The invalid id is available as part of the exception.
 * </p>
 */
public class IdInvalidException extends Exception
{
	private String m_id = null;

	public IdInvalidException(String id)
	{
		m_id = id;
	}

	public String toString()
	{
		return super.toString() + " id=" + m_id;
	}

	public String getMessage()
	{
		return m_id;
	}
}