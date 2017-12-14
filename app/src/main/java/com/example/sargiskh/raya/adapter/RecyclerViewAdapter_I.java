package com.example.sargiskh.raya.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.sargiskh.raya.MainActivity;
import com.example.sargiskh.raya.R;
import com.example.sargiskh.raya.enums.RowType;

import java.util.List;

/**
 * Created by sargiskh on 12/11/2017.
 */

public class RecyclerViewAdapter_I extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public int getItemViewType(int position) {
        if (position == numberOfColumns - 2) {
            return RowType.ROW_TYPE_OPTIMAL;
        } else if (position == numberOfColumns - 1) {
            return RowType.ROW_TYPE_RATING;
        }
        return RowType.ROW_TYPE_NORMAL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);

        if (viewType == RowType.ROW_TYPE_OPTIMAL) {

            for (int i = 0; i < numberOfRows; i ++) {
                View childView = inflater.inflate(R.layout.layout_optimal_edit_text, parent, false);
                ((LinearLayout)view).addView(childView);
            }

            ViewHolder_OPTIMAL viewHolder = new ViewHolder_OPTIMAL(view);
            return viewHolder;

        } else if (viewType == RowType.ROW_TYPE_RATING) {

            for (int i = 0; i < numberOfRows; i ++) {
                View childView = inflater.inflate(R.layout.layout_rating_edit_text, parent, false);
                ((LinearLayout)view).addView(childView);
            }

            ViewHolder_RATING viewHolder = new ViewHolder_RATING(view);
            return viewHolder;
        } else {

            for (int i = 0; i < numberOfRows; i ++) {
                View childView = inflater.inflate(R.layout.layout_normal_edit_text, parent, false);
                ((LinearLayout)view).addView(childView);
            }

            ViewHolder_NORMAL viewHolder = new ViewHolder_NORMAL(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder_OPTIMAL) {

            for (int ri = 0; ri < numberOfRows; ri++) {
                if (((ViewHolder_OPTIMAL)viewHolder).linearLayout.getChildAt(ri) instanceof Button) {
                    final Button button = ((Button)(((ViewHolder_OPTIMAL)viewHolder).linearLayout.getChildAt(ri)));

                    if (ri == 0) {
                        button.setText("Optimal");
                        continue;
                    }

                    (((ViewHolder_OPTIMAL)viewHolder).linearLayout.getChildAt(ri)).setTag(R.id.all, ri);
                    (((ViewHolder_OPTIMAL)viewHolder).linearLayout.getChildAt(ri)).setTag(R.id.always, position);


                    String orderStr = data.get(position + ri*numberOfColumns);
                    if (orderStr.isEmpty()) {
                        button.setText("Min");
                        data.set(position + ri*numberOfColumns, "0");
                    } else if (orderStr == "0"){
                        button.setText("Min");
                    } else {
                        button.setText("Max");
                    }

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int rowIndex = ((Integer) button.getTag(R.id.all)).intValue();
                            int columnIndex = ((Integer) button.getTag(R.id.always)).intValue();
                            String order = "0";
                            if (button.getText().toString() == "Min") {
                                order = "1";
                                button.setText("Max");
                            } else {
                                button.setText("Min");
                            }
                            listener.editTextChanged(rowIndex, columnIndex, order);
                        }
                    });
                }
            }

        } else if (viewHolder instanceof ViewHolder_RATING) {

            for (int ri = 0; ri < numberOfRows; ri++) {
                int inputType;
                if (position == 0 || ri == 0) {
                    inputType = InputType.TYPE_CLASS_TEXT;
                } else {
                    inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER;
                }

                if (((ViewHolder_RATING)viewHolder).linearLayout.getChildAt(ri) instanceof EditText) {
                    ((EditText)(((ViewHolder_RATING)viewHolder).linearLayout.getChildAt(ri))).setInputType(inputType);
                    (((ViewHolder_RATING)viewHolder).linearLayout.getChildAt(ri)).setTag(R.id.all, ri);
                    (((ViewHolder_RATING)viewHolder).linearLayout.getChildAt(ri)).setTag(R.id.always, position);

                    final EditText editText = ((EditText)(((ViewHolder_RATING)viewHolder).linearLayout.getChildAt(ri)));
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

        } else {
            for (int ri = 0; ri < numberOfRows; ri++) {
                int inputType;
                if (position == 0 || ri == 0) {
                    inputType = InputType.TYPE_CLASS_TEXT;
                } else {
                    inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER;
                }

                if (((ViewHolder_NORMAL)viewHolder).linearLayout.getChildAt(ri) instanceof EditText) {
                    ((EditText)(((ViewHolder_NORMAL)viewHolder).linearLayout.getChildAt(ri))).setInputType(inputType);
                    ((EditText)(((ViewHolder_NORMAL)viewHolder).linearLayout.getChildAt(ri))).setTag(R.id.all, ri);
                    ((EditText)(((ViewHolder_NORMAL)viewHolder).linearLayout.getChildAt(ri))).setTag(R.id.always, position);

                    final EditText editText = ((EditText)(((ViewHolder_NORMAL)viewHolder).linearLayout.getChildAt(ri)));
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
    }

    @Override
    public int getItemCount() {
        return numberOfColumns;
    }


    public class ViewHolder_NORMAL extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;

        public ViewHolder_NORMAL(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }

    public class ViewHolder_OPTIMAL extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;

        public ViewHolder_OPTIMAL(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }

    public class ViewHolder_RATING extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;

        public ViewHolder_RATING(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}
