/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.remus.infomngmnt.birtreport.extension;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Adapts a <code>Reader</code> as an <code>InputStream</code>. Adapted from
 * <CODE>StringInputStream</CODE>.
 * 
 */
public class ReaderInputStream extends InputStream {

	/** Source Reader */
	private Reader in;

	private String encoding = System.getProperty("file.encoding"); //$NON-NLS-1$

	private byte[] slack;

	private int begin;

	/**
	 * Construct a <CODE>ReaderInputStream</CODE> for the specified
	 * <CODE>Reader</CODE>.
	 * 
	 * @param reader
	 *            <CODE>Reader</CODE>. Must not be <code>null</code>.
	 */
	public ReaderInputStream(final Reader reader) {
		this.in = reader;
	}

	/**
	 * Construct a <CODE>ReaderInputStream</CODE> for the specified
	 * <CODE>Reader</CODE>, with the specified encoding.
	 * 
	 * @param reader
	 *            non-null <CODE>Reader</CODE>.
	 * @param encoding
	 *            non-null <CODE>String</CODE> encoding.
	 */
	public ReaderInputStream(final Reader reader, final String encoding) {
		this(reader);
		if (encoding == null) {
			throw new IllegalArgumentException("encoding must not be null"); //$NON-NLS-1$
		} else {
			this.encoding = encoding;
		}
	}

	/**
	 * Reads from the <CODE>Reader</CODE>, returning the same value.
	 * 
	 * @return the value of the next character in the <CODE>Reader</CODE>.
	 * 
	 * @exception IOException
	 *                if the original <code>Reader</code> fails to be read
	 */
	@Override
	public synchronized int read() throws IOException {
		if (this.in == null) {
			throw new IOException("Stream Closed"); //$NON-NLS-1$
		}

		byte result;
		if (this.slack != null && this.begin < this.slack.length) {
			result = this.slack[this.begin];
			if (++this.begin == this.slack.length) {
				this.slack = null;
			}
		} else {
			byte[] buf = new byte[1];
			if (read(buf, 0, 1) <= 0) {
				result = -1;
			}
			result = buf[0];
		}

		if (result < -1) {
			result += 256;
		}

		return result;
	}

	/**
	 * Reads from the <code>Reader</code> into a byte array
	 * 
	 * @param b
	 *            the byte array to read into
	 * @param off
	 *            the offset in the byte array
	 * @param len
	 *            the length in the byte array to fill
	 * @return the actual number read into the byte array, -1 at the end of the
	 *         stream
	 * @exception IOException
	 *                if an error occurs
	 */
	@Override
	public synchronized int read(final byte[] b, final int off, int len)
			throws IOException {
		if (this.in == null) {
			throw new IOException("Stream Closed"); //$NON-NLS-1$
		}

		while (this.slack == null) {
			char[] buf = new char[len]; // might read too much
			int n = this.in.read(buf);
			if (n == -1) {
				return -1;
			}
			if (n > 0) {
				this.slack = new String(buf, 0, n).getBytes(this.encoding);
				this.begin = 0;
			}
		}

		if (len > this.slack.length - this.begin) {
			len = this.slack.length - this.begin;
		}

		System.arraycopy(this.slack, this.begin, b, off, len);

		if ((this.begin += len) >= this.slack.length) {
			this.slack = null;
		}

		return len;
	}

	/**
	 * Marks the read limit of the StringReader.
	 * 
	 * @param limit
	 *            the maximum limit of bytes that can be read before the mark
	 *            position becomes invalid
	 */
	@Override
	public synchronized void mark(final int limit) {
		try {
			this.in.mark(limit);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe.getMessage());
		}
	}

	/**
	 * @return the current number of bytes ready for reading
	 * @exception IOException
	 *                if an error occurs
	 */
	@Override
	public synchronized int available() throws IOException {
		if (this.in == null) {
			throw new IOException("Stream Closed"); //$NON-NLS-1$
		}
		if (this.slack != null) {
			return this.slack.length - this.begin;
		}
		if (this.in.ready()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * @return false - mark is not supported
	 */
	@Override
	public boolean markSupported() {
		return false; // would be imprecise
	}

	/**
	 * Resets the StringReader.
	 * 
	 * @exception IOException
	 *                if the StringReader fails to be reset
	 */
	@Override
	public synchronized void reset() throws IOException {
		if (this.in == null) {
			throw new IOException("Stream Closed"); //$NON-NLS-1$
		}
		this.slack = null;
		this.in.reset();
	}

	/**
	 * Closes the Stringreader.
	 * 
	 * @exception IOException
	 *                if the original StringReader fails to be closed
	 */
	@Override
	public synchronized void close() throws IOException {
		if (this.in != null) {
			this.in.close();
			this.slack = null;
			this.in = null;
		}
	}
}
