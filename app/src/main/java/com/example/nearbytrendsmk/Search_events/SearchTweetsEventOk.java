package com.example.nearbytrendsmk.Search_events;


import com.example.nearbytrendsmk.Arraylist.TweetList;


public class SearchTweetsEventOk {

	public  TweetList tweetsList;

	public SearchTweetsEventOk(TweetList tweets) {
		this.tweetsList = tweets;
	}

	public TweetList getTweetsList() {
		return tweetsList;
	}
}
