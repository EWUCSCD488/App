package com.spokanevalley.bankStore;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spokanevalley.addScoreGPS.addScoreGPSLauncher;
import com.spokanevalley.app.MapView;
import com.spokanevalley.app.R;

/**
 * create layout for each coupon
 * 
 * @author Quyen Ha
 * Eastern Washington University
 */

public class ListViewCustomCouponAdapter extends ArrayAdapter<gameModel> {
	
	protected static final int REQUEST_CODE = 1;
	public static final String COUPON_ID = "couponID";				// use to send coupon ID to trophy Acitivity
	
	private ButtonSoundFactory buttoSounds;
	
	private int resource;
	private LayoutInflater inflater;
	private Context context;
	
	/**
	 * Default constructor 
	 * 
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public ListViewCustomCouponAdapter(Context context, int resource, List objects) {
		super(context, resource, objects);
		this.resource = resource;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		buttoSounds = new ButtonSoundFactory(context);
	} // End DVC

	/**
	 * Constructor layout for each coupon
	 * 
	 */
	 @Override
	    public View getView ( int position, View convertView, ViewGroup parent ) {
		 
		 Typeface face = Typeface.createFromAsset(this.context.getAssets(),"fonts/Bubblegum.otf");
	        /* create a new view of my layout and inflate it in the row */
		 if (null == convertView)
             convertView = ( LinearLayout)  inflater.inflate(resource, null);

	        /* Extract the game object to show */
	        final Model game = getItem( position );

	        /* Take the TextView from layout and set the game name */
	        TextView title = (TextView) convertView.findViewById(R.id.titleItem);
	        title.setText(game.getTitle());

	        /* Take the TextView from layout and set the game wiki link */
	        TextView description = (TextView) convertView.findViewById(R.id.DescriptionItem);
	        description.setText(game.getDescription());
	        
	        /* redeeem button*/
	        Button redeemButton = (Button) convertView.findViewById(R.id.buttonGetCoupon);
	        redeemButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					buttoSounds.playsound2();
					Intent intent = new Intent(context,CouponActivity.class);
					intent.putExtra(COUPON_ID, game.getTitle());
					((Activity) context).startActivityForResult(intent, REQUEST_CODE);
					//notifyDataSetChanged();
				} // End onClick
			}); // End setOnClickListener

	        /* Take the ImageView from layout and set the game image */
	        ImageView imageGame = (ImageView) convertView.findViewById(R.id.poolViewImage);
	        String uri = "drawable/" + game.getImagePath();
	        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
	        Drawable image = context.getResources().getDrawable(imageResource);
	        imageGame.setImageDrawable(image);
	        
	        /* Set Font */
	        title.setTypeface(face);
	        redeemButton.setTypeface(face);
	        description.setTypeface(face);
	        
	        return convertView;
	    } // End getView

} // End ListViewCustomCouponAdapter

