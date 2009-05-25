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

package org.remus.infomngmnt.common.flags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FlagTransformer {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		String src = "C:\\Users\\tom\\Desktop\\plain";
		String target = "C:\\IDEs\\eclipse_remus_3_5_2\\eclipse\\workspace\\org.remus.infomngmnt.common.flags\\iconexperience\\flags\\";
		File file3 = new File("out.txt");
		if (file3.exists()) {
			file3.delete();
		}
		try {
			file3.createNewFile();
			System.setOut(new PrintStream(file3));
		} catch (Exception e1) {
			return;
		}
		List<Country> countries = new ArrayList<Country>();
		Locale[] locales = Locale.getAvailableLocales();
		List<String> isos = new ArrayList<String>();
		for (Locale locale : locales) {
			String iso = locale.getISO3Country();
			String code = locale.getCountry();
			String name = locale.getDisplayCountry();

			if (!"".equals(name) && !isos.contains(iso)) {
				if (name.equals("United States")) {
					countries.add(new Country(iso, code, "usa"));
				} else {
					countries.add(new Country(iso, code, name));
				}
				isos.add(iso);
			}
		}
		for (Country country : countries) {
			File file = new File(src);
			File[] listFiles = file.listFiles();
			boolean found = false;
			for (File file2 : listFiles) {
				if (file2.getName().equals(
						"flag_" + country.getName().toLowerCase().replaceAll(" ", "_") + ".png")) {
					try {
						copy(file2, new File(target + country.getIso() + ".png"));
						System.out.println("      <referenceResource\r\n"
								+ "            id=\"org.remus.infomngmnt.common.flags."
								+ country.getIso() + "\"\r\n"
								+ "            resource=\"iconexperience/flags/" + country.getIso()
								+ ".png\">\r\n" + "      </referenceResource>");
						found = true;
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (!found) {
				System.out.println("Not found" + country.getName());
			}

		}

	}

	public static void copy(final File inputFile, final File outputFile) throws IOException {
		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}
		final int BUFF_SIZE = 4096; // 5MB
		final byte[] buffer = new byte[BUFF_SIZE];
		InputStream in = new FileInputStream(inputFile);
		FileOutputStream out = new FileOutputStream(outputFile);

		while (true) {
			int count = in.read(buffer);
			if (count == -1) {
				break;
			}
			out.write(buffer, 0, count);
		}
		out.close();
		in.close();

		in.close();
		out.close();
	}

	public static class Country {
		private final String iso;

		private final String code;

		public String name;

		Country(final String iso, final String code, final String name) {
			this.iso = iso;
			this.code = code;
			this.name = name;
		}

		@Override
		public String toString() {
			return this.iso + " - " + this.code + " - " + this.name.toUpperCase();
		}

		/**
		 * @return the iso
		 */
		public String getIso() {
			return this.iso;
		}

		/**
		 * @return the code
		 */
		public String getCode() {
			return this.code;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}
	}

}
