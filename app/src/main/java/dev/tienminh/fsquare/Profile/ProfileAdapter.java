package dev.tienminh.fsquare.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dev.tienminh.fsquare.R;

/**
 * Created by TIEN on 25/08/2016.
 */
public class ProfileAdapter extends BaseExpandableListAdapter {
    Context c;
    ArrayList<Group> groups;
    LayoutInflater inflater;

    public ProfileAdapter(Context c, ArrayList<Group> groups) {
        this.c = c;
        this.groups = groups;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPos) {
        return groups.get(groupPos).items.size();
    }

    @Override
    public Object getGroup(int groupPos) {
        return groups.get(groupPos);
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        return groups.get(groupPos).items.get(childPos);
    }

    @Override
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View view, ViewGroup viewGroup) {
        Group group = (Group) getGroup(groupPos);
        if (view==null){
            view = inflater.inflate(R.layout.list_group,null);
        }
        TextView tv = (TextView) view.findViewById(R.id.listTitle);
        tv.setText(group.name);
        return view;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View view, ViewGroup viewGroup) {
        String child = (String) getChild(groupPos,childPos);
        if (view==null){
            view= inflater.inflate(R.layout.list_item,null);
        }

        TextView tv = (TextView) view.findViewById(R.id.textView1);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
        tv.setText(child);
        String gr = getGroup(groupPos).toString();
        if (gr=="Account"){
            if (child=="Profile"){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user.getPhotoUrl()!=null) {
                   // Glide.with(c).load(user.getPhotoUrl()).preload(150,150).
                    Picasso.with(c).load(user.getPhotoUrl()).resize(150, 150).centerCrop().into(iv);
                }else {
                    iv.setImageResource(R.drawable.ic_person_black_36dp);
                }

            }
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int childPos) {
        return true;
    }
}
