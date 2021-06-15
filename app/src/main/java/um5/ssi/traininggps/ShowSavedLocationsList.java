package um5.ssi.traininggps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowSavedLocationsList extends AppCompatActivity {

    List<WayPoint> allWayPoints = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_location_list);

        recyclerView = (RecyclerView)findViewById(R.id.lv_way_points_list);
        recyclerView.setHasFixedSize(true);

        //linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(ShowSavedLocationsList.this);
        allWayPoints = feedReaderDbHelper.getAll();

        mAdapter=new RecyclerViewAdapter(allWayPoints,ShowSavedLocationsList.this);
        recyclerView.setAdapter(mAdapter);

    }
}