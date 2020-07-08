package com.example.nearbytrendsmk.API_interfaces;

import com.example.nearbytrendsmk.Arraylist.TweetList;
import com.example.nearbytrendsmk.Arraylist.TwitterTokenType;

import retrofit.Callback;
import retrofit.http.*;


public interface TwitterApiService {
	@GET(ApiConstants.TWITTER_HASHTAG_SEARCH_CODE )
	void getTweetList(
            @Header("Authorization") String authorization,
            @Query("q") String hashtag, @Query("geocode") String geocode,
            Callback<TweetList> callback
    );

	@FormUrlEncoded
	@POST("/oauth2/token")
	void getToken(
            @Header("Authorization") String authorization,
            @Field("grant_type") String grantType,
            Callback<TwitterTokenType> response
    );
}

