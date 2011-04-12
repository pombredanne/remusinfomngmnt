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

import java.awt.geom.Point2D;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.remus.core.services.IGeoData;
import org.remus.infomngmnt.geodata.GeoDataActivator;
import org.remus.infomngmnt.geodata.preferences.GeoDataPreferenceInitializer;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class GMapsApi implements IGeoData {

	public String getApiKey() {
		IPreferenceStore store = GeoDataActivator.getDefault()
				.getPreferenceStore();
		String string = store
				.getString(GeoDataPreferenceInitializer.GOOGLE_API_KEY);

		return string;
	}

	public String canRetreiveGeoData() {
		if (getApiKey() != null && getApiKey().trim().length() > 0) {
			return null;
		}
		return "You need an API Key from Google"; //$NON-NLS-1$
	}

	public Point2D getCoordinates(final Map<String, Object> values) {
		Point2D p2d = new Point2D.Float();

		String curSearchValue = values.get(KEY_LOCALITY)
				+ "+" + values.get(KEY_POST_OFFICE_BOX) //$NON-NLS-1$
				+ "+" + values.get(KEY_POST_CODE) + "+" + values.get(KEY_REGION) + "+" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ values.get(KEY_STREET);
		curSearchValue = curSearchValue.replace(" ", "+"); //$NON-NLS-1$ //$NON-NLS-2$
		String encode = curSearchValue;
		try {
			encode = URLEncoder.encode(curSearchValue, "UTF-8"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String curUrl = "http://maps.google.com/maps/geo?q=" + encode + "&output=xml&key=" //$NON-NLS-1$ //$NON-NLS-2$
				+ getApiKey();
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
			if (result.get(i).contains("<coordinates>")) { //$NON-NLS-1$
				String curCoData = result.get(i);

				String[] s1 = curCoData.split(">"); //$NON-NLS-1$
				for (int j = 0; j < s1.length; j++) {
					System.out.println(s1[j]);
					if (s1[j].contains(",")) { //$NON-NLS-1$
						String[] s2 = s1[j].split("<"); //$NON-NLS-1$
						for (int k = 0; k < s2.length; k++) {
							if (s2[k].contains(",")) { //$NON-NLS-1$
								String[] s3 = s2[k].split(","); //$NON-NLS-1$
								p2d.setLocation(Double.valueOf(s3[0]),
										Double.valueOf(s3[1]));
							}
						}
					}
				}
			}
		}
		return p2d;
	}
}
