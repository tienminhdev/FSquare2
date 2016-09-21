package dev.tienminh.fsquare.TabManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import dev.tienminh.fsquare.MyTimeLine.MyTimeLine;
import dev.tienminh.fsquare.Profile.Group;
import dev.tienminh.fsquare.Profile.ProfileAdapter;
import dev.tienminh.fsquare.R;
import dev.tienminh.fsquare.Start.Login;

/**
 * Created by TIEN on 20/08/2016.
 */
public class Tab_Profile extends Fragment {

    private ExpandableListView expLV;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListerner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vProfile = inflater.inflate(R.layout.tab_profile,container,false);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListerner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user==null){
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                }else {
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    String uid = user.getUid();
                }
            }
        };

        expLV= (ExpandableListView) vProfile.findViewById(R.id.expandableListView);
        final ArrayList<Group> groups = getData();
        ProfileAdapter adapter= new ProfileAdapter(getActivity(),groups);
        expLV.setAdapter(adapter);

        expLV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int ChildPos, long id) {
                String click = groups.get(groupPos).items.get(ChildPos);
                if (click=="Profile"){
                    startActivity(new Intent(getActivity(), MyTimeLine.class));
                    Toast.makeText(getContext(),groups.get(groupPos).items.get(ChildPos),Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return vProfile;
    }
    public ArrayList<Group> getData(){
        Group g1 = new Group("Account");
        g1.items.add("Profile");

        Group g2 = new Group("Friends");
        g2.items.add("No friend to display");

        ArrayList<Group> allGroup = new ArrayList<>();
        allGroup.add(g1);
        allGroup.add(g2);

        return allGroup;
    }
}
