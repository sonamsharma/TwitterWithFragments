package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	String tweetText;
	String currentuser_profileImageUrl;
	String currentUserName;
	String currentUserScreen_Name;
	EditText etText;
	ImageView ivMyProfileImage;
	TextView tvMyName;
	TextView tvScreenName;
	User meUser;
	private Menu menu;
	private MenuItem iTweetLength;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();
		getUserInfo();
		etText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {

				int letterCount = etText.getText().length();
				int letterRemaining = 140 - letterCount;
				iTweetLength = menu.findItem(R.id.iTweetLength);
				View v = iTweetLength.getActionView();
				TextView tv = (TextView) v.findViewById(R.id.tvLength);
				tv.setText(String.valueOf(letterRemaining));
				if (letterRemaining >= 0) {
					tv.setTextColor(Color.parseColor("#FFFFFF"));
				} else {
					tv.setTextColor(Color.parseColor("#8B0000"));
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

	}

	private void getUserInfo() {
		// Get the profile image, user name and screen name
		TwitterClient client = TwitterApplication.getRestClient();
		client.getCurrentUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJSON(json);
				getActionBar().setTitle("@" + u.getScreenName());
				ivMyProfileImage.setImageResource(android.R.color.transparent);
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.displayImage(u.getProfileImageUrl(),
						ivMyProfileImage);
				tvMyName.setText(u.getName());
				tvScreenName.setText("@" + u.getScreenName());
			}
		});

	}

	public void setupViews() {
		etText = (EditText) findViewById(R.id.et_tweetText);
		ivMyProfileImage = (ImageView) findViewById(R.id.ivMyProfileImage);
		tvMyName = (TextView) findViewById(R.id.tvMyName);
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
	}

	public void onClickTweet(MenuItem mi) {
		tweetText = etText.getText().toString();
		TwitterClient client = TwitterApplication.getRestClient();
		client.updateStatus(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				Toast.makeText(ComposeActivity.this, "Success!",
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent();
				i.putExtra("bodyforTweet", Tweet.fromJSON(jsonObject));
				setResult(RESULT_OK, i);
				ComposeActivity.this.finish();
			}

			@Override
			public void onFailure(Throwable throwable, String s) {

				Log.e("DBG", throwable.toString());
				Log.e("DBG", s);
				Toast.makeText(ComposeActivity.this, "Tweet failed!",
						Toast.LENGTH_SHORT).show();
				super.onFailure(throwable, s);
			}
		}, tweetText);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		this.menu = menu;
		return true;
	}

}
