package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	long maxTweetId = Integer.MAX_VALUE;
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvtweets;
	private final int REQUEST_CODE = 20;
	private Tweet editTweet;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		// populateTimeline();
		lvtweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();

		aTweets = new TweetArrayAdapter(this, tweets);
		lvtweets.setAdapter(aTweets);
		getCurrentUser();
		populateTimeline(1, -1);
		lvtweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your //
				// AdapterView //
				customLoadMoreDataFromApi(page);
				// customLoadMoreDataFromApi(totalItemsCount);
			}
		});

	}

	private void getCurrentUser() {
		// TODO Auto-generated method stub
		client.getCurrentUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				Log.d("debug", json.toString());
				user = User.fromJSON(json);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				// TODO Auto-generated method stub
				Log.d("debug", e.toString());
			}
		});
	}

	protected void customLoadMoreDataFromApi(int page) {
		// TODO Auto-generated method stub
		populateTimeline(1, maxTweetId);
	}

	public void populateTimeline(long since_id, long max_id) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
				setMaxId(tweets);
				aTweets.addAll(tweets);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				// TODO Auto-generated method stub
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, since_id, max_id);
	}

	protected void setMaxId(ArrayList<Tweet> fromJSONArray) {
		for (Tweet tweet : fromJSONArray) {
			long currentTweetId = tweet.getUid();
			if (currentTweetId < maxTweetId) {
				maxTweetId = currentTweetId - 1;
			}
		}

	}

	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("tweet", tweets);
		i.putExtra("user_profileImage_URL", user.getProfileImageUrl());
		i.putExtra("user_name", user.getName());
		i.putExtra("user_screenName", user.getScreenName());
		startActivityForResult(i, REQUEST_CODE);

		Toast.makeText(this, "ready to compose", Toast.LENGTH_LONG).show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 20) {
				editTweet = (Tweet) data.getSerializableExtra("bodyforTweet");
				aTweets.insert(editTweet, 0);
				aTweets.notifyDataSetChanged();
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
