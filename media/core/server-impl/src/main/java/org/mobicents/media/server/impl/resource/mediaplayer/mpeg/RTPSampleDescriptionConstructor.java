/*
 * JBoss, Home of Professional Open Source
 * Copyright XXXX, Red Hat Middleware LLC, and individual contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.media.server.impl.resource.mediaplayer.mpeg;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @author amit bhayani
 * 
 */
public class RTPSampleDescriptionConstructor extends RTPConstructor {

	public static final int TYPE = 3;

	private int trackRefIndex;
	private int length;
	private long sampleDescIndex;
	private long sampleDescOffset;
	private long reserved;

	public RTPSampleDescriptionConstructor() {
		super(TYPE);
	}

	@Override
	public int load(RandomAccessFile raAccFile) throws IOException {
		trackRefIndex = raAccFile.read();
		length = (raAccFile.read() << 8) | raAccFile.read();
		sampleDescIndex = ((long) (raAccFile.read() << 24 | raAccFile.read() << 16 | raAccFile.read() << 8 | raAccFile
				.read())) & 0xFFFFFFFFL;
		sampleDescOffset = ((long) (raAccFile.read() << 24 | raAccFile.read() << 16 | raAccFile.read() << 8 | raAccFile
				.read())) & 0xFFFFFFFFL;
		reserved = ((long) (raAccFile.read() << 24 | raAccFile.read() << 16 | raAccFile.read() << 8 | raAccFile
				.read())) & 0xFFFFFFFFL;
		return 16;
	}

	public int getTrackRefIndex() {
		return trackRefIndex;
	}

	public int getLength() {
		return length;
	}

	public long getSampleDescIndex() {
		return sampleDescIndex;
	}

	public long getSampleDescOffset() {
		return sampleDescOffset;
	}

	public long getReserved() {
		return reserved;
	}

}