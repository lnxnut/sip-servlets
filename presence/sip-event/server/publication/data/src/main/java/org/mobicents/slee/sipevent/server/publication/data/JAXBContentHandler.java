/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/**
 * 
 */
package org.mobicents.slee.sipevent.server.publication.data;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 * @author martins
 *
 */
public class JAXBContentHandler {

	private final JAXBContext jaxbContext;
	
	/**
	 * 
	 */
	public JAXBContentHandler(JAXBContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}
	
	public String marshallToString(JAXBElement<?> unmarshalledContent) {
		final StringWriter stringWriter = new StringWriter();
		try {
			jaxbContext.createMarshaller().marshal(unmarshalledContent, stringWriter);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
		return stringWriter.toString();
		// no need to close string writer, does nothing
	}
	
	public JAXBElement<?> unmarshallFromString(String marshalledContent) {
		try {
			return (JAXBElement<?>) jaxbContext.createUnmarshaller().unmarshal(new StringReader(marshalledContent));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null; 
		}
		// no need to close string reader, does nothing
	}
	
}
