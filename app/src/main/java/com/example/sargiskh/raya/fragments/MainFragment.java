package com.example.sargiskh.raya.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sargiskh.raya.MainActivity;
import com.example.sargiskh.raya.R;
import com.example.sargiskh.raya.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ViewPager viewPager;
    public ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
//        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

//        // Add Fragments to viewPagerAdapter one by one
        viewPagerAdapter.addFragment(new Fragment_I(), "PAGE I");
        viewPagerAdapter.addFragment(new Fragment_II(), "PAGE II");
        viewPagerAdapter.addFragment(new Fragment_III(), "PAGE III");
        viewPagerAdapter.addFragment(new Fragment_IV(), "PAGE IV");
        viewPagerAdapter.addFragment(new Fragment_V(), "PAGE V");
        viewPager.setOffscreenPageLimit(5);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener((MainActivity)getActivity());

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public void notifyOriginalDataChanged() {
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            if (viewPagerAdapter.getItem(i) instanceof Fragment_I) {
                ((Fragment_I)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
            } else if (viewPagerAdapter.getItem(i) instanceof Fragment_II) {
                ((Fragment_II)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
            } else if (viewPagerAdapter.getItem(i) instanceof Fragment_III) {
                ((Fragment_III)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
            }  else if (viewPagerAdapter.getItem(i) instanceof Fragment_IV) {
                ((Fragment_IV)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
            }  else if (viewPagerAdapter.getItem(i) instanceof Fragment_V) {
                ((Fragment_V)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
            }
        }
    }

}
