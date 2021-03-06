/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2014, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.mobicents.servlet.sip.testsuite.simple;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sip.SipProvider;
import javax.sip.header.ToHeader;

import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.log4j.Logger;
import org.mobicents.servlet.sip.SipServletTestCase;
import org.mobicents.servlet.sip.annotation.ConcurrencyControlMode;
import org.mobicents.servlet.sip.catalina.SipStandardManager;
import org.mobicents.servlet.sip.startup.SipContextConfig;
import org.mobicents.servlet.sip.startup.SipStandardContext;
import org.mobicents.servlet.sip.testsuite.ProtocolObjects;
import org.mobicents.servlet.sip.testsuite.TestSipListener;

public class ShootistTelURLSipServletTest extends SipServletTestCase {
	private static transient Logger logger = Logger.getLogger(ShootistTelURLSipServletTest.class);		
	private static final String TRANSPORT = "udp";
	private static final boolean AUTODIALOG = true;
	private static final int TIMEOUT = 10000;	
//	private static final int TIMEOUT = 100000000;
	
	TestSipListener receiver;
	
	ProtocolObjects receiverProtocolObjects;
	
	public ShootistTelURLSipServletTest(String name) {
		super(name);
		startTomcatOnStartup = false;
		autoDeployOnStartup = false;
	}

	@Override
	protected void deployApplication() {
		// TODO Auto-generated method stub
		
	}
	
	public void deployApplication(String urlType) {
		SipStandardContext context = new SipStandardContext();
		context.setDocBase(projectHome + "/sip-servlets-test-suite/applications/shootist-sip-servlet/src/main/sipapp");
		context.setName("sip-test-context");
		context.setPath("sip-test");
		context.addLifecycleListener(new SipContextConfig());
		context.setManager(new SipStandardManager());
		ApplicationParameter applicationParameter = new ApplicationParameter();
		applicationParameter.setName("urlType");
		applicationParameter.setValue(urlType);
		context.addApplicationParameter(applicationParameter);
		assertTrue(tomcat.deployContext(context));
	}
	
	public SipStandardContext deployApplication(String name, String value) {
        SipStandardContext context = new SipStandardContext();
        context.setDocBase(projectHome + "/sip-servlets-test-suite/applications/shootist-sip-servlet/src/main/sipapp");
        context.setName("sip-test-context");
        context.setPath("/sip-test");
        context.addLifecycleListener(new SipContextConfig());
        context.setManager(new SipStandardManager());
        context.setConcurrencyControlMode(ConcurrencyControlMode.SipApplicationSession);
        ApplicationParameter applicationParameter = new ApplicationParameter();
        applicationParameter.setName(name);
        applicationParameter.setValue(value);
        context.addApplicationParameter(applicationParameter);
        assertTrue(tomcat.deployContext(context));
        return context;
    }
    
    public SipStandardContext deployApplication(Map<String, String> params) {
        SipStandardContext context = new SipStandardContext();
        context.setDocBase(projectHome + "/sip-servlets-test-suite/applications/shootist-sip-servlet/src/main/sipapp");
        context.setName("sip-test-context");
        context.setPath("/sip-test");
        context.addLifecycleListener(new SipContextConfig());
        context.setConcurrencyControlMode(ConcurrencyControlMode.SipApplicationSession);
        context.setManager(new SipStandardManager());
        for (Entry<String, String> param : params.entrySet()) {
            ApplicationParameter applicationParameter = new ApplicationParameter();
            applicationParameter.setName(param.getKey());
            applicationParameter.setValue(param.getValue());
            context.addApplicationParameter(applicationParameter);
        }
        assertTrue(tomcat.deployContext(context));
        return context;
    }

	@Override
	protected String getDarConfigurationFile() {
		return "file:///" + projectHome + "/sip-servlets-test-suite/testsuite/src/test/resources/" +
				"org/mobicents/servlet/sip/testsuite/simple/shootist-sip-servlet-dar.properties";
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();						
		
		receiverProtocolObjects =new ProtocolObjects(
				"sender", "gov.nist", TRANSPORT, AUTODIALOG, null, null, null);
					
		receiver = new TestSipListener(5080, 5070, receiverProtocolObjects, false);
		SipProvider senderProvider = receiver.createProvider();			
		
		senderProvider.addSipListener(receiver);
		
		receiverProtocolObjects.start();			
	}
	
	public void testShootistTelURL() throws Exception {
//		receiver.sendInvite();
		tomcat.startTomcat();
		deployApplication("tel");
		Thread.sleep(TIMEOUT);
		assertTrue(receiver.getByeReceived());		
	}
	
	/*
	 * https://code.google.com/p/sipservlets/issues/detail?id=170
	 */
	public void testShootistTelURLFlagParameter() throws Exception {
//      receiver.sendInvite();
        tomcat.startTomcat();
        Map<String, String> params = new HashMap<String, String>();
        params.put("urlType", "tel");
        params.put("flag", "ndpi");
        deployApplication(params);
        Thread.sleep(TIMEOUT);
        assertEquals("tel:+358-555-1234567;ndpi", ((ToHeader)receiver.getInviteRequest().getHeader(ToHeader.NAME)).getAddress().getURI().toString());
        assertTrue(receiver.getByeReceived());      
    }
	
	public void testShootistTelURLAsSIP() throws Exception {
//		receiver.sendInvite();
		tomcat.startTomcat();
		deployApplication("telAsSip");
		Thread.sleep(TIMEOUT);
		assertTrue(receiver.getByeReceived());		
	}

	@Override
	protected void tearDown() throws Exception {					
		receiverProtocolObjects.destroy();			
		logger.info("Test completed");
		super.tearDown();
	}
}