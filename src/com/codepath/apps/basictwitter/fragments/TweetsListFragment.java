package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvtweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Non View initialization
		tweets = new ArrayList<Tweet>();
		//tweets.clear();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);
		// Assign our view references
		lvtweets = (ListView) v.findViewById(R.id.lvTweets);
		lvtweets.setAdapter(aTweets);
		//aTweets.clear();
		// return the layout view
		return v;
	}

	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
}
