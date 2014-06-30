package com.codepath.apps.basictwitter.fragments;

import com.codepath.apps.basictwitter.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

	public void sendClientRequest(TwitterClient client, long sinceId,
			long max_id, JsonHttpResponseHandler handler) {
		client.getHomeTimeline(handler, sinceId, max_id);

	}
}
