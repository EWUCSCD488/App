package com.spokanevalley.bankStore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseCustomAccess;

/**
 * Coupon Activity
 * 
 * @author Quyen Ha
 * Eastern Washington University
 */

public class CouponActivity extends Activity{

	private ImageView imageView;
	private Button useButton;
	private Button cancelButton;
	private Button howtoButton;
	private ButtonSoundFactory buttonSounds;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_layout);
		buttonSounds = new ButtonSoundFactory(getApplicationContext());
		
		
		//get value pass from coupon list
		Intent intent = getIntent();
		final String Couponid = intent.getStringExtra(ListViewCustomCouponAdapter.COUPON_ID);
		
		/* Take the ImageView from layout and set the game image */
		imageView = (ImageView) findViewById(R.id.couponImage);
        imageView.setImageDrawable(getDrawablefromCouponCostFactory(Couponid));
		
        /* Handle howtoPlay button */
		howtoButton = (Button) findViewById(R.id.couponhowto);
		howtoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonSounds.playsound7();
				//showAlert(Couponid,v);
			}
		});
		/* Handle use button */
		useButton = (Button) findViewById(R.id.couponOkayButtons);
		useButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonSounds.playsound5();
				showAlert(Couponid,v);
			}
		});
		
		/* Handle cancel button */
		cancelButton = (Button) findViewById(R.id.couponCancelButton);		
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonSounds.playsound6();
				finish();					// close activity
			}
		});
	}

	/**
	 * generate Drawable of coupon from CouponCostFatory
	 * 
	 * @param id of coupon
	 * @return Drawable file of coupon thumbnail
	 */
	
	private Drawable getDrawablefromCouponCostFactory(String id){
		String uri = "drawable/" + CouponCostFactory.create().getCouponImagePath(id);
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        return image;
	}
	
	/**
	 * generate Drawable of coupon from CouponCostFatory
	 * 
	 * @param id of coupon as String
	 * @return Drawable file of coupon thumbnail
	 */
	
	private Drawable getDrawablefromThumbNailFactory(String id){
		String uri = "drawable/" + ThumbNailFactory.create().getThumbNail(id);
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        return image;
	}
	
	/**
	 * Build Alert dialog
	 * 
	 * @param id of coupon
	 * @param view of application
	 */
	
	private void showAlert(final String id,View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
        LinearLayout layout       = new LinearLayout(v.getContext());
        ImageView image = new ImageView(v.getContext());
        TextView tvMessage        = new TextView(v.getContext());
        //final EditText etInput    = new EditText(context);
        
        tvMessage.setTextSize(20);
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setText("Are you sure you want to redeem this coupon?");				// set text
        
        
        
        image.setImageDrawable(getDrawablefromCouponCostFactory(id));							// set image
        int width = 350 ;
        int height = 350;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        image.setLayoutParams(parms);
        
        layout.setOrientation(LinearLayout.VERTICAL);			// set layout
        layout.addView(image);
        layout.setGravity(Gravity.CENTER);
        layout.addView(tvMessage);
        
        
        alert.setTitle("Comfirmation!");					// add title
        alert.setView(layout);
 
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {			// set cancel button
 
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	buttonSounds.playsound6();
                dialog.cancel();
           
            }
        });
 
        alert.setPositiveButton("Redeem", new DialogInterface.OnClickListener() {			// set redeem button
 
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	// delete coupon from couponList
            	DatabaseCustomAccess.Create(getApplicationContext()).updatePoolwithBoughtCoupon(CouponCostFactory.create().getTheRightPoolFromCoupon(id), false);	// update location in database with true gotCoupon
                DatabaseCustomAccess.Create(getApplicationContext()).updateCouponwithBoughtCoupon(id, false);
                buttonSounds.playsound5();
            	dialog.cancel();
            	finish();
            }


        });
 
        alert.show();
    }
	
}
