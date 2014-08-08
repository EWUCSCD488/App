package com.spokanevalley.bankStore;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spokanevalley.app.R;
/*
 * @author Kevin Borling
 * Pool Activity is responsible for displaying information regarding each swimming pool
 * Pool Activity is called when user selects "More Info." from the mall location
 */
public class PoolActivity extends Activity {	
 
	private ImageView imageView;
	private TextView textView;
	private Button button;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pool_desc_layout);
		
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		/* Pool Location Main Image */
		imageView = (ImageView) findViewById(R.id.ListImageView);
		/* Pool Location Information */
		textView = (TextView ) findViewById(R.id.ListTextView);
		
		button = (Button) findViewById(R.id.buttonGetCoupon2);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			} // End onClick
		});
		
		
		button.setTypeface(face);
		textView.setTypeface(face);
	}// End onCreate
	
	
} // End PoolActivity class
