package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;

public class TimelineActivity extends FragmentActivity {

	long maxTweetId = Long.MAX_VALUE;
	private final int REQUEST_CODE = 20;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();

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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 20) {

				Toast.makeText(this, "Tweeting", Toast.LENGTH_LONG).show();

				Tweet newTweet = (Tweet) data
						.getSerializableExtra("bodyforTweet");

				TweetsListFragment fragmentTweetList = (TweetsListFragment) getSupportFragmentManager()
						.findFragmentByTag("home");
				fragmentTweetList.insertTweet(newTweet);
				fragmentTweetList.populateTimeline(1, -1);

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
