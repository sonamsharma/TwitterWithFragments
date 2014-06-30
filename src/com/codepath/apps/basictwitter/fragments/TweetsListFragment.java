package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvtweets;

	long maxTweetId = Long.MAX_VALUE;
	private TwitterClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		// Non View initialization
		tweets = new ArrayList<Tweet>();
		// tweets.clear();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);
		// Assign our view references
		populateTimeline(1, -1);
		lvtweets = (ListView) v.findViewById(R.id.lvTweets);
		tweets.clear();
		aTweets.clear();
		// pagination code
		lvtweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				customLoadMoreDataFromApi(totalItemsCount);
			}
		});
		lvtweets.setAdapter(aTweets);
		return v;
	}

	protected void customLoadMoreDataFromApi(int page) {
		// TODO Auto-generated method stub
		populateTimeline(1, maxTweetId);
	}

	abstract public void sendClientRequest(TwitterClient client, long since_id,
			long max_id, JsonHttpResponseHandler handler);

	public void populateTimeline(long since_id, long max_id) {
		if (client != null) {
			this.sendClientRequest(client, since_id, max_id,
					new JsonHttpResponseHandler() {
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
					});
		}
	}

	protected void setMaxId(ArrayList<Tweet> fromJSONArray) {
		for (Tweet tweet : fromJSONArray) {
			long currentTweetId = tweet.getUid();
			if (currentTweetId < maxTweetId) {
				maxTweetId = currentTweetId - 1;
			}
		}

	}

	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}

	public void insertTweet(Tweet newTweet) {
		// TODO Auto-generated method stub
		aTweets.insert(newTweet, 0);
	}
}
