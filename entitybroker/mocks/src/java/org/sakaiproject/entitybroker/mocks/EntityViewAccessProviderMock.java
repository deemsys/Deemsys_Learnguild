/**
 * $Id: EntityViewAccessProviderMock.java 59674 2009-04-03 23:05:58Z arwhyte@umich.edu $
 * $URL: https://source.sakaiproject.org/svn/entitybroker/tags/entitybroker-1.5.2/mocks/src/java/org/sakaiproject/entitybroker/mocks/EntityViewAccessProviderMock.java $
 * EntityViewAccessProviderMock.java - entity-broker - Apr 11, 2008 4:31:51 PM - azeckoski
 **************************************************************************
 * Copyright (c) 2008, 2009 The Sakai Foundation
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
 */

package org.sakaiproject.entitybroker.mocks;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sakaiproject.entitybroker.EntityView;
import org.sakaiproject.entitybroker.access.AccessFormats;
import org.sakaiproject.entitybroker.access.EntityViewAccessProvider;
import org.sakaiproject.entitybroker.entityprovider.extension.Formats;
import org.sakaiproject.entitybroker.exception.FormatUnsupportedException;
import org.sakaiproject.entitybroker.util.http.EntityHttpServletResponse;

/**
 * Pretends to be an access servlet provider for things that use them,
 * will only handle HTML and RSS formats, dies for all others,
 * this is an access formatter (RSS,HTML)
 * 
 * @author Aaron Zeckoski (aaron@caret.cam.ac.uk)
 */
public class EntityViewAccessProviderMock implements EntityViewAccessProvider, AccessFormats {

    private String prefix = null;
    public EntityViewAccessProviderMock(String prefix) {
        this.prefix = prefix;
    }

    /* (non-Javadoc)
     * @see org.sakaiproject.entitybroker.access.EntityViewAccessProvider#handleAccess(org.sakaiproject.entitybroker.EntityView, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void handleAccess(EntityView view, HttpServletRequest req, HttpServletResponse res) {
        String format = view.getFormat();
        if (format.equals(Formats.HTML) || format.equals(Formats.RSS)) {
            // Okey dokey, do nothing but say all is well
            try {
                res.getWriter().print(prefix + ": EntityViewAccessProviderMock");
            } catch (IOException e) {
                // nothing to do here
                e.printStackTrace();
            }
            ((EntityHttpServletResponse) res).setStatus(HttpServletResponse.SC_OK);
        } else {
            throw new FormatUnsupportedException("No support for format: " + format, view.getEntityReference()+"", format);
        }
    }

    public String[] getHandledAccessFormats() {
        return new String[] {Formats.HTML, Formats.RSS, Formats.ATOM};
    }

}
