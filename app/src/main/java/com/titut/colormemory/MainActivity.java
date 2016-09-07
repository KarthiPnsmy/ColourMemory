package com.titut.colormemory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyandroidanimations.library.FlipHorizontalToAnimation;
import com.titut.colormemory.adapter.ColorGridAdapter;
import com.titut.colormemory.model.Card;
import com.titut.colormemory.model.User;


import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements CardManager.GameListener, View.OnClickListener{

    private ArrayList<Card> mCardList = new ArrayList<>();
    private GridView mGridView;
    private ColorGridAdapter mColorGridAdapter;
    private CardManager mCardManager;
    private Button mReloadButton;
    private Button mHighScoreButton;
    private TextView mScoreView;
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        mDatabase = new DatabaseHelper(this);

        mGridView = (GridView)findViewById(R.id.gridView);
        mReloadButton = (Button)findViewById(R.id.reloadButton);
        mHighScoreButton = (Button)findViewById(R.id.highScoreButton);
        mScoreView = (TextView)findViewById(R.id.scoreView);

        mReloadButton.setOnClickListener(this);
        mHighScoreButton.setOnClickListener(this);

        populateData();
        mCardManager = new CardManager(this, mCardList);
        mColorGridAdapter = new ColorGridAdapter(MainActivity.this, mCardList);
        mGridView.setAdapter(mColorGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCardManager.itemClick(position);
            }
        });
    }

    private void populateData(){
        mCardList = new ArrayList<>();
        mCardList.add(new Card(1, R.drawable.colour1, false, false));
        mCardList.add(new Card(2, R.drawable.colour2, false, false));
        mCardList.add(new Card(3, R.drawable.colour3, false, false));
        mCardList.add(new Card(4, R.drawable.colour4, false, false));
        mCardList.add(new Card(5, R.drawable.colour5, false, false));
        mCardList.add(new Card(6, R.drawable.colour6, false, false));
        mCardList.add(new Card(7, R.drawable.colour7, false, false));
        mCardList.add(new Card(8, R.drawable.colour8, false, false));

        mCardList.add(new Card(9, R.drawable.colour1, false, false));
        mCardList.add(new Card(10, R.drawable.colour2, false, false));
        mCardList.add(new Card(11, R.drawable.colour3, false, false));
        mCardList.add(new Card(12, R.drawable.colour4, false, false));
        mCardList.add(new Card(13, R.drawable.colour5, false, false));
        mCardList.add(new Card(14, R.drawable.colour6, false, false));
        mCardList.add(new Card(15, R.drawable.colour7, false, false));
        mCardList.add(new Card(16, R.drawable.colour8, false, false));
        Collections.shuffle(mCardList);
    }

    @Override
    public void gameUpdated(final int score)  {
        mColorGridAdapter.notifyDataSetChanged();
        mScoreView.setText(String.valueOf(score));

        if(mCardManager.isLevelCompleted()){
            showScoreDiaog();
        }
    }

    private void showScoreDiaog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Level Completed");
        builder.setMessage("The score is " + mScoreView.getText() + ". Please enter your username");

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText userField = new EditText(this);
        userField.setHint("Please enter your name");
        userField.setLayoutParams(linearLayoutParams);
        linearLayoutParams.setMargins(70, 0, 100, 0);
        linearLayout.addView(userField);
        userField.setFocusable(true);
        userField.setSingleLine();
        builder.setView(linearLayout);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(null, null);

        final AlertDialog scoreDialog = builder.create();
        scoreDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button positiveButton = scoreDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String input = userField.getText().toString().trim();
                        if (input.length() == 0) {
                            userField.setError("Please enter valid username");
                        } else {
//                            int userCount = mDatabase.getUserCount(userField.getText().toString());
//                            if(userCount>0){
//                                userField.setError("Username already exist!");
//                            } else {
                                mDatabase.addUser(new User(userField.getText().toString(), Integer.valueOf(mScoreView.getText().toString())));
                                scoreDialog.cancel();
//                            }

                        }
                    }
                });
            }
        });
        scoreDialog.show();

    }

    private void restartGame(){
        mScoreView.setText("0");
        populateData();
        mCardManager = new CardManager(this, mCardList);
        mColorGridAdapter = new ColorGridAdapter(MainActivity.this, mCardList);
        mGridView.setAdapter(mColorGridAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.reloadButton){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Restart Game");
            builder.setMessage("Are you sure want to restart the game?");
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restartGame();
                    dialog.cancel();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if(view.getId() == R.id.highScoreButton){
            Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
            startActivity(intent);
        }
    }
}
