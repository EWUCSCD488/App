package com.spokanevalley.bankStore;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseCustomAccess;

/**
 * 
 * @author Quyen Ha
 * Eastern Washington University
 * Responsible for populating the pool listings inside of the Mall
 * User is able to view each available pool location from a list
 * User can get more information or purchase coupon for the select pool location
 * Purchased coupons are transferred to the Bank, handled by BankActivity
 */
public class MallActivity extends Activity {
	
	private ListView listView;
	private ImageView listImageView;
	private TextView listTextView;
	private Context context;
	private List<poolLocation> poolLocationList;
	public static final String TAG = MallActivity.class.getName();
	private int redeemedCoupon;
	
	/**
	 * Populates list_item.xml with appropriate thumbnail, title, and description of each pool location
	 */
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
		/* Pool Thumbnail */
		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML
		/* Pool Description */
		listTextView = (TextView) findViewById(R.id.ListTextView);
		changeScoreDisplaying();
		
		listView.getAdapter().registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				redeemedCoupon = DatabaseCustomAccess.Create(context).getTotalScore();
				changeScoreDisplaying();
			} // End onChanged
			public void onInvalidated() {} // End onInvalidated
		}); // End DataSetObserver
		
		/* Set Font */
		listTextView.setTypeface(face);
	} // End onCreate
	
	/**
	 * Updates score after coupon is purchased
	 */
	protected void changeScoreDisplaying() {
		listTextView.setText(redeemedCoupon + " points");
	} // End changeScoreDisplaying

	/**
	 * Check result of Activity sent from PoolActivity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
				super.onActivityResult(requestCode, resultCode, data);
				poolLocationList = DatabaseCustomAccess.Create(context).getPoolList();
				listView.setAdapter(new ListViewCustomPoolAdapter(context,
					R.layout.list_item, poolLocationList));
				changeScoreDisplaying();
			} catch (Exception ex) {
			// Toast.makeText(MapView.this, ex.toString(), Toast.LENGTH_SHORT)
			// .show();
			} // End catch
	} // End onActivityResult
	/**
	 * Populates Array List of pool locations and gets the users score
	 */
	private void loadFromDatabase() {
		//poolLocationFactory.create();
		poolLocationList = DatabaseCustomAccess.Create(context).getPoolList();
		redeemedCoupon = DatabaseCustomAccess.Create(context).getTotalScore();
	} // End loadFromDatabase
	
} // End MallActivity class
