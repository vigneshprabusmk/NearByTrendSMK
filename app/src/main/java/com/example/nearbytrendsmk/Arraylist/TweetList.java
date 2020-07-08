package com.example.nearbytrendsmk.Arraylist;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class TweetList  {

	@SerializedName("statuses")
	public ArrayList<Tweet> tweets;

	public ArrayList<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(ArrayList<Tweet> tweets) {
		this.tweets = tweets;
	}


	public class Tweet {

		@SerializedName("created_at")
		public String dateCreated;

		@SerializedName("id")
		public String id;

		@SerializedName("text")
		public String text;

		@SerializedName("in_reply_to_status_id")
		public String inReplyToStatusId;

		@SerializedName("in_reply_to_user_id")
		public String inReplyToUserId;

		@SerializedName("in_reply_to_screen_name")
		public String inReplyToScreenName;

		@SerializedName("user")
		public TwitterUser user;


		@SerializedName("retweet_count")
		public int retweet_count;

		@Override
		public String toString(){
			return text;
		}

		public String getDateCreated() {
			return dateCreated;
		}

		public void setDateCreated(String dateCreated) {
			this.dateCreated = dateCreated;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getInReplyToStatusId() {
			return inReplyToStatusId;
		}

		public void setInReplyToStatusId(String inReplyToStatusId) {
			this.inReplyToStatusId = inReplyToStatusId;
		}

		public String getInReplyToUserId() {
			return inReplyToUserId;
		}

		public void setInReplyToUserId(String inReplyToUserId) {
			this.inReplyToUserId = inReplyToUserId;
		}

		public String getInReplyToScreenName() {
			return inReplyToScreenName;
		}

		public void setInReplyToScreenName(String inReplyToScreenName) {
			this.inReplyToScreenName = inReplyToScreenName;
		}

		public TwitterUser getUser() {
			return user;
		}

		public void setUser(TwitterUser user) {
			this.user = user;
		}

		public int getRetweet_count() {
			return retweet_count;
		}

		public void setRetweet_count(int retweet_count) {
			this.retweet_count = retweet_count;
		}

		public class TwitterUser {

			@SerializedName("name")
			public String name;
			@SerializedName("screen_name")
			public String screen_name;
			@SerializedName("description")
			public String description;
			@SerializedName("favourites_count")
			public int favourites_count;
			@SerializedName("statuses_count")
			public int statuses_count;
			@SerializedName("profile_image_url")
			public String profile_image_url;
			@SerializedName("profile_image_url_https")
			public String profile_image_url_https;
			@SerializedName("profile_banner_url")
			public String profile_banner_url;


			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getScreen_name() {
				return screen_name;
			}

			public void setScreen_name(String screen_name) {
				this.screen_name = screen_name;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public int getFavourites_count() {
				return favourites_count;
			}

			public void setFavourites_count(int favourites_count) {
				this.favourites_count = favourites_count;
			}

			public int getStatuses_count() {
				return statuses_count;
			}

			public void setStatuses_count(int statuses_count) {
				this.statuses_count = statuses_count;
			}

			public String getProfile_image_url() {
				return profile_image_url;
			}

			public void setProfile_image_url(String profile_image_url) {
				this.profile_image_url = profile_image_url;
			}

			public String getProfile_image_url_https() {
				return profile_image_url_https;
			}

			public void setProfile_image_url_https(String profile_image_url_https) {
				this.profile_image_url_https = profile_image_url_https;
			}

			public String getProfile_banner_url() {
				return profile_banner_url;
			}

			public void setProfile_banner_url(String profile_banner_url) {
				this.profile_banner_url = profile_banner_url;
			}
		}




	}





}
