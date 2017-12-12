package com.example.sargiskh.raya.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sargiskh.raya.MainActivity;
import com.example.sargiskh.raya.R;

import java.util.List;

/**
 * Created by sargiskh on 12/11/2017.
 */

public class RecyclerViewAdapter_III extends RecyclerView.Adapter<RecyclerViewAdapter_III.ViewHolder> {

    private List<String> data;
    private int numberOfRows = 0;
    private int numberOfColumns = 0;

    public RecyclerViewAdapter_III(MainActivity activity, List<String> data, int numberOfRows, int numberOfColumns) {
        this.data = data;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public RecyclerViewAdapter_III.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);

        for (int i = 0; i < numberOfRows; i ++) {
            View childView = inflater.inflate(R.layout.text_view_layout, parent, false);
            ((LinearLayout)view).addView(childView);
        }

        RecyclerViewAdapter_III.ViewHolder viewHolder = new RecyclerViewAdapter_III.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_III.ViewHolder viewHolder, final int position) {

        for (int ri = 0; ri < numberOfRows; ri++) {
            if (viewHolder.linearLayout.getChildAt(ri) instanceof TextView) {
                final TextView textView = ((TextView) (viewHolder.linearLayout.getChildAt(ri)));

                int pos = position + ri*numberOfColumns;
                if (data.size() != 0 && pos < data.size()) {
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
}
