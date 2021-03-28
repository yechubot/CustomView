package com.example.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.content.res.ResourcesCompat;

public class CustomEdit extends androidx.appcompat.widget.AppCompatEditText {
    Drawable mClearButtonImage;

    // constructor
    public CustomEdit(Context context) {
        super(context);
        init();
    }

    public CustomEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showArrowButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if((getCompoundDrawablesRelative()[2]!=null)){ // return drawable at the end of the text [2]
                    // only the location is not null, code executes
                    //it means, arrow button is in that location.

                    float clearArrowStart; // for LTR
                    float clearArrowEnd; // for RTL
                    boolean isArrowClicked = false;

                    // get current layout direction
                    if(getLayoutDirection() == LAYOUT_DIRECTION_RTL){
                        clearArrowEnd = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        // use MotionEvent to decide when touch occured
                        if(event.getX() < clearArrowEnd){
                            isArrowClicked = true;
                        }
                    } else {
                        // LTR
                        clearArrowStart = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());
                        if(event.getX() > clearArrowStart){
                            isArrowClicked = true;
                        }
                    }

                    // check if button is tapped
                    if(isArrowClicked){
                        //always occure before action up
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_clear_black_24dp,null);
                            showArrowButton();
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_clear_opaque_24dp,null);
                            getText().clear();
                            hideArrowButton();
                            return true;
                        }
                    }else {
                        return false;
                    }

                }
                return false;
            }
        });
    }

    private void showArrowButton(){
        // set the location of the arrow
        // by order, start of text, top of text, end of text, bottom of text
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null);
    }

    private void hideArrowButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}
