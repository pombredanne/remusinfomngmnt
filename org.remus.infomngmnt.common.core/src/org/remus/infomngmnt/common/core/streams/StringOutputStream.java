package org.remus.infomngmnt.common.core.streams;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * Provides an OutputStream to an internal String. Internally converts bytes
 * to a Strings and stores them in an internal StringBuffer.
 */
public class StringOutputStream extends OutputStream implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5459677158049000629L;
	/**
	 * The internal destination StringBuffer.
	 */
	protected StringBuffer buf = null;

	/**
	 * Creates new StringOutputStream, makes a new internal StringBuffer.
	 */
	public StringOutputStream() {
		super();
		this.buf = new StringBuffer();
	}

	/**
	 * Returns the content of the internal StringBuffer as a String, the result
	 * of all writing to this OutputStream.
	 *
	 * @return returns the content of the internal StringBuffer
	 */
	@Override
	public String toString() {
		return this.buf.toString();
	}

	/**
	 * Sets the internal StringBuffer to null.
	 */
	@Override
	public void close() {
		this.buf = null;

	}

	/**
	 * Writes and appends byte array to StringOutputStream.
	 *
	 * @param b byte array
	 */
	@Override
	public void write(final byte[] b) {
		this.buf.append(CharUtil.toCharArray(b));
	}

	/**
	 * Writes and appends a byte array to StringOutputStream.
	 *
	 * @param b the byte array
	 * @param off the byte array starting index
	 * @param len the number of bytes from byte array to write to the stream
	 */
	@Override
	public void write(final byte[] b, int off, final int len) {
		if ((off < 0) || (len < 0) || (off + len) > b.length) {
			throw new IndexOutOfBoundsException("StringOutputStream.write: Parameters out of bounds.");
		}
		byte[] bytes = new byte[len];
		for (int i = 0; i < len; i++) {
			bytes[i] = b[off];
			off++;
		}
		this.buf.append(CharUtil.toCharArray(bytes));
	}

	/**
	 * Writes and appends a single byte to StringOutputStream.
	 *
	 * @param b the byte as an int to add
	 */
	@Override
	public void write(final int b) {
		this.buf.append((char)b);
	}
}
