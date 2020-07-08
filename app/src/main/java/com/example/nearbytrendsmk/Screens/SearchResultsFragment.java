package com.example.nearbytrendsmk.Screens;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nearbytrendsmk.Arraylist.TweetList;
import com.example.nearbytrendsmk.DB_and_SP.PrefsController;
import com.example.nearbytrendsmk.R;
import com.example.nearbytrendsmk.Search_events.SearchTweetsEvent;
import com.example.nearbytrendsmk.Search_events.SearchTweetsEventFailed;
import com.example.nearbytrendsmk.Search_events.SearchTweetsEventOk;
import com.example.nearbytrendsmk.Search_events.TwitterGetTokenEvent;
import com.example.nearbytrendsmk.Search_events.TwitterGetTokenEventFailed;
import com.example.nearbytrendsmk.Search_events.TwitterGetTokenEventOk;
import com.example.nearbytrendsmk.util.BusProvider;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import static com.example.nearbytrendsmk.util.Util.makeToast;

public class SearchResultsFragment extends ListFragment {

	private static final String TAG = SearchResultsFragment.class.getName();
	private Bus mBus;
	private String request,geoloc;

	private TweetAdapter brandAdapter;

	private TextView textResult;

	public static final String ARG_SEARCH_REQUEST = "request";
	public static final String ARG_SEARCH_GEOLOC = "geoloc";

	ArrayList<TweetList> data = new ArrayList<TweetList>();

	Handler handler = new Handler();
	Runnable runnable;
	int delay = 960000;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list_tweets, container, false);

		brandAdapter = new TweetAdapter(getActivity(), new TweetList());
		setListAdapter(brandAdapter);
		//brandAdapter.notifyDataSetChanged();

		request = getArguments().getString(ARG_SEARCH_REQUEST);
		geoloc = getArguments().getString(ARG_SEARCH_GEOLOC);

		return rootView;
	}




	@Override
	public void onStart() {
		super.onStart();


		getBus().register(this);
		if (TextUtils.isEmpty(PrefsController.getAccessToken(getActivity()))) {
			getBus().post(new TwitterGetTokenEvent());
		} else {
			String token = PrefsController.getAccessToken(getActivity());
			getBus().post(new SearchTweetsEvent(token, request,geoloc));
		}

	}


	@Override
	public void onStop() {
		super.onStop();
		getBus().unregister(this);
	}

	@Subscribe
	public void onTwitterGetTokenOk(TwitterGetTokenEventOk event) {
		getBus().post(new SearchTweetsEvent(PrefsController.getAccessToken(getActivity()), request,geoloc));
	}

	@Subscribe
	public void onTwitterGetTokenFailed(TwitterGetTokenEventFailed event) {
		makeToast(getActivity(), "Failed to get token");
	}

	@Subscribe
	public void onSearchTweetsEventOk(final SearchTweetsEventOk event) {

		brandAdapter.setTweetList(event.tweetsList);
		brandAdapter.notifyDataSetChanged();



	handler.postDelayed(new Runnable() {
	@Override
			public void run() {

		setUserVisibleHint();

		}
		}, delay);


	}


	public void setUserVisibleHint() {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (ft != null) {

			if (Build.VERSION.SDK_INT >= 26) {
				ft.setReorderingAllowed(false);
				//brandAdapter.notifyDataSetChanged();
				makeToast(getActivity(), "Updated");
			}
			ft.detach(this).attach(this).commit();


		}
	}




	@Subscribe
	public void onSearchTweetsEventFailed(SearchTweetsEventFailed event) {
		makeToast(getActivity(), "Search of tweets failed");
	}


	// TODO migrate to DI
	private Bus getBus() {
		if (mBus == null) {
			mBus = BusProvider.getInstance();
		}
		return mBus;
	}

	public void setBus(Bus bus) {
		mBus = bus;
	}


}
