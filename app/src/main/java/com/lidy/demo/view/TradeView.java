package com.lidy.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.system.Os;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.lidy.demo.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lideyou
 */
public class TradeView extends View {

    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    FontMetrics metrics;

    private Rect mBounds = new Rect();
    private Rect mBounds2 = new Rect();

    float offsetX, offsetY;
    float offsetX1;

    private final static int PADDING = (int) Utils.dp2px(16);
    private final static int PADDING_TOP = (int) Utils.dp2px(6);

    public TradeView(Context context) {
        super(context);
    }

    public TradeView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TradeView(Context context,
            @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(Utils.dp2px(15));
        textPaint.setStyle(Style.FILL);
        textPaint.setTextAlign(Align.LEFT);

        metrics = textPaint.getFontMetrics();
        paint.setColor(Color.GRAY);
        textPaint.getTextBounds("0.148", 0, "0.148".length(), mBounds);
        textPaint.getTextBounds("4026.88", 0, "4026.88".length(), mBounds2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        offsetX = getWidth() - mBounds.width() - Utils.dp2px(6);
        offsetX1 = offsetX - mBounds2.width() - PADDING;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        offsetY = mBounds.height() + PADDING_TOP;
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        for (int i = 1; i < getTestData().size() + 1; i++) {
            Item item = getTestData().get(i - 1);
            canvas.drawText("" + i, 0, offsetY, textPaint);
            canvas.drawText(item.price, offsetX1, offsetY, textPaint);
            canvas.drawText(item.count, offsetX, offsetY, textPaint);
            offsetY += mBounds.height() + PADDING_TOP;
        }

    }

    public List<Item> getTestData() {

        List<Item> items = new ArrayList<>();
        items.add(new Item("4026.88", "0.148"));
        items.add(new Item("4026.87", "1.148"));
        items.add(new Item("4026.86", "1.45K"));
        items.add(new Item("4026.86", "70.21"));
        items.add(new Item("4026.86", "179.1"));
        items.add(new Item("--", "--"));
        return items;
    }

    protected class Item {

        public String price = "4026.88";
        public String count = "0.0148";

        public Item(String price, String count) {
            this.price = price;
            this.count = count;
        }
    }
}
