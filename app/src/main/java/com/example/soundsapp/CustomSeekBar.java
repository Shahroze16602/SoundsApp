package com.example.soundsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatSeekBar;

public class CustomSeekBar extends AppCompatSeekBar {
    Context context;
    private Paint linePaint;
    private Paint donePaint;
    float lineCount = 50;

    public CustomSeekBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(4f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        donePaint = new Paint();
        donePaint.setColor(Color.BLACK);
        donePaint.setAntiAlias(true);
        donePaint.setStrokeWidth(4f);
        donePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        donePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        float lineSpacing = (width /  lineCount) - 1.5f;
        int value = 1;
        int increment = 1;
        for (int i = 0; i < lineCount; i++) {
            if (value == 7) {
                increment = -1;
            } else if (value == 1) {
                increment = 1;
            }
            value += increment;
            float lineX = i * lineSpacing + 40;
            float centerY = height / 2;
            float lineHeight = (height / 2) - (value * (height / 7)) + 1.5f;
            float startY = centerY - lineHeight;
            float endY = centerY + lineHeight;
            float cornerRadius = 20.0f;
            @SuppressLint("DrawAllocation") RectF rect = new RectF(lineX - 3, startY, lineX + 3, endY);
            if (i > (getProgress() * lineCount) / getMax()) {
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, linePaint);
            } else {
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, donePaint);
            }
        }
        super.onDraw(canvas);
    }
}