package com.example.liferestart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private Paint dividerPaint;

    public RecyclerViewDecoration(Context context) {
        dividerHeight = context.getResources().getDimensionPixelOffset(R.dimen.divider_height);
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.pale));

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();
        for(int i=0;i<childCount;i++){
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = top+dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}

