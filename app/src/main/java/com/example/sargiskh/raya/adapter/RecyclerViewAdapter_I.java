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

import com.example.sargiskh.raya.MainActivity;
import com.example.sargiskh.raya.R;

import java.util.List;

/**
 * Created by sargiskh on 12/11/2017.
 */

public class RecyclerViewAdapter_I extends RecyclerView.Adapter<RecyclerViewAdapter_I.ViewHolder> {

    public interface EditTextChangedListener {
        public void editTextChanged(int rowIndex, int columnIndex, String value);
    }

    private EditTextChangedListener listener;

    private MainActivity activity;
    private List<String> data;
    private int numberOfRows = 0;
    private int numberOfColumns = 0;

    public RecyclerViewAdapter_I(MainActivity activity, List<String> data, int numberOfRows, int numberOfColumns) {
        this.activity = activity;
        this.listener = activity;
        this.data = data;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public RecyclerViewAdapter_I.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);

        for (int i = 0; i < numberOfRows; i ++) {
            View childView = inflater.inflate(R.layout.edit_text_layout, parent, false);
            ((LinearLayout)view).addView(childView);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_I.ViewHolder viewHolder, final int position) {

        for (int ri = 0; ri < numberOfRows; ri++) {
            int inputType;
            if (position == 0 || ri == 0) {
                inputType = InputType.TYPE_CLASS_TEXT;
            } else {
                inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER;
            }

            if (viewHolder.linearLayout.getChildAt(ri) instanceof EditText) {
                ((EditText)(viewHolder.linearLayout.getChildAt(ri))).setInputType(inputType);
                ((EditText)(viewHolder.linearLayout.getChildAt(ri))).setTag(R.id.all, ri);
                ((EditText)(viewHolder.linearLayout.getChildAt(ri))).setTag(R.id.always, position);

                final EditText editText = ((EditText)(viewHolder.linearLayout.getChildAt(ri)));
                editText.setText(data.get(position + ri*numberOfColumns));

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        int rowIndex = ((Integer) editText.getTag(R.id.all)).intValue();
                        int columnIndex = ((Integer) editText.getTag(R.id.always)).intValue();
                        listener.editTextChanged(rowIndex, columnIndex, charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
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
