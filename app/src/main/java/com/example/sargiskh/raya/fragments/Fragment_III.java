package com.example.sargiskh.raya.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sargiskh.raya.MainActivity;
import com.example.sargiskh.raya.R;
import com.example.sargiskh.raya.adapter.RecyclerViewAdapter_II;
import com.example.sargiskh.raya.adapter.RecyclerViewAdapter_III;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_III extends Fragment {

    private MainActivity activity;
    private RecyclerViewAdapter_III recyclerViewAdapter;
    public RecyclerView recyclerView;

    public Fragment_III() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (MainActivity)getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__iii, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    public void notifyOriginalDataChanged() {
        recyclerViewAdapter = new RecyclerViewAdapter_III(activity.relativeValuesList, activity.numberOfRows, activity.numberOfColumns);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
