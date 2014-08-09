package com.spokanevalley.bankStore;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseCustomAccess;

/**
 * 
 * @author Quyen Ha
 * Eastern Washington University
 */

public class MallActivity extends Activity {
	
	
	private ListView listView;
	private ImageView listImageView;
	private TextView listTextView;
	private Context context;
	private Button moreInfo;
	private LayoutInflater inflater;
	private List<poolLocation> poolLocationList;
	public static final String TAG = MallActivity.class.getName();
	private int redeemedCoupon;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// prepare
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity);
		context = this;
		this.inflater = LayoutInflater.from(context);
		loadFromDatabase();
		
		
		// cook
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		
		listView = (ListView) findViewById(R.id.mainListView);
		listView.setAdapter(new ListViewCustomPoolAdapter(context,R.layout.list_item,poolLocationList));
	
		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML
		
		listTextView = (TextView) findViewById(R.id.ListTextView);
		changeScoreDisplaying();
		
		
		/* Get list_item view to assign action to moreInfo button */
		View itemView = (LinearLayout)  this.inflater.inflate(R.layout.list_item, null);
		moreInfo = (Button)itemView.findViewById(R.id.buttonMoreInfo);
		/* Assign Click Event to "More Info" button */
        moreInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//buttonsounds.playsound1();			// play button sound
				System.out.println("More Info Clicked");
				/* Must create new Intent to launch PoolActivity */
				Intent intent = new Intent(MallActivity.this.getApplicationContext(), PoolActivity.class);
				startActivity(intent);
			}
		});
		
		
		listView.getAdapter().registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				redeemedCoupon = DatabaseCustomAccess.Create(context).getTotalScore();
				changeScoreDisplaying();
			}
			
			public void onInvalidated() {
				
			}
		});
		
		listTextView.setTypeface(face);
	}

	protected void changeScoreDisplaying() {
		listTextView.setText(redeemedCoupon + " points");
		
	}

	private void loadFromDatabase() {
		//poolLocationFactory.create();
		poolLocationList = DatabaseCustomAccess.Create(context).getPoolList();
		redeemedCoupon = DatabaseCustomAccess.Create(context).getTotalScore();
		
		
	}
}
