package com.richardsgroup.test;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

public class BasicListDisplayArrayAdapter extends ArrayAdapter<String> {

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	public BasicListDisplayArrayAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		Log.i("MavAlertsFragment - ",
				"Inside BasicListDisplayArrayAdapter constructor");
		for (int i = 0; i < objects.size(); ++i) {
			mIdMap.put(objects.get(i), i);
		}
	}

	@Override
	public long getItemId(int position) {
		String item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}