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
	private List<poolLocation> poolLocationList;
	public static final String TAG = MallActivity.class.getName();
	private int redeemedCoupon;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// prepare
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity);
		context = this;
		loadFromDatabase();
		
		// cook
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		
		listView = (ListView) findViewById(R.id.mainListView);
		listView.setAdapter(new ListViewCustomPoolAdapter(context,R.layout.list_item,poolLocationList));
	
		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML
		
		listTextView = (TextView) findViewById(R.id.ListTextView);
		changeScoreDisplaying();
		
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
