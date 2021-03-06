/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/kernel/tags/kernel-1.3.2/kernel-util/src/test/java/org/sakaiproject/util/PasswordCheckTest.java $
 * $Id: PasswordCheckTest.java 67352 2009-10-08 08:31:23Z david.horwitz@uct.ac.za $
 ***********************************************************************************
 *
 * Copyright (c) 2007, 2008 Sakai Foundation
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

package org.sakaiproject.util;

import junit.framework.TestCase;


/** 
 ** JUnit PasswordCheck test cases
 **/
public class PasswordCheckTest extends TestCase {

	/* for checking strength */
	private String passwordVeryWeak = "a";
	private String passwordWeak = "test";
	private String passwordMediocre = "IloveT3sts";
	private String passwordStrong = "IloveT3sts!";
	private String passwordVeryStrong = "I_r3a11y_loveT3sts!";
	
	/* for checking length */
	private String passwordShort = "test";
	private String passwordLong = "098f6bcd4621d373cade4e832627b4f6";
	private int zero=0;
	private int six=6;
	private int eight=8;
	private int thirty=30;
	
	/* general empty string */
	private String passwordEmpty = "";
	
	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testLengthPasswordZeroToThirty() {
		assertTrue(PasswordCheck.isAcceptableLength(passwordShort, zero, thirty));
	}
	
	public void testLengthLongPasswordZeroToEight() {
		assertFalse(PasswordCheck.isAcceptableLength(passwordLong, zero, eight));
	}
	
	public void testLengthShortPasswordSixToEight() {
		assertFalse(PasswordCheck.isAcceptableLength(passwordShort, six, eight));
	}
	
	public void testLengthLongPasswordSixToEight() {
		assertFalse(PasswordCheck.isAcceptableLength(passwordLong, six, eight));
	}
	
	public void testLengthEmptyPassword() {
		assertFalse(PasswordCheck.isAcceptableLength(passwordEmpty, zero, eight));
	}
	
	
	public void testStrengthVeryWeak() {
		assertEquals(PasswordCheck.getPasswordStrength(passwordVeryWeak), PasswordCheck.VERY_WEAK);
	}
	
	public void testStrengthWeak() {
		assertEquals(PasswordCheck.getPasswordStrength(passwordWeak), PasswordCheck.VERY_WEAK);
	}
	
	public void testStrengthMediocre() {
		assertEquals(PasswordCheck.getPasswordStrength(passwordMediocre), PasswordCheck.MEDIOCRE);
	}
		
	
	public void testStrengthStrong() {
		assertEquals(PasswordCheck.getPasswordStrength(passwordStrong), PasswordCheck.STRONG);
	}
	
	public void testStrengthVeryStrong() {
		assertEquals(PasswordCheck.getPasswordStrength(passwordVeryStrong), PasswordCheck.VERY_STRONG);
	}
	
	public void testStrengthNone() {
		assertEquals(PasswordCheck.getPasswordStrength(passwordEmpty), PasswordCheck.NONE);
	}
	
	
}
