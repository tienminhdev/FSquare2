package dev.tienminh.fsquare.TabManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.tienminh.fsquare.R;

/**
 * Created by TIEN on 20/08/2016.
 */
public class Tab_Search extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vSearch= inflater.inflate(R.layout.tab_search,container,false);
        return vSearch;
    }
}
