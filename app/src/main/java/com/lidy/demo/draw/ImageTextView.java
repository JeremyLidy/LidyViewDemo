package com.lidy.demo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.lidy.demo.Utils;

/**
 *  文字换行处理
 * @author lideyou
 */
public class ImageTextView extends View {


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    StaticLayout staticLayout;

    private String str = "The goal of CatchUp was simple. I was living in Palo Alto at the time and commuting to the city every day for work, which left me with a lot of time on the train. At the end of the day, I wanted to wind down and catch up on the news and posts of the day. There’s tons of aggregators/readers out there, but I wanted to make something unique and just for me. I didn’t want an RSS reader, nor something that would burden me with keeping meticulous track of all my “unreads” or any of that silliness. At the end of the day, there were several services I like to read (reddit, hacker news, etc). Each has some form of dedicated app, but I didn’t want to deep dive into each just to get a digest. I’d seen some apps that took steps in this direction, but I thought it was a good opportunity to try to do it right and build something for myself.";

    {
        paint.setTextSize(Utils.dp2px(15));
        textPaint.setTextSize(Utils.dp2px(15));

    }

    public ImageTextView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        // textView 与 imageView 相关的排版
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            staticLayout = StaticLayout.Builder
                    .obtain(str, 0, str.length(), textPaint, getWidth()).build();
        } else {
            staticLayout = new StaticLayout(str, textPaint, getWidth(), Alignment.ALIGN_NORMAL, 0,
                    0, false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        staticLayout.draw(canvas);
    }
}
