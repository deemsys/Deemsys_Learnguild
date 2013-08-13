/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/kernel/tags/kernel-1.3.2/test-harness/src/main/java/org/sakaiproject/test/SakaiTestTimer.java $
 * $Id: SakaiTestTimer.java 59688 2009-04-03 23:45:02Z arwhyte@umich.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2008 The Sakai Foundation
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
package org.sakaiproject.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:jholtzman@berkeley.edu">Josh Holtzman</a>
 * 
 */
public class SakaiTestTimer {
	private static final Log log = LogFactory.getLog(SakaiTestTimer.class);
	private String task;
	private long start;

	/**
	 * Create a new SakaiTestTimer
	 * 
	 * @param task
	 *            The name of the task being timed
	 */
	public SakaiTestTimer(String task) {
		this.task = task;
		restart();
	}

	public void restart() {
		start = System.currentTimeMillis();
	}

	/**
	 * Log the elapsed time since this was created or reset.
	 */
	public void logTimeElapsed() {
		log.debug(task + ": " + (System.currentTimeMillis() - start) + " ms");
	}
}