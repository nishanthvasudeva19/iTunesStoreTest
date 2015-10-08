package com.richardsgroup.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

@SuppressLint("ViewHolder")
public class ImageTextViewArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public ImageTextViewArrayAdapter(Context context, String[] values) {
		super(context, -1, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.median, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

		String trackName = values[position].split(";")[1];
		String trackCensoredName = values[position].split(";")[2];
		String primaryGenreName = values[position].split(";")[3];
		String artworkUrl100 = values[position].split(";")[4];

		textView.setText(trackName + "\n" + trackCensoredName + "\n"
				+ primaryGenreName);
		if (artworkUrl100.equals("@drawable/ic_launcher")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			Picasso.with(context).load(artworkUrl100).into(imageView);
		}

		return rowView;
	}
}
