package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	String tweetText;
	EditText etText;
	User meUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etText = (EditText) findViewById(R.id.et_tweetText);
		/*
		 * TwitterApplication.getRestClient().getUser( new
		 * JsonHttpResponseHandler() {
		 * 
		 * @Override public void onSuccess(JSONObject jsonObject) { meUser =
		 * User.fromJSON(jsonObject); ImageLoader.getInstance().displayImage(
		 * meUser.getProfileImageUrl(), R.id.ivMyProfileImage);
		 * runOnUiThread(new Runnable() {
		 * 
		 * @Override public void run() { View v = getRoot();
		 * setupTextviewContents(v, R.id.tvMyName, meUser.getName());
		 * setupTextviewContents( v, R.id.tvScreenName, String.format("@%s",
		 * meUser.getScreenName(), "@")); } }); } });
		 */
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
