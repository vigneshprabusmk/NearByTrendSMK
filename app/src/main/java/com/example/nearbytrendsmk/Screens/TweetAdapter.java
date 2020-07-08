package com.example.nearbytrendsmk.Screens;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearbytrendsmk.Arraylist.TweetList;
import com.example.nearbytrendsmk.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TweetAdapter extends BaseAdapter {

	private Context mContext;
	private TweetList tweetList;

	public TweetAdapter(Context mContext, TweetList tweetList) {
		this.mContext = mContext;
		this.tweetList = tweetList;
	}

	public void setTweetList(TweetList tweetList) {
		this.tweetList = tweetList;
	}

	@Override
	public int getCount() {
		if (tweetList.tweets != null) {
			return tweetList.tweets.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return null; // we don't need it now
	}

	@Override
	public long getItemId(int position) {
		return 0; // we don't need it now
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final ViewHolder holder;

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.row_feed, parent, false);
			holder = new ViewHolder();
			holder.textTweet = (TextView) row.findViewById(R.id.text_tweet);
			holder.textUser = (TextView) row.findViewById(R.id.text_user);
			holder.imageLogo = (CircleImageView) row.findViewById(R.id.image_user_logo);

			holder.tv_name2 =  row.findViewById(R.id.tv_name2);
			holder.tv_time =  row.findViewById(R.id.tv_time);

			holder.media_container = row.findViewById(R.id.media_container);
			holder.thumbnail = row.findViewById(R.id.thumbnail);
			holder.tv_likes =  row.findViewById(R.id.tv_like);
			holder.tv_comments = row.findViewById(R.id.tv_comment);
			//holder.tv_status = row.findViewById(R.id.tv_status);
			holder.tv_views =  row.findViewById(R.id.tv_views);
			holder.moredots =  row.findViewById(R.id.imgView_more);

			holder.moredots.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					Toast.makeText(mContext,"More options", Toast.LENGTH_SHORT).show();
				}
			});

			holder.imageLogo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					Toast.makeText(v.getContext(),(tweetList.tweets.get(position).user.getName()), Toast.LENGTH_SHORT).show();
				}
			});

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}


		Picasso.get().load(tweetList.tweets.get(position).user.getProfile_image_url_https()).into(holder.imageLogo);

		holder.textUser.setText(tweetList.tweets.get(position).user.getName());

		holder.tv_name2.setText("@"+tweetList.tweets.get(position).user.getScreen_name());
		holder.tv_time.setText(tweetList.tweets.get(position).getDateCreated());
		holder.textTweet.setText(tweetList.tweets.get(position).text);
		//Picasso.get().load(tweetList.tweets.get(position).user.getProfile_banner_url()).into(holder.thumbnail);

		if((tweetList.tweets.get(position).user.getProfile_banner_url()==null)){
			holder.thumbnail.setVisibility(View.GONE);
		} else {
			holder.thumbnail.setVisibility(View.VISIBLE);
			Picasso.get().load(tweetList.tweets.get(position).user.getProfile_banner_url()).into(holder.thumbnail);

		}

       /* if(tweetList.tweets.get(position).user.getProfile_url().isEmpty()){
            imgView_proPic.setImageResource(R.drawable.howdy);

        }*/
		//else
		{
			//Picasso.get().load(tweetList.tweets.get(position).user.getProfile_url()).into(imgView_proPic);
		}

		holder.tv_likes.setText(String.valueOf(tweetList.tweets.get(position).user.getFavourites_count()));
		holder.tv_comments.setText(tweetList.tweets.get(position).getRetweet_count()+"");
		holder.tv_views.setText(tweetList.tweets.get(position).user.getStatuses_count()+"");









		return row;


	}

	public  class ViewHolder {
		TextView textTweet;
		TextView textUser;
		CircleImageView imageLogo;

		FrameLayout media_container;
		ImageView thumbnail,moredots;
		TextView tv_name ,tv_name2, tv_time, tv_likes, tv_comments, tv_status,tv_views;
		//CircleImageView imgView_proPic;



	}

}
