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

package org.remus.infomngmnt.efs.password;

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
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AESCryptoManager {

	public static SecureRandom rand = new SecureRandom();

	private final byte[] pass;

	private final static String CIPHER_TYPE = "AES/CBC/PKCS5Padding";

	private final static String CIPHER_KEY_TYPE = "AES";

	private static final String HASH_ALG = "SHA-1";

	private static final int KEY_LENGTH = 16;

	// The salt used for symmetric key generation
	private static final byte[] SALT = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	// The initialization Vector for CBC Mode
	private static final byte[] IV = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public AESCryptoManager(final byte[] pass) {
		this.pass = pass;
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
		Cipher cipher = Cipher.getInstance(CIPHER_KEY_TYPE);
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParamSpec);

		return new CipherInputStream(fileIn, cipher);
	}

	/*
	 * Derives the file specific session key from a supplied master secret
	 */
	private byte[] deriveKey() throws Exception {

		// The digest derives a key from the master secret and the salt
		MessageDigest md = MessageDigest.getInstance(HASH_ALG);
		md.update(this.pass);
		md.update(SALT);

		// The derived key may be too long so we copy only the needed part
		byte[] key = new byte[KEY_LENGTH];
		System.arraycopy(md.digest(), 0, key, 0, KEY_LENGTH);

		return key;
	}

}
