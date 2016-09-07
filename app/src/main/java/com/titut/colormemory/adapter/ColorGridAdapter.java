package com.titut.colormemory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyandroidanimations.library.FlipHorizontalAnimation;
import com.easyandroidanimations.library.FlipHorizontalToAnimation;
import com.titut.colormemory.R;
import com.titut.colormemory.model.Card;

import java.util.ArrayList;

/**
 * Created by 429023 on 9/6/2016.
 */
public class ColorGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Card> mCardList;
    private ImageView mDefaultCard = null;

    public ColorGridAdapter(Context context, ArrayList<Card> mCardList) {
        mContext = context;
        this.mCardList = mCardList;
        mDefaultCard = new ImageView(mContext);
        mDefaultCard.setBackgroundResource(R.drawable.card_bg);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mCardList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Card currentItem = mCardList.get(position);

        ImageView gridTileView;
        if (convertView == null) {
            gridTileView = new ImageView(mContext);
        } else {
            gridTileView = (ImageView) convertView;
        }

        if (currentItem.isRevealed()) {
            gridTileView.setVisibility(View.GONE);
        } else {
            gridTileView.setVisibility(View.VISIBLE);
        }

        gridTileView.setBackgroundResource(R.drawable.card_bg);
        if (currentItem.isOpened()) {
            gridTileView.setImageResource(currentItem.getImage());
        } else {
            gridTileView.setImageResource(R.drawable.card_bg);
        }

        return gridTileView;

    }




}
