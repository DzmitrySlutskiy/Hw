package by.dzmitryslutskiy.hw.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.data.TypeA;
import by.dzmitryslutskiy.hw.data.TypeB;
import by.dzmitryslutskiy.hw.data.TypeC;
import by.dzmitryslutskiy.hw.data.TypeD;

/**
 * Classname
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class SampleAdapter extends BaseAdapter {

    private static final int TYPE_A = 0;
    private static final int TYPE_B = TYPE_A + 1;
    private static final int TYPE_C = TYPE_B + 1;
    private static final int TYPE_D = TYPE_C + 1;
    private static final int TYPE_MAX_COUNT = TYPE_D + 1;

    private LayoutInflater mInflater;
    private Resources mResources;
    private List<TypeA> mListTypes;

    public SampleAdapter(Context context, List<TypeA> list) {
        if (list == null) {
            throw new IllegalArgumentException("list cannot be null");
        }
        mInflater = LayoutInflater.from(context);
        mResources = context.getResources();
        mListTypes = list;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mListTypes == null ? 0 : mListTypes.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mListTypes == null ? null : mListTypes.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        TypeA baseType = (TypeA) getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TYPE_B:
                    convertView = mInflater.inflate(R.layout.list_item_type_b, null);
                    break;

                case TYPE_C:
                    convertView = mInflater.inflate(R.layout.list_item_type_c, null);
                    break;

                case TYPE_D:
                    convertView = mInflater.inflate(R.layout.list_item_type_d, null);
                    break;

                default:
                case TYPE_A:
                    convertView = mInflater.inflate(R.layout.list_item_type_a, null);
                    break;
            }
            holder.icon = (ImageView) convertView.findViewById(android.R.id.icon);
            holder.icon2 = (ImageView) convertView.findViewById(android.R.id.icon2);
            holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            holder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        switch (type) {
            case TYPE_A:
                holder.text1.setText(baseType.getText());
                break;

            case TYPE_C:
            case TYPE_B:
                TypeB typeB = (TypeB) baseType;
                holder.text1.setText(typeB.getText());
                holder.text2.setText(typeB.getmText2());
                holder.icon.setImageDrawable(mResources.getDrawable(typeB.getmImageId()));
                break;

            case TYPE_D:
                TypeD typeD = (TypeD) baseType;
                holder.text1.setText(typeD.getText());
                holder.text2.setText(typeD.getmText2());
                holder.icon.setImageDrawable(mResources.getDrawable(typeD.getmImageId()));
                holder.icon2.setImageDrawable(mResources.getDrawable(typeD.getmIconId2()));
                break;

            default:
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        TypeA item = mListTypes.get(position);

        if (item instanceof TypeD) {
            return TYPE_D;
        } else if (item instanceof TypeC) {
            return TYPE_C;
        } else if (item instanceof TypeB) {
            return TYPE_B;
        } else {
            return TYPE_A;
        }
    }

    public void addItem(TypeA item){
        mListTypes.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    private static final class ViewHolder {
        private static int counter = 0;

        ViewHolder() {
            counter++;
            Log.d("ViewHolder", "counter:" + counter);
        }

        TextView text1;
        TextView text2;
        ImageView icon;
        ImageView icon2;
    }
}
