package com.titut.colormemory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.titut.colormemory.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 429023 on 9/7/2016.
 */
public class HighScoreActivity extends AppCompatActivity {

    private DatabaseHelper mDatabase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_high_score);
        ListView scoreListView = (ListView)findViewById(R.id.scoreListview);

        mDatabase = new DatabaseHelper(this);
        List<User> users = mDatabase.getAllUsers();

        String[] from = new String[] {"rank", "userName", "score"};
        int[] to = new int[] { R.id.rank, R.id.name, R.id.score};

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < users.size(); i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("rank", String.valueOf(i+1));
            map.put("userName", users.get(i).getName());
            map.put("score", String.valueOf(users.get(i).getScore()));
            fillMaps.add(map);
        }

        SimpleAdapter scoreAdapter = new SimpleAdapter(this, fillMaps, R.layout.score_list_row_item, from, to);
        scoreListView.setAdapter(scoreAdapter);
    }
}
