package dev.tienminh.fsquare.TimeLine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import dev.tienminh.fsquare.R;

public class TimeLine extends AppCompatActivity {

    RecyclerView rec_timeline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_line);

        rec_timeline = (RecyclerView) findViewById(R.id.rec_timeline);

    }
}
