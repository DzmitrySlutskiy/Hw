package by.dzmitryslutskiy.hw.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.dzmitryslutskiy.hw.bo.Note;
import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

/**
 * Classname
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class NoteBaseAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private String mColumnName;

    public NoteBaseAdapter(Context context, Cursor cursor, String ColumnName) {
        super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mColumnName = ColumnName;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(android.R.layout.simple_list_item_1, null);

        ViewHolder holder = new ViewHolder();
        holder.text1 = (TextView) view.findViewById(android.R.id.text1);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text1.setText(cursor.getString(cursor.getColumnIndex(mColumnName)) +
                " id: " + cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_ID)));
    }

    private class ViewHolder {
        TextView text1;
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);


        return new Note("{\"id\":" + cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_ID)) + "," +
                "\"title\":\"" + cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_TITLE)) + "\"," +
                "\"content\":\"" + cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_CONTENT)) + "\"" +
                "}");
    }

}
