/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/kernel/tags/kernel-1.3.2/kernel-impl/src/main/java/org/sakaiproject/thread_local/impl/ThreadLocalComponent.java $
 * $Id: ThreadLocalComponent.java 76914 2010-05-01 10:21:05Z david.horwitz@uct.ac.za $
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Sakai Foundation
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

package org.sakaiproject.thread_local.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.thread_local.api.ThreadBound;
import org.sakaiproject.thread_local.api.ThreadLocalManager;

/**
 * <p>
 * ThreadLocalComponent provides the standard implementation of the Sakai Framework ThreadLocalManager.
 * </p>
 * <p>
 * See the {@link org.sakaiproject.api.kernel.thread_local.ThreadLocalManager}interface for details.
 * </p>
 */
public class ThreadLocalComponent implements ThreadLocalManager
{
	/** Our log (commons). */
	private static Log M_log = LogFactory.getLog(ThreadLocalComponent.class);

	/**
	 * <p>
	 * ThreadBindings is a thread local map of keys to objects, holding the things bound to each thread.
	 * </p>
	 */
	protected class ThreadBindings extends ThreadLocal
	{
		public Object initialValue()
		{
			return new HashMap();
		}

		public Map getBindings()
		{
			return (Map) get();
		}
	}

	/** The bindings for each thread. */
	protected ThreadBindings m_bindings = new ThreadBindings();

	/**********************************************************************************************************************************************************************************************************************************************************
	 * Dependencies and their setter methods
	 *********************************************************************************************************************************************************************************************************************************************************/

	/**********************************************************************************************************************************************************************************************************************************************************
	 * Init and Destroy
	 *********************************************************************************************************************************************************************************************************************************************************/

	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init()
	{
		M_log.info("init()");
	}

	/**
	 * Final cleanup.
	 */
	public void destroy()
	{
		M_log.info("destroy()");
	}

	/**********************************************************************************************************************************************************************************************************************************************************
	 * Work interface methods: org.sakaiproject.api.kernel.thread_local.ThreadLocalManager
	 *********************************************************************************************************************************************************************************************************************************************************/

	/**
	 * {@inheritDoc}
	 */
	public void set(String name, Object value)
	{
		// find the map that might already exist
		Map bindings = m_bindings.getBindings();
		if (bindings == null)
		{
			M_log.warn("setInThread: no bindings!");
			return;
		}

		Object existing = bindings.get(name);
		if (existing instanceof ThreadBound)
		{
			if (!existing.equals(value))
			{
				unbind((ThreadBound) existing);
			}
		}

		// remove if nulling
		if (value == null)
		{
			bindings.remove(name);
		}

		// otherwise bind the object
		else
		{
			bindings.put(name, value);
		}
	}

	/**
	 * @param bound
	 */
	private void unbind(ThreadBound bound)
	{
		try
		{
			bound.unbind();
			M_log.debug("Unbound from ThreadLocal " + bound);
		}
		catch (Exception t)
		{
			M_log.error("Failed to unbind Object " + bound, t);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void clear()
	{
		Map bindings = m_bindings.getBindings();
		if (bindings == null)
		{
			M_log.warn("clear: no bindings!");
			return;
		}
		
		// unbind all objects that need it
		Object[] oa = bindings.values().toArray(); // make the code re-entrant
		for ( Object o : oa ) {
			if ( o instanceof ThreadBound ) {
				unbind((ThreadBound)o);
			}
		}

		// clear the bindings map associated with this thread
		bindings.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(String name)
	{
		Map bindings = m_bindings.getBindings();
		if (bindings == null)
		{
			M_log.warn("get: no bindings!");
			return null;
		}

		return bindings.get(name);
	}
}
