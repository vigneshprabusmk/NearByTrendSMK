package com.example.nearbytrendsmk.Search_events;


public class SearchTweetsEvent {

	public final String hashtag,geolocation;
	public final String twitterToken;

	public SearchTweetsEvent(String twitterToken, String hashtag, String geolocation) {
		this.hashtag = hashtag;
		this.geolocation = geolocation;
		this.twitterToken = twitterToken;
	}


}
