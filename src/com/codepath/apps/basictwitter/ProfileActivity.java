package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();

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
				ImageLoader.getInstance().displayImage(
						user.getProfileImageUrl(), ivProfileImage);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
