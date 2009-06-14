package org.remus.infomngmnt.connector.twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

public class Snippet {

	public static void main(final String args[]) throws Exception {
		Twitter twitter = new Twitter();
		twitter.setOAuthConsumer("omhaenBOi32GmCWpIZCKQ",
				"T1ptIaPDBgnM1S2gtjZGZRSU9YhB4Er5fhJkFywsKm0");
		RequestToken requestToken = twitter.getOAuthRequestToken();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Open the following URL and grant access to your account:");
		System.out.println(requestToken.getAuthorizationURL());
		System.out.print("Hit enter when it's done.[Enter]:");
		br.readLine();
		try {

			AccessToken accessToken2 = twitter.getOAuthAccessToken(requestToken, "352482");
			// AccessToken accessToken2 = new
			// AccessToken(requestToken.getToken(), requestToken
			// .getTokenSecret());
			twitter.setOAuthAccessToken(accessToken2);
			twitter.setSource("Remus");
			twitter.updateStatus("TEst1234");
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				System.out.println("Unable to get the access token.");
			} else {
				te.printStackTrace();
			}
		}

		// persist to the accessToken for future reference.

		System.exit(0);
	}

	private static void storeAccessToken(final int useId, final AccessToken at) {
		// store at.getToken()
		// store at.getTokenSecret()
	}

}
