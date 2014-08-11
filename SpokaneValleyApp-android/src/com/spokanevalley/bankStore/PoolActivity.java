package com.spokanevalley.bankStore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
	private ButtonSoundFactory buttonSounds;
	
	/**
	 * Populates pool_desc_layout.xml with the appropriate image, and description of pool location
	 * Also assigns click events to the "Get Coupon" and "Cancel" buttons
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pool_desc_layout);
		this.context = this;
		this.buttonSounds = new ButtonSoundFactory(context);
		Intent intent = getIntent();
		final String poolID = intent.getStringExtra(ListViewCustomPoolAdapter.POOL_ID);
		
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		/* Pool Location Main Image */
		this.imageView = (ImageView) findViewById(R.id.poolViewImage);
		/* Take the ImageView from layout and set the game image */
        String uri = "drawable/" + ThumbNailFactory.create().getActualPoolPicture(poolID);
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable image = context.getResources().getDrawable(imageResource);
        this.imageView.setImageDrawable(image);
		
		/* Pool Location Information */
        this.textView = (TextView) findViewById(R.id.poolDesc);
        this.textView.setText(PoolDescriptionFactory.create().getDescription(poolID));
		
        this.getCoupon = (Button) findViewById(R.id.buttonGetCoupon2);
		
        this.getCoupon.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				checkTotalScoreAndBuyCoupon(poolID);
				buttonSounds.playsound2();
			} // End onClick
		}); // End setOnClickListener
		
        this.cancel = (Button) findViewById(R.id.buttonCancel);
		
        this.cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonSounds.playsound1();
				finish();
			} // End onClick
		}); // End setOnClickListener
		
		/* Set Font */
        this.getCoupon.setTypeface(face);
        this.cancel.setTypeface(face);
        this.textView.setTypeface(face);
		
	}// End onCreate

	/**
	 * Checks the user's score against the cost of the coupon
	 * A user is able to purchase a coupon if they have points >= cost of coupon
	 * If the user attempts to purchase a coupon that cost more than there current points
	 * a prompt is displayed notifying the user
	 * @param poolID
	 */
	private void checkTotalScoreAndBuyCoupon(final String poolID) {
		int price = CouponCostFactory.create().getPrice(poolID);
		//Log.d(TAG, "price is : "+ price);
		if( price <= DatabaseCustomAccess.Create(context).getTotalScore())
			comfirmationPromptBuyCoupon(poolID);
		else
			confirmationPromptBuyCouponFailed();
	} // End checkTotalScoreAndBuyCoupon
	/**
	 * Prompts the user with a dialog requesting confirmation of coupon purchase
	 * @param poolID
	 */
	private void comfirmationPromptBuyCoupon(final String poolID){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure you want to buy this coupon? \n Please redeem the coupon in the bank");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	// subtract coupon cost from the total score
            	DatabaseCustomAccess.Create(context).addUpTotalScore(-1 * CouponCostFactory.create().getPrice(poolID));
            	//remove((poolLocation) game);			// remove location from list
				//notifyDataSetChanged();					// update location
				DatabaseCustomAccess.Create(context).updatePoolwithBoughtCoupon(poolID, true);	// update location in database with true gotCoupon
                DatabaseCustomAccess.Create(context).updateCouponwithBoughtCoupon(CouponCostFactory.create().getTheRightCouponFromPool(poolID), true);
                buttonSounds.playsound3();
                finish();
				dialog.cancel();
            } // End onClick
        }); // End OnClickListener
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	buttonSounds.playsound1();
                dialog.cancel();
            } // End onClick
        }); // End OnClickListener
        AlertDialog alert11 = builder1.create();
        alert11.show();
	} // End comfirmationPromptBuyCoupon
	/**
	 * Prompts a dialog to the user if they don't have the required points for coupon purchase
	 */
	private void confirmationPromptBuyCouponFailed(){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("You don't have enough points to get this coupon");
        builder1.setCancelable(false);
        builder1.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	buttonSounds.playsound2();
                dialog.cancel();
            } // End onClick
        }); // End OnClickListener
        
       /* builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });*/
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
	} // End confirmationPromptBuyCouponFailed
	
} // End PoolActivity class
