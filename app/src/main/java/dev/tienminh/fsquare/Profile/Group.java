package dev.tienminh.fsquare.Profile;

import java.util.ArrayList;

/**
 * Created by TIEN on 25/08/2016.
 */
public class Group {
    public String name;
    public String image;
    public ArrayList<String> items= new ArrayList<String>();

    public Group(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
