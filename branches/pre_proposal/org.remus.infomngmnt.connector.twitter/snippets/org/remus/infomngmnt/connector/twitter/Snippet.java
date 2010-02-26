package org.remus.infomngmnt.connector.twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.http.AccessToken;

public class Snippet {

	public static void main(final String args[]) throws Exception {
		Twitter twitter = new Twitter("APITestNUE", "!Test123");
		List<Status> friends = twitter.getFriendsTimeline(new Paging(1, 1));
		System.out.println(friends.get(0).getRateLimitLimit());
		System.out.println(friends.get(0).getRateLimitRemaining());
		System.out.println(friends.get(0).getRateLimitReset());
		System.out.println(friends.get(0).getText());
		System.out.print("Hit enter when it's done.[Enter]:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		System.exit(0);
	}

	private static void storeAccessToken(final int useId, final AccessToken at) {
		// store at.getToken()
		// store at.getTokenSecret()
	}

}
