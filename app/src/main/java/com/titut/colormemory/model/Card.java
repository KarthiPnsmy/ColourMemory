package com.titut.colormemory.model;

/**
 * Created by 429023 on 9/6/2016.
 */
public class Card {
    int id;
    int image;
    boolean isOpened;
    boolean isRevealed;

    public Card(int id, int image, boolean isOpened, boolean isRevealed) {
        this.id = id;
        this.image = image;
        this.isOpened = isOpened;
        this.isRevealed = isRevealed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

}
