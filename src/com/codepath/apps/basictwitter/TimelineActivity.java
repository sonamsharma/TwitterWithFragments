package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;

public class TimelineActivity extends FragmentActivity {

	long maxTweetId = Long.MAX_VALUE;
	private final int REQUEST_CODE = 20;
	private Tweet editTweet;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();

		/*
		 * getCurrentUser(); // populateTimeline(1, -1);
		 * lvtweets.setOnScrollListener(new EndlessScrollListener() {
		 * 
		 * @Override public void onLoadMore(int page, int totalItemsCount) { //
		 * Triggered only when new data needs to be appended to the list // Add
		 * whatever code is needed to append new items to your // // AdapterView
		 * // customLoadMoreDataFromApi(page); //
		 * customLoadMoreDataFromApi(totalItemsCount); } });
		 * 
		 * }
		 * 
		 * private void getCurrentUser() { // TODO Auto-generated method stub
		 * client.getCurrentUser(new JsonHttpResponseHandler() {
		 * 
		 * @Override public void onSuccess(JSONObject json) { // TODO
		 * Auto-generated method stub Log.d("debug", json.toString()); user =
		 * User.fromJSON(json); }
		 * 
		 * @Override public void onFailure(Throwable e, String s) { // TODO
		 * Auto-generated method stub Log.d("debug", e.toString()); } }); }
		 * 
		 * protected void customLoadMoreDataFromApi(int page) { // TODO
		 * Auto-generated method stub populateTimeline(1, maxTweetId); }
		 * 
		 * protected void setMaxId(ArrayList<Tweet> fromJSONArray) { for (Tweet
		 * tweet : fromJSONArray) { long currentTweetId = tweet.getUid(); if
		 * (currentTweetId < maxTweetId) { maxTweetId = currentTweetId - 1; } }
		 * 
		 * }
		 * 
		 * public void onComposeAction(MenuItem mi) { Intent i = new
		 * Intent(this, ComposeActivity.class); i.putExtra("tweet", tweets);
		 * i.putExtra("user_profileImage_URL", user.getProfileImageUrl());
		 * i.putExtra("user_name", user.getName());
		 * i.putExtra("user_screenName", user.getScreenName());
		 * startActivityForResult(i, REQUEST_CODE);
		 * 
		 * Toast.makeText(this, "ready to compose", Toast.LENGTH_LONG).show(); }
		 * 
		 * protected void onActivityResult(int requestCode, int resultCode,
		 * Intent data) { if (resultCode == RESULT_OK) { if (requestCode == 20)
		 * { editTweet = (Tweet) data.getSerializableExtra("bodyforTweet");
		 * aTweets.insert(editTweet, 0); aTweets.notifyDataSetChanged(); }
		 * super.onActivityResult(requestCode, resultCode, data); } }
		 */
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tabHome = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_home)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimelineFragment>(
								R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tabHome);
		actionBar.selectTab(tabHome);

		Tab tabMentions = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_mentions)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>(
								R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tabMentions);
	}

	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);

	}

	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
