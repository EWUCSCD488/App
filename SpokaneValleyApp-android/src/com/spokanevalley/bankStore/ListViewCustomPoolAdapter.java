package com.spokanevalley.bankStore;

import java.util.List;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.spokanevalley.app.R;
import com.spokanevalley.database.DatabaseCustomAccess;

/**
 * construct layout for pool location list in Mall Activity
 * 
 * @author Quyen Ha Eastern Washington University
 */

public class ListViewCustomPoolAdapter extends ArrayAdapter<poolLocation> {

	public static final String TAG = ListViewCustomPoolAdapter.class.getName();

	public static final String POOL_ID = "poolID"; // use to send pool ID to
													// poolAcitivity
	protected static final int REQUEST_CODE = 1;
	private ButtonSoundFactory buttonsounds;
	private int resource;
	private LayoutInflater inflater;
	private Context context;

	/**
	 * Constructor for pool adapter
	 * 
	 * @param context
	 * @param resource
	 * @param objects
	 */

	public ListViewCustomPoolAdapter(Context context, int resource, List objects) {
		super(context, resource, objects);

		this.resource = resource;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		buttonsounds = new ButtonSoundFactory(context); // register for sounds
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/* create a new view of my layout and inflate it in the row */
		if (null == convertView) {
			convertView = (LinearLayout) inflater.inflate(resource, null);
		}

		/* Extract the game object to show */
		final Model game = getItem(position);

		/* Take the TextView from layout and set the game name */
		TextView title = (TextView) convertView.findViewById(R.id.titleItem);
		title.setText(game.getTitle());

		/* Take the TextView from layout and set the game wiki link */
		TextView description = (TextView) convertView
				.findViewById(R.id.DescriptionItem);
		description.setText(String.valueOf(game.getDescription()));

		/* Take the ImageView from layout and set the game image */
		ImageView imageGame = (ImageView) convertView
				.findViewById(R.id.poolViewImage);
		String uri = "drawable/" + game.getImagePath();
		int imageResource = context.getResources().getIdentifier(uri, null,
				context.getPackageName());
		final Drawable image = context.getResources()
				.getDrawable(imageResource);
		imageGame.setImageDrawable(image);

		/* handle buy coupon */
		Button buyCoupon = (Button) convertView
				.findViewById(R.id.buttonGetCoupon);
		buyCoupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonsounds.playsound2(); // play button sound

				showAlert(image, game);
			}
		});

		Button moreInfo = (Button) convertView
				.findViewById(R.id.buttonMoreInfo);
		/* Assign Click Event to "More Info" button */

		moreInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Activity activity = (MallActivity) getContext();
				buttonsounds.playsound1(); // play button sound
				Intent intent = new Intent(context, PoolActivity.class);
				intent.putExtra(POOL_ID, game.getTitle());
				((Activity) context).startActivityForResult(intent,
						REQUEST_CODE);
			}
		});

		return convertView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showAlert(Drawable Dimage, final Model game) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		LinearLayout layout = new LinearLayout(context);
		ImageView image = new ImageView(context);
		TextView tvMessage = new TextView(context);
		// final EditText etInput = new EditText(context);

		tvMessage.setText(PoolDescriptionFactory.create().getDescription(
				game.getTitle())); // set text

		layout.setOrientation(LinearLayout.VERTICAL); // set layout
		layout.setGravity(Gravity.CENTER);
		layout.addView(tvMessage);

		image.setImageDrawable(Dimage); // set image
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width
				- (width / 5), width - (width / 5));
		image.setLayoutParams(parms);
		layout.addView(image);

		alert.setTitle("Confirmation!");
		alert.setView(layout);

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						buttonsounds.playsound1();
						dialog.cancel();
					}
				});

		alert.setPositiveButton("Buy", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				checkTotalScoreAndBuyCoupon(game);
				buttonsounds.playsound2();
				dialog.cancel();
			}

		});

		alert.show();
	}

	private void checkTotalScoreAndBuyCoupon(final Model game) {
		int price = CouponCostFactory.create().getPrice(game.getTitle());
		Log.d(TAG, "price is : " + price);
		if (price <= DatabaseCustomAccess.Create(context).getTotalScore()) {
			// buy coupon
			// prompt another to make sure they want to buy it
			comfirmationPromptBuyCoupon(game);
		} else {
			// don't buy coupon
			// prompt user that they don't have anough points
			comfirmationPromptBuyCouponFailed();
		}
	}

	private void comfirmationPromptBuyCoupon(final Model game) {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setMessage("Are you sure you want to buy this coupon? \n Please redeem the coupon in the bank");
		builder1.setCancelable(true);
		builder1.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// substract coupon cost with total score
						DatabaseCustomAccess.Create(context).addUpTotalScore(
								-1
										* CouponCostFactory.create().getPrice(
												game.getTitle()));
						remove((poolLocation) game); // remove location from
														// list
						notifyDataSetChanged(); // update location
						DatabaseCustomAccess.Create(context)
								.updatePoolwithBoughtCoupon(game.getTitle(),
										true); // update location in database
												// with true gotCoupon
						DatabaseCustomAccess.Create(context)
								.updateCouponwithBoughtCoupon(
										CouponCostFactory.create()
												.getTheRightCouponFromPool(
														game.getTitle()), true);
						buttonsounds.playsound3();
						dialog.cancel();
					}
				});
		builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				buttonsounds.playsound1();
				dialog.cancel();
			}
		});

		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

	private void comfirmationPromptBuyCouponFailed() {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setMessage("You don't have enough points to get this coupon");
		builder1.setCancelable(false);
		builder1.setPositiveButton("Okay",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						buttonsounds.playsound1();
						dialog.cancel();
					}
				});
		/*
		 * builder1.setNegativeButton("No", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) { dialog.cancel(); } });
		 */

		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

}
