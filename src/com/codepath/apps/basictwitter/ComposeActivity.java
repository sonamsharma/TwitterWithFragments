package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();

		currentuser_profileImageUrl = getIntent().getStringExtra(
				"user_profileImage_URL");
		currentUserName = getIntent().getStringExtra("user_name");
		currentUserScreen_Name = getIntent().getStringExtra("user_screenName");

		ivMyProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(currentuser_profileImageUrl, ivMyProfileImage);
		tvMyName.setText(currentUserName);
		tvScreenName.setText("@" + currentUserScreen_Name);

	}

	public void setupViews() {
		etText = (EditText) findViewById(R.id.et_tweetText);
		ivMyProfileImage = (ImageView) findViewById(R.id.ivMyProfileImage);
		tvMyName = (TextView) findViewById(R.id.tvMyName);
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
	}

	public void onClickTweet(View v) {
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
				finish();
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
		return true;
	}

}
