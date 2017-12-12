package com.example.sargiskh.raya.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sargiskh.raya.MainActivity;
import com.example.sargiskh.raya.R;

import java.util.List;

/**
 * Created by sargiskh on 12/11/2017.
 */

public class RecyclerViewAdapter_II extends RecyclerView.Adapter<RecyclerViewAdapter_II.ViewHolder> {

    private List<String> data;
    private int numberOfRows = 0;
    private int numberOfColumns = 0;

    public RecyclerViewAdapter_II(MainActivity activity, List<String> data, int numberOfRows, int numberOfColumns) {
        this.data = data;
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


        for (int ri = 0; ri < numberOfRows; ri++) {
            if (viewHolder.linearLayout.getChildAt(ri) instanceof TextView) {
                final TextView textView = ((TextView) (viewHolder.linearLayout.getChildAt(ri)));

                int pos = position + ri*numberOfColumns;
                if (data.size() != 0 && pos < data.size()) {

                    boolean isOptimal = false;

                    textView.setText(data.get(pos));
                } else {
                    textView.setText("");
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


    public boolean checkIsOptimal(int indexRow, int indexColumn) {

        boolean isOptimal = false;

        int optimalValue = 0;
        for (int i = 1; i < numberOfColumns - 1; i ++) {

            int dataPosition = indexRow*numberOfColumns + indexColumn;
            int ratingPosition = (indexRow + 1) * numberOfColumns - 1;
            int ratingValue = Integer.valueOf(data.get(ratingPosition));
            
            if (ratingValue == 1) {
                if (optimalValue < Integer.valueOf(data.get(dataPosition))) {
                    isOptimal = true;
                }
            } else {

            }


        }
        return isOptimal;
    }
}
