package dev.tienminh.fsquare.Photos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.tienminh.fsquare.R;

/**
 * Created by TIEN on 30/08/2016.
 */
public class GridViewAdapter extends ArrayAdapter {

    private Context context;
    private int mLayout;
    private List data = new ArrayList();
    public GridViewAdapter(Context context, int resourceId, List data) {
        super(context, resourceId, data);
        this.context = context;
        mLayout = resourceId;
        this.data= data;
    }
    static class ViewHolder{
        TextView imgTitle;
        ImageView img;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row=inflater.inflate(mLayout,parent,false);
            holder= new ViewHolder();
            holder.imgTitle = (TextView) row.findViewById(R.id.text);
            holder.img = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }
        ImageItem imageItem = (ImageItem) data.get(position);
        holder.imgTitle.setText(imageItem.getTitle());
        holder.img.setImageBitmap(imageItem.getImage());

        return row;
    }
}
