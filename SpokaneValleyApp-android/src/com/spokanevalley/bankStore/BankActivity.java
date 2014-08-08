package com.spokanevalley.bankStore;

import java.util.List;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseInterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*
 * Banks loads coupons for access to specific pools
 */

public class BankActivity extends Activity {
	protected static final int REQUEST_CODE = 1;
	private ListView listView;
	private ImageView listImageView;
	private TextView listTextView;
	private Context context;
	private List<poolLocation> CouponList;
	public static final String TAG = MallActivity.class.getName();
	private int redeemedCoupon;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity);
		context = this;
		loadFromDatabase();

		listView = (ListView) findViewById(R.id.mainListView);
		listView.setAdapter(new ListViewCustomGameAdapter(context,R.layout.list_item2,CouponList));
		
		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML
		
		listTextView = (TextView ) findViewById(R.id.ListTextView);
		listTextView.setText(redeemedCoupon + " points");
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);

			//if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
				//String requiredValue = data.getStringExtra("Key");
			loadFromDatabase();
			listView.setAdapter(new ListViewCustomGameAdapter(context,R.layout.list_item2,CouponList));
				
			//}
			
		} catch (Exception ex) {
			//Toast.makeText(MapView.this, ex.toString(), Toast.LENGTH_SHORT)
					//.show();
		}
	}
	
	private void loadFromDatabase() {
		//poolLocationFactory.create();
		CouponList = DatabaseInterface.Create(context).getCouponList();
		redeemedCoupon = DatabaseInterface.Create(context).getTotalScore();
		
		
	}
}
