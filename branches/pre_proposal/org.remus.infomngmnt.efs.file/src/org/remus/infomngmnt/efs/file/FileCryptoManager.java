/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.efs.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A simple manager which creates derived master secrets and writes them to a
 * given file. In addition this class is able to en- and decrypt based on the
 * given key file streams.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FileCryptoManager {

	public static SecureRandom rand = new SecureRandom();

	private final static String CIPHER_TYPE = "AES/CBC/PKCS5Padding";

	private final static String CIPHER_KEY_TYPE = "AES";

	private static final String HASH_ALG = "SHA-1";

	private static final int KEY_LENGTH = 16;

	private static final String RNG_ALG = "SHA1PRNG";

	/**
	 * The salt used for symmetric key generation TODO: Set values
	 */
	private static final byte[] SALT = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	/**
	 * The initialization Vector for CBC Mode TODO: set Values
	 */
	private static final byte[] IV = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	private final File keyFile;

	private byte[] masterSecret;

	/**
	 * Creates a new Crypto-Manager based on the given file. If the file exists
	 * the key is restored, else a new key will be generated.
	 * 
	 * @param keyFile
	 *            the key File
	 * @throws Exception
	 *             if any exceotion occur
	 */
	public FileCryptoManager(final File keyFile) throws Exception {
		this.keyFile = keyFile;
		if (!keyFile.exists()) {
			if (!keyFile.getParentFile().exists()) {
				keyFile.getParentFile().mkdirs();
			}
			this.masterSecret = generateMasterSecret();
		} else {
			this.masterSecret = restoreMasterSecret();
		}

	}

	public OutputStream getEncryptedOutputStream(final File file) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(file);
		Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
		IvParameterSpec ivParamSpec = new IvParameterSpec(IV);
		SecretKeySpec sKeySpec = new SecretKeySpec(deriveKey(), CIPHER_KEY_TYPE);
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParamSpec);

		return new CipherOutputStream(fileOut, cipher);
	}

	public InputStream getDecryptedInputStream(final File file) throws Exception {

		FileInputStream fileIn = new FileInputStream(file);
		IvParameterSpec ivParamSpec = new IvParameterSpec(IV);
		SecretKeySpec sKeySpec = new SecretKeySpec(deriveKey(), CIPHER_KEY_TYPE);
		Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParamSpec);

		return new CipherInputStream(fileIn, cipher);
	}

	/**
	 * Generates a new master secret.
	 * 
	 * @return the new password in bytes[]
	 * @throws Exception
	 *             if any error occurs
	 */
	private byte[] generateMasterSecret() throws Exception {

		SecureRandom secRand = SecureRandom.getInstance(RNG_ALG);
		byte[] ms = new byte[KEY_LENGTH];
		secRand.nextBytes(ms);

		FileOutputStream keyOut = new FileOutputStream(this.keyFile.getAbsoluteFile());
		keyOut.write(ms);
		keyOut.close();

		return ms;
	}

	/**
	 * Simple method for reading the bytes of the password file.
	 * 
	 * @return the bytes
	 * @throws Exception
	 *             if reading fails.
	 */
	private byte[] restoreMasterSecret() throws Exception {

		FileInputStream keyIn = new FileInputStream(this.keyFile.getAbsoluteFile());
		byte[] tmp = new byte[keyIn.available()];
		keyIn.read(tmp);
		keyIn.close();

		return tmp;
	}

	/**
	 * Standard key-derivation
	 * 
	 * @return the derived key as byte-array
	 * @throws Exception
	 *             if any error occurs.
	 */
	private byte[] deriveKey() throws Exception {

		MessageDigest md = MessageDigest.getInstance(HASH_ALG);
		md.update(this.masterSecret);
		md.update(SALT);

		byte[] key = new byte[KEY_LENGTH];
		System.arraycopy(md.digest(), 0, key, 0, KEY_LENGTH);

		return key;
	}

}
