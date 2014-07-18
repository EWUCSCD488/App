package com.spokanevalley.bankStore;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseInterface;

public class BankActivity extends Activity {
	private ListView listView;
	private ImageView listImageView;
	private TextView listTextView;
	private Context context;
	private List<gameModel> gameList;
	public static final String TAG = BankActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity);
		context = this;
		//gameList = new ArrayList<gameModel>();
		loadFromDatabase();
		//gameList.add(new gameModel("title", "description", false, "pharrell"));
		
		//List<gameModel> gameList1 = new ArrayList<gameModel>();
		//gameList1.add(new gameModel("Apple Game",10000,"pharrell"));
		
		listView = (ListView) findViewById(R.id.mainListView);
		listView.setAdapter(new ListViewCustomAdapter(context,R.layout.list_item,gameList));
		
		listImageView = (ImageView) findViewById(R.id.ListImageView);
		// you can add image here or in XML
		
		listTextView = (TextView ) findViewById(R.id.ListTextView);
		listTextView.setText("Testing for title for bank store");
		
	}

	private void loadFromDatabase() {
		gameList = DatabaseInterface.Create(context).getScoreList();
		for(gameModel game : gameList){
			Log.d(TAG, ""+ game.getTitle() + " with " + game.getMaxScore());
		}
		
	}
}
