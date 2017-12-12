package com.example.sargiskh.raya;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.sargiskh.raya.adapter.RecyclerViewAdapter_I;
import com.example.sargiskh.raya.adapter.ViewPagerAdapter;
import com.example.sargiskh.raya.fragments.Fragment_I;
import com.example.sargiskh.raya.fragments.Fragment_II;
import com.example.sargiskh.raya.fragments.Fragment_III;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RecyclerViewAdapter_I.EditTextChangedListener {

    public ArrayList<String> originalData = new ArrayList<>();
    public boolean isOriginalDataChanged = false;

    public int numberOfRows = 1;
    public int numberOfColumns = 1;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

//        // Add Fragments to viewPagerAdapter one by one
        viewPagerAdapter.addFragment(new Fragment_I(), "FRAG I");
        viewPagerAdapter.addFragment(new Fragment_II(), "FRAG II");
        viewPagerAdapter.addFragment(new Fragment_III(), "FRAG III");
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (isOriginalDataChanged) {
            for (int i = 1; i < viewPagerAdapter.getCount(); i++) {
                if (viewPagerAdapter.getItem(i) instanceof Fragment_II) {
                    ((Fragment_II)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
                } else if (viewPagerAdapter.getItem(i) instanceof Fragment_III) {
                    ((Fragment_III)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
                }
            }
            isOriginalDataChanged = false;
        }
        hideSoftKeyboard();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void editTextChanged(int rowIndex, int columnIndex, String value) {
        originalData.set(columnIndex + rowIndex*numberOfColumns, value);
        isOriginalDataChanged = true;
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            Log.e("LOG_TAG", "not a null");
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } else {
            Log.e("LOG_TAG", "null");
        }
    }
}
