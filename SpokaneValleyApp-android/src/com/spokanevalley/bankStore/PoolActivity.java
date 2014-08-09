package com.spokanevalley.bankStore;

import java.util.List;

import android.app.Activity;
import android.content.Context;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pool_desc_layout);
		context = this;
		loadFromDatabase();

		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		/* Pool Location Main Image */
		imageView = (ImageView) findViewById(R.id.poolViewImage);
		
		/* Pool Location Information */
		textView = (TextView) findViewById(R.id.poolDesc);

		getCoupon = (Button) findViewById(R.id.buttonGetCoupon2);
		
		getCoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			} // End onClick
		}); // End setOnClickListener
		
		cancel = (Button) findViewById(R.id.buttonCancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			} // End onClick
		}); // End setOnClickListener
		
		/* Set Font */
		getCoupon.setTypeface(face);
		cancel.setTypeface(face);
		textView.setTypeface(face);
		
	}// End onCreate
	
	private void loadFromDatabase() {
		poolLocationList = DatabaseCustomAccess.Create(context).getPoolList();
	}
	
	
} // End PoolActivity class
