package com.example.downloadprogressapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;


public class DownloadProgressBar extends ProgressBar {

    private Context context;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private float progress;
    private String progressText = "";
    private int backgroundColor;
    private int progressColor;

    public DownloadProgressBar(Context context) {
        this(context, null);
        this.context = context;
        init();
    }

    public DownloadProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0, androidx.appcompat.R.style.Widget_AppCompat_ProgressBar_Horizontal);
        this.context = context;
        init();
    }

    /**
     * 设置进度中的文字
     *
     * @param progressText
     */
    public void setProgressState(String progressText) {
        if (!TextUtils.isEmpty(progressText)) {
            this.progressText = progressText;
        } else {
            this.progressText = "";
        }
        invalidate();
    }

    /**
     * 设置下载进度
     */
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        this.progress = progress;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = progressText;
        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);

        float textX = (getWidth() / 2) - textRect.centerX();
        float textY = (getHeight() / 2) - textRect.centerY();
        paint.setColor(ContextCompat.getColor(context, progressColor));
        canvas.drawText(text, textX, textY, paint);

        Bitmap bufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(bufferBitmap);
        bufferCanvas.drawText(text, textX, textY, paint);
        // 设置混合模式
        paint.setXfermode(porterDuffXfermode);
        paint.setColor(ContextCompat.getColor(context, backgroundColor));
        RectF rectF = new RectF(0, 0, getWidth() * progress / 100, getHeight());
        // 绘制源图形
        bufferCanvas.drawRect(rectF, paint);
        // 绘制目标图
        canvas.drawBitmap(bufferBitmap, 0, 0, null);
        // 清除混合模式
        paint.setXfermode(null);

        if (!bufferBitmap.isRecycled()) {
            bufferBitmap.recycle();
        }
    }

    private void init() {
        setIndeterminate(false);
        setIndeterminateDrawable(ContextCompat.getDrawable(context,
                android.R.drawable.progress_indeterminate_horizontal));
        setProgressDrawable(ContextCompat.getDrawable(context,
                R.drawable.background_downloade_progressbar));
        setMax(100);

        backgroundColor = R.color.progress_bg_color;
        progressColor = R.color.progress_color;

        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(sp2px(context, 14));
        paint.setTypeface(Typeface.MONOSPACE);

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
