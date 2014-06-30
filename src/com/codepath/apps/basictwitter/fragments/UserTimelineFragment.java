package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

import com.codepath.apps.basictwitter.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	/*
	 * private TwitterClient client;
	 * 
	 * public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); client =
	 * TwitterApplication.getRestClient(); populateUserTimeline(); };
	 * 
	 * public void populateUserTimeline() { if (client != null) {
	 * client.getUserTimeline(new JsonHttpResponseHandler() {
	 * 
	 * @Override public void onSuccess(JSONArray json) { ArrayList<Tweet> tweets
	 * = Tweet.fromJSONArray(json); // setMaxId(tweets); addAll(tweets); }
	 * 
	 * @Override public void onFailure(Throwable e, String s) { // TODO
	 * Auto-generated method stub Log.d("debug", e.toString()); Log.d("debug",
	 * s.toString()); } }); } }
	 */
	private long user_id;

	public static UserTimelineFragment newInstance(long user_id) {
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putLong("user_id", user_id);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user_id = getArguments().getLong("user_id", 0);
	}

	public void sendClientRequest(TwitterClient client, long since_id,
			long max_id, JsonHttpResponseHandler handler) {
		client.getUserTimeline(handler, since_id, max_id, user_id);
	}

}
