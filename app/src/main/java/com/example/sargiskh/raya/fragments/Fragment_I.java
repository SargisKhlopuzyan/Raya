package com.example.sargiskh.raya.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sargiskh.raya.MainActivity;
import com.example.sargiskh.raya.R;
import com.example.sargiskh.raya.adapter.RecyclerViewAdapter_I;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_I extends Fragment {

    public EditText editTextNumberOfRows;
    public EditText editTextNumberOfColumns;
    public Button buttonSave;
    public RecyclerView recyclerView;

    private RecyclerViewAdapter_I recyclerViewAdapter;

    private MainActivity activity;

    public Fragment_I() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("LOG_TAG", "onCreateView: Fragment_I");

        activity = (MainActivity)getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__i, container, false);
        editTextNumberOfRows = view.findViewById(R.id.editTextNumberOfRows);
        editTextNumberOfColumns = view.findViewById(R.id.editTextNumberOfColumns);
        buttonSave = view.findViewById(R.id.buttonSave);
        recyclerView = view.findViewById(R.id.recyclerView);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRecyclerView();
                activity.isOriginalDataChanged = true;
            }
        });
        return view;
    }

    private void createRecyclerView() {
        String stringNumberOfRows = editTextNumberOfRows.getText().toString();
        String stringNumberOfColumns = editTextNumberOfColumns.getText().toString();

        if(stringNumberOfRows.isEmpty()) stringNumberOfRows = "0";
        if(stringNumberOfColumns.isEmpty()) stringNumberOfColumns = "0";
        activity.numberOfRows = Integer.parseInt(stringNumberOfRows);
        activity.numberOfColumns = Integer.parseInt(stringNumberOfColumns);

        activity.originalData.clear();
        int index = 0;
        for (int i = 0; i< activity.numberOfColumns*activity.numberOfRows; i++) {
            if (i < activity.numberOfColumns || (i % activity.numberOfColumns) == 0) {
                activity.originalData.add("No " + index);
                ++index;
            } else {
                activity.originalData.add("");
            }
        }

        recyclerViewAdapter = new RecyclerViewAdapter_I(activity, activity.originalData, activity.numberOfRows, activity.numberOfColumns);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
