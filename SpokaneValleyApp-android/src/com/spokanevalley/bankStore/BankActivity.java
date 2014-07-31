package com.spokanevalley.bankStore;

import java.util.List;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseInterface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BankActivity extends Activity {
	private ListView listView;
	private ImageView listImageView;
	private TextView listTextView;
	private Context context;
	private List<poolLocation> poolLocationList;
	public static final String TAG = BankActivity.class.getName();
	private long redeemedCoupon;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity);
		context = this;
		loadFromDatabase();

		listView = (ListView) findViewById(R.id.mainListView);
		listView.setAdapter(new ListViewCustomPoolAdapter(context,R.layout.list_item,poolLocationList));
		
		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML
		
		listTextView = (TextView ) findViewById(R.id.ListTextView);
		listTextView.setText(redeemedCoupon + " Coupons");
		
		
	}

	private void loadFromDatabase() {
		//poolLocationFactory.create();
		poolLocationList = DatabaseInterface.Create(context).getPoolList();
		
		
	}
}
