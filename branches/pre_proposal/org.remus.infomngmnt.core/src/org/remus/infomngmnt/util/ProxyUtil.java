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

package org.remus.infomngmnt.util;

import java.net.URI;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;

import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ProxyUtil {

	public static Proxy getProxyByUrl(final String url) {
		Proxy proxy = null;
		try {
			IProxyService proxyService = InfomngmntEditPlugin.getPlugin().getProxyService();
			// Only do this if platform service exists
			if (proxyService != null && proxyService.isProxiesEnabled()) {
				// Setup via proxyService entry
				URI target = new URI(url);
				final IProxyData[] proxies = proxyService.select(target);
				IProxyData selectedProxy = selectProxyFromProxies(target.getScheme(), proxies);
				if (selectedProxy != null) {
					proxy = new Proxy(((selectedProxy.getType()
							.equalsIgnoreCase(IProxyData.SOCKS_PROXY_TYPE)) ? Proxy.Type.SOCKS
							: Proxy.Type.HTTP), new ProxyAddress(selectedProxy.getHost(),
							selectedProxy.getPort()), selectedProxy.getUserId(), selectedProxy
							.getPassword());
				}
			}
		} catch (Exception e) {
			// If we don't even have the classes for this (i.e. the
			// org.eclipse.core.net plugin not available)
			// then we simply log and ignore
			// Activator.logNoProxyWarning(e);
		}
		return proxy;
	}

	/**
	 * Select a single proxy from a set of proxies available for the given host.
	 * This implementation selects in the following manner: 1) If proxies
	 * provided is null or array of 0 length, null is returned. If only one
	 * proxy is available (array of length 1) then the entry is returned. If
	 * proxies provided is length > 1, then if the type of a proxy in the array
	 * matches the given protocol (e.g. http, https), then the first matching
	 * proxy is returned. If the protocol does not match any of the proxies,
	 * then the *first* proxy (i.e. proxies[0]) is returned. Subclasses may
	 * override if desired.
	 * 
	 * @param protocol
	 *            the target protocol (e.g. http, https, scp, etc). Will not be
	 *            <code>null</code>.
	 * @param proxies
	 *            the proxies to select from. May be <code>null</code> or array
	 *            of length 0.
	 * @return proxy data selected from the proxies provided.
	 */
	private static IProxyData selectProxyFromProxies(final String protocol,
			final IProxyData[] proxies) {
		if (proxies == null || proxies.length == 0) {
			return null;
		}
		// If only one proxy is available, then use that
		if (proxies.length == 1) {
			return proxies[0];
		}
		// If more than one proxy is available, then if http/https protocol then
		// look for that
		// one...if not found then use first
		if (protocol.equalsIgnoreCase("http")) { //$NON-NLS-1$
			for (int i = 0; i < proxies.length; i++) {
				if (proxies[i].getType().equals(IProxyData.HTTP_PROXY_TYPE)) {
					return proxies[i];
				}
			}
		} else if (protocol.equalsIgnoreCase("https")) { //$NON-NLS-1$
			for (int i = 0; i < proxies.length; i++) {
				if (proxies[i].getType().equals(IProxyData.HTTPS_PROXY_TYPE)) {
					return proxies[i];
				}
			}
		}
		// If we haven't found it yet, then return the first one.
		return proxies[0];
	}

}
