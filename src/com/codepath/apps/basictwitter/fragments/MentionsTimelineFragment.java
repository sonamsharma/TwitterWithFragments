package com.codepath.apps.basictwitter.fragments;

import com.codepath.apps.basictwitter.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {

	public void sendClientRequest(TwitterClient client, long sinceId,
			long max_id, JsonHttpResponseHandler handler) {
		client.getMentionsTimeline(handler, sinceId, max_id);
	}
}
