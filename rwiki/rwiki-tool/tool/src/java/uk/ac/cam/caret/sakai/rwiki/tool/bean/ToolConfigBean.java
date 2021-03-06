/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/rwiki/tags/sakai-2.9.2/rwiki-tool/tool/src/java/uk/ac/cam/caret/sakai/rwiki/tool/bean/ToolConfigBean.java $
 * $Id: ToolConfigBean.java 84222 2010-11-03 13:15:52Z david.horwitz@uct.ac.za $
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

import java.util.Properties;

import org.sakaiproject.tool.api.Placement;

/**
 * This bean provides access to the tool configuration.
 * @author ieb
 *
 */
public class ToolConfigBean
{

	private Properties m_properties;
	private String defaultHomePageName;
	public ToolConfigBean(Placement currentPlacement, String defaultHomePageName)
	{
		m_properties = currentPlacement.getConfig();
		this.defaultHomePageName = defaultHomePageName;
	}
	
	public String getHomePage() {
		return m_properties.getProperty("home-page","home");
	}

	public String getHomePageName() {
		String friendlyName = m_properties.getProperty("home-page-friendly-name", defaultHomePageName);
		return (friendlyName == null || "".equals(friendlyName)) ? defaultHomePageName : friendlyName;
	}
	
}
