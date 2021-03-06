package com.example.sargiskh.raya.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sargiskh.raya.R;

import java.util.List;

/**
 * Created by sargiskh on 12/11/2017.
 */

public class RecyclerViewAdapter_V extends RecyclerView.Adapter<RecyclerViewAdapter_V.ViewHolder> {

    private List<String> integralValuesList;
    private float integralIndicatorValue = 0.0F;
    private int numberOfRows = 0;
    private int numberOfColumns = 0;

    public RecyclerViewAdapter_V(List<String> integralValuesList, float integralIndicatorValue, int numberOfRows, int numberOfColumns) {
        this.integralValuesList = integralValuesList;
        this.integralIndicatorValue = integralIndicatorValue;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public RecyclerViewAdapter_V.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);

        for (int i = 0; i < 2; i ++) {
            View childView = inflater.inflate(R.layout.text_view_layout, parent, false);
            ((LinearLayout)view).addView(childView);
        }

        RecyclerViewAdapter_V.ViewHolder viewHolder = new RecyclerViewAdapter_V.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_V.ViewHolder viewHolder, final int position) {

        for (int rowIndex = 0; rowIndex < 2; rowIndex++) {

            if (viewHolder.linearLayout.getChildAt(rowIndex) instanceof TextView) {
                final TextView textView = ((TextView) (viewHolder.linearLayout.getChildAt(rowIndex)));

                textView.setBackgroundResource(R.drawable.recycler_view_item_rounded_background);

                int itemPosition = position + rowIndex * (numberOfColumns - 3);
                String stringItemValue = (integralValuesList.get(itemPosition)).isEmpty() ? "0" : integralValuesList.get(itemPosition);

                textView.setText(stringItemValue);

                if (rowIndex != 0) {
                    float value = Float.valueOf(stringItemValue);
                    if (value == integralIndicatorValue) {
                        textView.setBackgroundResource(R.drawable.optimal_recycler_view_item_rounded_background);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return numberOfColumns - 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}
