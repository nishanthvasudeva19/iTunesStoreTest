package com.richardsgroup.test;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	TextView textViewtrackName, textViewtrackCensoredName,
			textViewprimaryGenreName, textViewcollectionName,
			textViewcollectionPrice, textViewtrackPrice;

	String stringtrackName, stringtrackCensoredName, stringprimaryGenreName,
			stringcollectionName, stringcollectionPrice, stringtrackPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		textViewtrackName = (TextView) findViewById(R.id.textViewtrackName);
		textViewtrackCensoredName = (TextView) findViewById(R.id.textViewtrackCensoredName);
		textViewprimaryGenreName = (TextView) findViewById(R.id.textViewprimaryGenreName);
		textViewcollectionName = (TextView) findViewById(R.id.textViewcollectionName);
		textViewcollectionPrice = (TextView) findViewById(R.id.textViewcollectionPrice);
		textViewtrackPrice = (TextView) findViewById(R.id.textViewtrackPrice);

		String trackId = getIntent().getStringExtra("trackId");
		SQLiteDatabase myDB = openOrCreateDatabase("richards", MODE_PRIVATE,
				null);
		Log.i("detailsactivity", trackId);
		ArrayList<String> list = DBAccess.retrieveDB(myDB, trackId);
		String data = list.get(0);

		stringtrackName = data.split(";")[1];
		stringtrackCensoredName = data.split(";")[2];
		stringprimaryGenreName = data.split(";")[3];
		stringcollectionName = data.split(";")[4];
		stringcollectionPrice = data.split(";")[5];
		stringtrackPrice = data.split(";")[6];

		textViewtrackName.setText("Track Name : " + stringtrackName);
		textViewtrackCensoredName.setText("Censored Name : "
				+ stringtrackCensoredName);
		textViewprimaryGenreName.setText("Genre : " + stringprimaryGenreName);
		textViewcollectionName.setText("Collection Name : "
				+ stringcollectionName);
		textViewcollectionPrice.setText("Collection Price : "
				+ stringcollectionPrice);
		textViewtrackPrice.setText("Track Price : " + stringtrackPrice);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
