package com.spokanevalley.bankStore;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spokanevalley.app.R;

public class ListViewCustomPoolAdapter extends ArrayAdapter<poolLocation> {
	
	public static final String TAG = ListViewCustomPoolAdapter.class.getName();
	
	private int resource;
	private LayoutInflater inflater;
	private Context context;
	
	public ListViewCustomPoolAdapter(Context context, int resource, List objects) {
		super(context, resource, objects);
		
		this.resource = resource;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}

	
	 @Override
	    public View getView ( int position, View convertView, ViewGroup parent ) {

	        /* create a new view of my layout and inflate it in the row */
		 if (null == convertView) {
             convertView = ( LinearLayout)  inflater.inflate(resource, null);
         }

	        /* Extract the game object to show */
	        final Model game = getItem( position );

	        /* Take the TextView from layout and set the game name */
	        TextView title = (TextView) convertView.findViewById(R.id.titleItem);
	        title.setText(game.getTitle());

	        /* Take the TextView from layout and set the game wiki link */
	        TextView description = (TextView) convertView.findViewById(R.id.DescriptionItem);
	        description.setText(String.valueOf(game.getDescription()));

	        Button buyCoupon = (Button)convertView.findViewById(R.id.buttonGetCoupon);
	        buyCoupon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.d(TAG, "clicked : "+ game.getTitle());
				}
			});
	        
	        
	        /* Take the ImageView from layout and set the game image */
	        ImageView imageGame = (ImageView) convertView.findViewById(R.id.imageView1);
	        String uri = "drawable/" + game.getImagePath();
	        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
	        Drawable image = context.getResources().getDrawable(imageResource);
	        imageGame.setImageDrawable(image);
	        return convertView;
	    }
	
	
}
