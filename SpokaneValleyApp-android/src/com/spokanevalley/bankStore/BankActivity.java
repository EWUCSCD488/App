package com.spokanevalley.bankStore;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseCustomAccess;

/**
 * Bank store Acitivity
 * 
 * @author Quyen Ha Eastern Washington University
 */

public class BankActivity extends Activity {

	protected static final int REQUEST_CODE = 1; // check for status of Activity
													// sent from BankAcitivity

	private ListView listView;
	private ImageView listImageView;
	private TextView listTextView;
	private Context context;

	private List<poolLocation> CouponList;

	public static final String TAG = MallActivity.class.getName(); // for
																	// debugging

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity2);
		context = this;
		loadFromDatabase();

		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Bubblegum.otf");

		listView = (ListView) findViewById(R.id.mainListView);
		listView.setAdapter(new ListViewCustomCouponAdapter(context,
				R.layout.list_item2, CouponList));

		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML

		listTextView = (TextView) findViewById(R.id.ListTextView);
		changeScoreDisplaying();

		listTextView.setTypeface(face);
	}

	/**
	 * check result of Activity sent from BankActivity
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
				super.onActivityResult(requestCode, resultCode, data);
				CouponList = DatabaseCustomAccess.Create(context).getCouponList();
				listView.setAdapter(new ListViewCustomCouponAdapter(context,
					R.layout.list_item2, CouponList));
				changeScoreDisplaying();
				
			} catch (Exception ex) {
			// Toast.makeText(MapView.this, ex.toString(), Toast.LENGTH_SHORT)
			// .show();
			}
	}

	protected void changeScoreDisplaying() {
		if(CouponList.size() == 1)
			listTextView.setText(CouponList.size() + " coupon");
		else
			listTextView.setText(CouponList.size() + " coupons");
	}
	
	/**
	 * get updates from databases
	 */
	
	private void loadFromDatabase() {
		CouponList = DatabaseCustomAccess.Create(context).getCouponList();

	}
}
