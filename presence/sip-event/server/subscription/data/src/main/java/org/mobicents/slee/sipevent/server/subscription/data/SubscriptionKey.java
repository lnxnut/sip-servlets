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

package org.mobicents.slee.sipevent.server.subscription.data;

import java.io.Serializable;

/**
 * 
 * @author eduardomartins
 *
 */
public class SubscriptionKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6638892043798746768L;
	
	public static final String NO_DIALOG_ID = "_";
	
    private final String dialogId;
    private final String eventPackage;
    private final String eventId;

    private transient Boolean internalSubscription;
    private transient Boolean wInfoSubscription;
    
    public SubscriptionKey(String dialogId, String eventPackage, String eventId) {
        if (dialogId == null) {
        	throw new NullPointerException("null dialog id");
        }
    	this.dialogId = dialogId;    	
    	this.eventPackage = eventPackage;
        this.eventId = eventId;
    }

    public String getDialogId() {
		return dialogId;
	}
    
    public String getEventId() {
    	return eventId;
	}
    
    public String getEventPackage() {
		return eventPackage;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dialogId == null) ? 0 : dialogId.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result
				+ ((eventPackage == null) ? 0 : eventPackage.hashCode());		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubscriptionKey other = (SubscriptionKey) obj;
		if (dialogId == null) {
			if (other.dialogId != null)
				return false;
		} else if (!dialogId.equals(other.dialogId))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (eventPackage == null) {
			if (other.eventPackage != null)
				return false;
		} else if (!eventPackage.equals(other.eventPackage))
			return false;		
		return true;
	}

	private transient String toString = null;
    public String toString() {
    	if (toString == null) {
    		toString = new StringBuilder("SubscriptionKeydialogId=").append(dialogId).append(",eventPackage=").append(eventPackage).append(",eventId=").append(String.valueOf(eventId)).toString();
    	}
        return toString; 
    }

    public boolean isInternalSubscription() {
    	if (internalSubscription == null) {
    		// no need to test both call id and remote tag
    		internalSubscription = Boolean.valueOf(dialogId.equals(SubscriptionKey.NO_DIALOG_ID));
    	}
    	return internalSubscription.booleanValue();
	}
    
    private static final String WINFO = ".winfo";
    
    public boolean isWInfoSubscription() {
    	if (wInfoSubscription == null) {
    		// no need to test both call id and remote tag
    		wInfoSubscription = Boolean.valueOf(eventPackage.endsWith(WINFO));
    	}
    	return wInfoSubscription.booleanValue();
	}
}