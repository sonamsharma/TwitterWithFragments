package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	private TwitterClient client;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateUserTimeline();
	};

	public void populateUserTimeline() {
		if (client != null) {
			client.getUserTimeline(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray json) {
					ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
					// setMaxId(tweets);
					addAll(tweets);
				}

				@Override
				public void onFailure(Throwable e, String s) {
					// TODO Auto-generated method stub
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			});
		}
	}
}