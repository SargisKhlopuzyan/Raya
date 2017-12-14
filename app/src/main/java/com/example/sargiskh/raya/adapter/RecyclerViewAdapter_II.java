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

public class RecyclerViewAdapter_II extends RecyclerView.Adapter<RecyclerViewAdapter_II.ViewHolder> {

    private List<String> data;
    private List<Float> optimalValuesList;
    private int numberOfRows = 0;
    private int numberOfColumns = 0;

    public RecyclerViewAdapter_II(List<String> data, List<Float> optimalValuesList, int numberOfRows, int numberOfColumns) {
        this.data = data;
        this.optimalValuesList = optimalValuesList;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public RecyclerViewAdapter_II.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);

        for (int i = 0; i < numberOfRows; i ++) {
            View childView = inflater.inflate(R.layout.text_view_layout, parent, false);
            ((LinearLayout)view).addView(childView);
        }

        RecyclerViewAdapter_II.ViewHolder viewHolder = new RecyclerViewAdapter_II.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_II.ViewHolder viewHolder, final int position) {
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            if (viewHolder.linearLayout.getChildAt(rowIndex) instanceof TextView) {
                final TextView textView = (TextView)viewHolder.linearLayout.getChildAt(rowIndex);

                textView.setBackgroundResource(R.drawable.recycler_view_item_rounded_background);
                int itemPosition = position + rowIndex * numberOfColumns;
                if (position == 0 || rowIndex == 0 || position == (numberOfColumns - 1)) {
                    if (rowIndex == 0 && position == numberOfColumns - 2) {
                        textView.setText("Optimal");
                    } else {
                        textView.setText(data.get(itemPosition));
                    }
                } else if (position == (numberOfColumns - 2)) {
                    String optimalValue = data.get(itemPosition).equals("0") ? "Min" : "Max";
                    textView.setText(optimalValue);;
                } else {
                    String stringValue = (data.get(itemPosition)).isEmpty() ? "0" : data.get(itemPosition);
                    textView.setText(stringValue);
                    float value = Float.valueOf(stringValue);
                    float optimalValue = optimalValuesList.get(rowIndex - 1);
                    if (value == optimalValue) {
                        textView.setBackgroundResource(R.drawable.optimal_recycler_view_item_rounded_background);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return numberOfColumns;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}
