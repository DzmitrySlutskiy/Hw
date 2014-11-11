package by.dzmitryslutskiy.hw.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.bo.Countries;

/**
 * CountryAdapter
 * Version information
 * 11.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class CountryAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private Countries mCountries;

    public CountryAdapter(Context context, Countries countries) {
        mCountries = countries;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mCountries.getSize();
    }

    @Override
    public Object getItem(int position) {
        return mCountries.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.country_item, parent, false);

            holder = new ViewHolder();
            holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            holder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            holder.image1 = (ImageView) convertView.findViewById(android.R.id.icon1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text1.setText(mCountries.getItem(position).getName());
        holder.text2.setText(mCountries.getItem(position).getFullname());

        return convertView;
    }


    private class ViewHolder {
        public TextView text1;
        public TextView text2;
        public ImageView image1;
    }
}