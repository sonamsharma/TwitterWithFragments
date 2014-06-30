package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();
		User user = (User) getIntent().getSerializableExtra("user");
		if (user != null) {
			setupWithUser(user);
		} else {
			TwitterApplication.getRestClient().getCurrentUser(
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject jsonObject) {
							User user = User.fromJSON(jsonObject);
							setupWithUser(user);
						}

						@Override
						public void onFailure(Throwable throwable, String s) {
							Log.d("debug", throwable.toString());
							Log.d("debug", s.toString());
							Toast.makeText(ProfileActivity.this,
									"Failure! " + s, Toast.LENGTH_LONG).show();
						}

						@Override
						protected void handleFailureMessage(Throwable t,
								String s) {
							Toast.makeText(ProfileActivity.this,
									"Failure! " + s, Toast.LENGTH_LONG).show();
						}
					});
		}

	}

	private void setupWithUser(User user) {
		getActionBar().setTitle("@" + user.getScreenName());
		populateProfileHeader(user);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragment = UserTimelineFragment.newInstance(user
				.getUid());
		ft.replace(R.id.fragmentUserTimeline, fragment);
		ft.commit();
	}

	private void loadProfileInfo() {
		TwitterClient client = TwitterApplication.getRestClient();
		client.getCurrentUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJSON(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
		});

	}

	private void populateProfileHeader(User user) {
		TextView tvUserProfileName = (TextView) findViewById(R.id.tvUserProfileName);
		TextView tvTagline = (TextView) findViewById(R.id.tvUserProfileTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvUserProfileFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvUserProfileFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivUserProfileImage);
		tvUserProfileName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + "Followers");
		tvFollowing.setText(user.getFriendsCount() + "Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),
				ivProfileImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
