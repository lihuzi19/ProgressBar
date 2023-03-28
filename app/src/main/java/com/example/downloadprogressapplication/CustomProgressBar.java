package com.example.downloadprogressapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

public class CustomProgressBar extends ProgressBar {

    private Paint mPaint;
    private String mText;

    public CustomProgressBar(Context context) {
        this(context, null);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, androidx.appcompat.R.style.Widget_AppCompat_ProgressBar_Horizontal);
        init();
    }

    private void init() {
        setProgressDrawable(ContextCompat.getDrawable(getContext(),
                R.drawable.background_downloade_progressbar));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(getContext(), 14));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (TextUtils.isEmpty(mText)) {
            mText = "";
        }
        // 绘制文本
        Rect rect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), rect);
        float textX = (getWidth() / 2) ;
        float textY = (getHeight() / 2) - rect.centerY();
        mPaint.setColor(getResources().getColor(R.color.progress_color));
        canvas.drawText(mText, textX, textY, mPaint);
        mPaint.setColor(getResources().getColor(R.color.progress_bg_color));
        canvas.clipRect(0, 0, getProgress() * getWidth() / getMax(), getHeight());
        canvas.drawText(mText, textX, textY, mPaint);
    }

    public void setText(String text) {
        mText = text;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
