package com.example.nearbytrendsmk.Screens;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.nearbytrendsmk.R;
import com.example.nearbytrendsmk.util.ActivityHelper;
import com.example.nearbytrendsmk.util.GlobalFunction;

import static com.example.nearbytrendsmk.util.Util.makeToast;

public class WelcomeFragment extends Fragment {

    EditText editSearch;
	TextView nLocation;
	CardView cardhashtag, cardloc;
	ImageView imageView;
	Button buttonSearch;
	private Handler mHandler = new Handler();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

		editSearch = (EditText) rootView.findViewById(R.id.ET_HASHTAG);
		nLocation = (TextView) rootView.findViewById(R.id.ET_LOCTION);
		imageView = (ImageView) rootView.findViewById(R.id.IMG_LOGO);
		cardhashtag = (CardView) rootView.findViewById(R.id.CV_hashtag);
		cardloc = (CardView) rootView.findViewById(R.id.CV_loc);
		buttonSearch = (Button) rootView.findViewById(R.id.button_search);

		Bundle gtl = getActivity().getIntent().getExtras();
		if (gtl != null) {
			nLocation.setText(" " + gtl.getString("location"));
			//Toast.makeText(getActivity(), gtl.getString("geolocation"), Toast.LENGTH_LONG).show();
		}

		nLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//getLocation();
				GlobalFunction.tranferab(getActivity(), Maps_Activity.class);
			}
		});

		//buttonSearch = (Button) rootView.findViewById(R.id.button_search);

		buttonSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				if (!validate()) {
					return;
				}

			}
		});

		return rootView;
	}


	public boolean validate() {

		if (TextUtils.isEmpty(nLocation.getText().toString())) {

			makeToast(getActivity(), "Please select your location");
			return false;

		} else if (TextUtils.isEmpty(editSearch.getText().toString())) {

			makeToast(getActivity(), "Please enter a valid word or #hashtag to search");
			return false;

		} else {

			editSearch.setError(null);

			//Toast.makeText(getActivity(), "This is a delayed toast", Toast.LENGTH_SHORT).show();

			SearchResultsFragment fragment = new SearchResultsFragment();
			Bundle args = new Bundle();
			args.putString(SearchResultsFragment.ARG_SEARCH_REQUEST, editSearch.getText().toString());
			args.putString(SearchResultsFragment.ARG_SEARCH_GEOLOC, getActivity().getIntent().getExtras().getString("geolocation"));
			fragment.setArguments(args);
			ActivityHelper.navigateTo(getActivity(), fragment, R.id.container_main);
		/*	NotificationHelper.scheduleRepeatingRTCNotification(getActivity(),"00","01");
			NotificationHelper.enableBootReceiver(getActivity());*/


		}
		return true;
	}






}