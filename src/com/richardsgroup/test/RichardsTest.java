package com.richardsgroup.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class RichardsTest extends Activity implements OnItemClickListener {
	SQLiteDatabase myDB;
	HttpClient httpClient;
	HttpPost httppost;
	HttpResponse response;
	HttpEntity entity;
	String status, result, uname, pasw, resp;
	InputStream isr;
	JSONArray jArray;
	JSONArray jsonArray;
	ArrayList<String> list;
	String[] values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_richards_test);

		myDB = openOrCreateDatabase("richards", MODE_PRIVATE, null);
		DBAccess.createDB(myDB);

		final ListView listview = (ListView) findViewById(R.id.list);
		boolean netStatus = isNetworkOnline();

		if (netStatus) {
			AppleAPI user = new AppleAPI();
			try {
				list = new ArrayList<String>();
				list = user.execute().get();
				values = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					values[i] = list.get(i);
				}
				final ImageTextViewArrayAdapter adapter = new ImageTextViewArrayAdapter(
						this, values);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(this);
			} catch (InterruptedException e) {
				Log.e("log_tag - ",
						"Error in InterruptedException - " + e.toString());
			} catch (ExecutionException e) {
				Log.e("log_tag - ",
						"Error in ExecutionException - " + e.toString());
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Connection Offline!!! Please connect to the Internet",
					Toast.LENGTH_LONG).show();

			list = DBAccess.retrieveDB(myDB, null);
			values = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String fullData = list.get(i);
				String trackId = fullData.split(";")[0];
				String trackName = fullData.split(";")[1];
				String trackCensoredName = fullData.split(";")[2];
				String primaryGenreName = fullData.split(";")[3];
				String artworkUrl100 = "@drawable/ic_launcher";

				values[i] = trackId + ";" + trackName + ";" + trackCensoredName
						+ ";" + primaryGenreName + ";" + artworkUrl100;

				Log.i("richie", "I am fulldata " + fullData);
			}
			final ImageTextViewArrayAdapter adapter = new ImageTextViewArrayAdapter(
					this, values);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(this);
		}

	}

	public boolean isNetworkOnline() {
		boolean status = false;

		try {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
					status = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return status;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String item = (String) parent.getItemAtPosition(position);
		Intent intent = new Intent("com.richardsgroup.test.DETAILSACTIVITY");

		String[] sendData = item.split(";");
		Log.i("log", sendData[0]);
//		Toast.makeText(getApplicationContext(), sendData[0], Toast.LENGTH_LONG)
//				.show();
		intent.putExtra("trackId", sendData[0]);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.richards_test, menu);
		return true;
	}

	// @SuppressWarnings("unused")
	private class AppleAPI extends AsyncTask<String, String, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(String... params) {
			try {

				httpClient = new DefaultHttpClient();

				httppost = new HttpPost(
						"https://itunes.apple.com/search?term=bb+king&limit=20");

				System.out.println("httpPost is done");
				response = httpClient.execute(httppost);
				System.out.println(response);
				entity = response.getEntity();
				if (entity != null) {
					isr = entity.getContent();
					System.out.println("byte - " + isr.available());
				}
			} catch (UnsupportedEncodingException e) {
				Log.e("log_tag", " Error in UnsupportedEncodingException - "
						+ e.toString());
			} catch (ClientProtocolException e) {
				Log.e("log_tag",
						" Error in ClientProtocolException - " + e.toString());
			} catch (IOException e) {
				Log.e("log_tag", " Error in IOException - " + e.toString());
			} catch (Exception e) {
				Log.e("log_tag", " Error in Connection" + e.toString());
			}

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(isr, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				isr.close();

				result = sb.toString();
				System.out.println("result from ISR : " + result.length());
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
			}

			try {
				ArrayList<String> ar = new ArrayList<String>();
				JSONObject json = new JSONObject(result);
				Log.i("log_tag", "create json object with result");
				JSONArray jsonArray = json.getJSONArray("results");
				Log.i("log_tag", "create getsth with json");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String trackId = jsonObject.getString("trackId");
					String trackName = jsonObject.getString("trackName");
					String trackCensoredName = jsonObject
							.getString("trackCensoredName");
					String primaryGenreName = jsonObject
							.getString("primaryGenreName");
					String collectionName = jsonObject
							.getString("collectionName");
					String collectionPrice = jsonObject
							.getString("collectionPrice");
					String trackPrice = jsonObject.getString("trackPrice");
					String artworkUrl100 = jsonObject
							.getString("artworkUrl100");

					ar.add(trackId + ";" + trackName + ";" + trackCensoredName
							+ ";" + primaryGenreName + ";" + artworkUrl100);

					Log.i("RichardsTest - ", "Value from itunes - " + trackName
							+ ", " + trackCensoredName + ", "
							+ primaryGenreName);

					DBAccess.insertDB(myDB, trackId, trackName,
							trackCensoredName, primaryGenreName,
							collectionName, collectionPrice, trackPrice);
				}
				return ar;
			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			} catch (NullPointerException e) {
				Log.e("log_tag", "NPE " + e.toString());
			}

			return null;
		}

	}
}