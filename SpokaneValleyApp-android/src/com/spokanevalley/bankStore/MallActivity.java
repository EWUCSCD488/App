package com.spokanevalley.bankStore;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseInterface;

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity);
		context = this;
		loadFromDatabase();

		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		
		listView = (ListView) findViewById(R.id.mainListView);
		listView.setAdapter(new ListViewCustomPoolAdapter(context,R.layout.list_item,poolLocationList));
	
		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML
		
		listTextView = (TextView ) findViewById(R.id.ListTextView);
		changeScoreDisplaying();
		
		listView.getAdapter().registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				redeemedCoupon = DatabaseInterface.Create(context).getTotalScore();
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
		poolLocationList = DatabaseInterface.Create(context).getPoolList();
		redeemedCoupon = DatabaseInterface.Create(context).getTotalScore();
		
		
	}
}
