package dev.tienminh.fsquare.MyTimeLine;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import dev.tienminh.fsquare.Photos.GridViewAdapter;
import dev.tienminh.fsquare.Photos.ImageItem;
import dev.tienminh.fsquare.R;

/**
 * Created by TIEN on 28/08/2016.
 */
public class Card_layout2 extends Fragment{
    GridView gridView;
    GridViewAdapter adapter;
    List<ImageItem> imageItems;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_layout2,container,false);
        gridView= (GridView) v.findViewById(R.id.gridView);
        imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.cover);
        for (int i = 0; i< imgs.length();i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imgs.getResourceId(i,-1));
            imageItems.add(new ImageItem(bitmap,"image#"+i));
        }
        adapter = new GridViewAdapter(getContext(),R.layout.photo_item_layout,imageItems);
        gridView.setAdapter(adapter);
        return v;
    }
}
