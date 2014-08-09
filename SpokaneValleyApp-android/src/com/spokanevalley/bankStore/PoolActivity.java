package com.spokanevalley.bankStore;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseCustomAccess;

/**
 * @author Kevin Borling
 * @author Quyen Ha
 * Pool Activity is responsible for displaying information regarding each swimming pool
 * Pool Activity is called when user selects "More Info." from the mall location
 */
public class PoolActivity extends Activity {	
 
	private ImageView imageView;
	private TextView textView;
	private Button getCoupon, cancel;
	private Context context;
	private List<poolLocation> poolLocationList;
	private ButtonSoundFactory buttonSounds;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pool_desc_layout);
		context = this;
		buttonSounds = new ButtonSoundFactory(context);
		Intent intent = getIntent();
		final String poolID = intent.getStringExtra(ListViewCustomPoolAdapter.POOL_ID);
		
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		/* Pool Location Main Image */
		imageView = (ImageView) findViewById(R.id.poolViewImage);
		/* Take the ImageView from layout and set the game image */
        String uri = "drawable/" + ThumbNailFactory.create().getActualPoolPicture(poolID);
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable image = context.getResources().getDrawable(imageResource);
        imageView.setImageDrawable(image);
		
		
		/* Pool Location Information */
		textView = (TextView) findViewById(R.id.poolDesc);
		textView.setText(PoolDescriptionFactory.create().getDescription(poolID));
		
		
		getCoupon = (Button) findViewById(R.id.buttonGetCoupon2);
		
		getCoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkTotalScoreAndBuyCoupon(poolID);
				buttonSounds.playsound2();
			} // End onClick
		}); // End setOnClickListener
		
		cancel = (Button) findViewById(R.id.buttonCancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonSounds.playsound3();
				finish();
			} // End onClick
		}); // End setOnClickListener
		
		/* Set Font */
		getCoupon.setTypeface(face);
		cancel.setTypeface(face);
		textView.setTypeface(face);
		
	}// End onCreate

	
	private void checkTotalScoreAndBuyCoupon(final String poolID) {
		int price = CouponCostFactory.create().getPrice(poolID);
		//Log.d(TAG, "price is : "+ price);
		if( price <= DatabaseCustomAccess.Create(context).getTotalScore() ){
			// buy coupon
			// prompt another to make sure they want to buy it
			comfirmationPromptBuyCoupon(poolID);
		}else{
			// don't buy coupon
			// prompt user that they don't have anough points
			comfirmationPromptBuyCouponFailed();
		}
	}
	
	private void comfirmationPromptBuyCoupon(final String poolID){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure you want to buy this coupon? \n Please redeem the coupon in the bank");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	// substract coupon cost with total score
            	DatabaseCustomAccess.Create(context).addUpTotalScore(-1 * CouponCostFactory.create().getPrice(poolID));
            	//remove((poolLocation) game);			// remove location from list
				//notifyDataSetChanged();					// update location
				DatabaseCustomAccess.Create(context).updatePoolwithBoughtCoupon(poolID, true);	// update location in database with true gotCoupon
                DatabaseCustomAccess.Create(context).updateCouponwithBoughtCoupon(CouponCostFactory.create().getTheRightCouponFromPool(poolID), true);
                buttonSounds.playsound2();
                finish();
				dialog.cancel();
            }
        });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	buttonSounds.playsound3();
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
	}
	
	private void comfirmationPromptBuyCouponFailed(){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("You don't have enough points to get this coupon");
        builder1.setCancelable(false);
        builder1.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	buttonSounds.playsound2();
                dialog.cancel();
            }
        });
       /* builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
	}
	
} // End PoolActivity class
