/*******************************************************************************
public boolean isDeletionWizard; * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.geodata.google;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.awt.geom.Point2D;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.remus.infomngmnt.geodata.GeoDataActivator;
import org.remus.infomngmnt.geodata.preferences.GeoDataPreferenceInitializer;

public class GMapsApi {

	public static String getApiKey() {
		IPreferenceStore store = GeoDataActivator.getDefault()
				.getPreferenceStore();
		String string = store
				.getString(GeoDataPreferenceInitializer.GOOGLE_API_KEY);

		return string;
	}

	public static Point2D getGeoCoordFromGMaps(final String gMapsApiKey,
			final String searchValue) {

		Point2D p2d = new Point2D.Float();

		String curUrl = "http://maps.google.com/maps/geo?q=" + searchValue
				+ "&output=xml&key=" + gMapsApiKey;
		List<String> result = new ArrayList<String>();

		URL u;
		try {
			u = new URL(curUrl);
			DataInputStream theHTML = new DataInputStream(u.openStream());
			String thisLine;
			while ((thisLine = theHTML.readLine()) != null) {
				result.add(thisLine);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).contains("<coordinates>")) {
				String curCoData = result.get(i);

				String[] s1 = curCoData.split(">");
				for (int j = 0; j < s1.length; j++) {
					System.out.println(s1[j]);
					if (s1[j].contains(",")) {
						String[] s2 = s1[j].split("<");
						for (int k = 0; k < s2.length; k++) {
							if (s2[k].contains(",")) {
								String[] s3 = s2[k].split(",");
								p2d.setLocation(Double.valueOf(s3[0]), Double
										.valueOf(s3[1]));
							}
						}
					}
				}
			}
		}
		return p2d;
	}
}
