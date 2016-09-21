package dev.tienminh.fsquare.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.tienminh.fsquare.HomePage.ListItem;
import dev.tienminh.fsquare.R;

/**
 * Created by TIEN on 18/08/2016.
 */
public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.DerpHolder>{

    private List<ListItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public HomePageAdapter(List<ListItem> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public HomePageAdapter.DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubTitle());
        /*if (item.isFavourite()){
            holder.secondaryIcon.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            holder.secondaryIcon.setImageResource(R.drawable.ic_star_border_black_24dp);
        }*/
    }

    public void setListData(ArrayList<ListItem> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thumbnail;
        //ImageView secondaryIcon;
        TextView title;
        TextView subTitle;
        View container;
        Button load;

        public DerpHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView)itemView.findViewById(R.id.im_item_icon);
            // secondaryIcon = (ImageView)itemView.findViewById(R.id.im_item_icon_secondary);
            // secondaryIcon.setOnClickListener(this);
            subTitle = (TextView)itemView.findViewById(R.id.lbl_item_sub_title);
            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            container = (View)itemView.findViewById(R.id.cont_item_root);
            load = (Button)itemView.findViewById(R.id.btn_card_load);
            load.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_card_load){
                itemClickCallback.onItemClick(getAdapterPosition());
            } else {
                // itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
    }
}