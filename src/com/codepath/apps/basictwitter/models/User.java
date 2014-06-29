package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 782520282314155005L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String profileBackgroundImageUrl;
	private int numTweets;
	private int followersCount;
	private int friendsCount;
	private String tagline;

	public static User fromJSON(JSONObject json) {
		// TODO Auto-generated method stub
		User u = new User();

		try {
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImageUrl = json.getString("profile_image_url");
			u.profileBackgroundImageUrl = json
					.getString("profile_background_image_url");
			u.numTweets = json.getInt("statuses_count");
			u.followersCount = json.getInt("followers_count");
			u.friendsCount = json.getInt("friend_count");
			u.tagline = json.getString("description");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		// Return new object
		return u;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public int getNumTweets() {
		return numTweets;
	}

	public String getTagline() {
		return tagline;
	}

}
