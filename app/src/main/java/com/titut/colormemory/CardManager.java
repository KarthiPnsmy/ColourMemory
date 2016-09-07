package com.titut.colormemory;

import android.os.Handler;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.easyandroidanimations.library.FlipHorizontalToAnimation;
import com.titut.colormemory.model.Card;

import java.util.ArrayList;

/**
 * Created by 429023 on 9/6/2016.
 */
public class CardManager {
    interface GameListener {
        void gameUpdated(int score);
    }

    private ArrayList<Card> mCardList;
    private GameListener mGameListener;
    private Card mSelectedCard = null;
    private Card mPrevSelectedCard = null;
    private int mScore = 0;


    CardManager(GameListener callback, ArrayList<Card> mCardList){
        mGameListener = callback;
        this.mCardList = mCardList;
        this.mScore = 0;
    }

    public void itemClick(int position) {
        mSelectedCard = mCardList.get(position);
        if(mPrevSelectedCard == null){
            mSelectedCard.setOpened(true);
            mPrevSelectedCard = mSelectedCard;
        } else {
            mSelectedCard.setOpened(true);
            if((mSelectedCard.getId() != mPrevSelectedCard.getId()) && (mSelectedCard.getImage() == mPrevSelectedCard.getImage())){
                mSelectedCard.setRevealed(true);
                mPrevSelectedCard.setRevealed(true);

                mSelectedCard = null;
                mPrevSelectedCard = null;
                mScore += 2;
            } else {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSelectedCard != null){
                            mSelectedCard.setOpened(false);
                        }

                        if(mPrevSelectedCard != null){
                            mPrevSelectedCard.setOpened(false);
                        }
                        mGameListener.gameUpdated(mScore);
                        mSelectedCard = null;
                        mPrevSelectedCard = null;
                    }
                }, 1000);

                mScore--;
            }
        }
        mGameListener.gameUpdated(mScore);
    }

    public boolean isLevelCompleted() {
        for(int i=0; i<mCardList.size(); i++){
            if (!mCardList.get(i).isRevealed()) {
                return false;
            }
        }
        return true;
    }
}
