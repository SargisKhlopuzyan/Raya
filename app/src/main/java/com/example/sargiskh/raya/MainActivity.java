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
import com.example.sargiskh.raya.fragments.Fragment_IV;
import com.example.sargiskh.raya.fragments.Fragment_V;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RecyclerViewAdapter_I.EditTextChangedListener {

    public ArrayList<String> originalData = new ArrayList<>();
    public ArrayList<Float> optimalValuesList = new ArrayList<>();
    public ArrayList<String> relativeValuesList = new ArrayList<>();
    public ArrayList<String> relativeRatingValuesList = new ArrayList<>();
    public ArrayList<String> integralValuesList = new ArrayList<>();

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
        viewPagerAdapter.addFragment(new Fragment_IV(), "FRAG IV");
        viewPagerAdapter.addFragment(new Fragment_V(), "FRAG V");
        viewPager.setOffscreenPageLimit(5);
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

            getOptimalValues();
            getRelativeValues();
            getRelativeRatingValues();
            getIntegralValues();

            for (int i = 1; i < viewPagerAdapter.getCount(); i++) {
                if (viewPagerAdapter.getItem(i) instanceof Fragment_II) {
                    ((Fragment_II)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
                } else if (viewPagerAdapter.getItem(i) instanceof Fragment_III) {
                    ((Fragment_III)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
                }  else if (viewPagerAdapter.getItem(i) instanceof Fragment_IV) {
                    ((Fragment_IV)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
                }  else if (viewPagerAdapter.getItem(i) instanceof Fragment_V) {
                    ((Fragment_V)viewPagerAdapter.getItem(i)).notifyOriginalDataChanged();
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

    public void getOptimalValues() {
        optimalValuesList.clear();
        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex ++) {

            int columnStartPosition = (rowIndex) * numberOfColumns + 1;
            int ratingPosition = (rowIndex + 1) * numberOfColumns - 2;
            String ratingValue = originalData.get(ratingPosition);

            String stringColumnStartPosition = originalData.get(columnStartPosition);
            stringColumnStartPosition = stringColumnStartPosition.isEmpty() ? "0" : stringColumnStartPosition;
            float min = Float.valueOf(stringColumnStartPosition);
            float max = Float.valueOf(stringColumnStartPosition);

            for(int k = columnStartPosition; k < columnStartPosition + numberOfColumns - 3; k ++) {
                String stringItemValue = originalData.get(k);
                stringItemValue = stringItemValue.isEmpty() ? "0" : stringItemValue;
                float f = Float.valueOf(stringItemValue);
                if(f < min) min = f;
                if(f > max) max = f;
            }

            if (ratingValue.equals("0")) {
                optimalValuesList.add(min);
            } else {
                optimalValuesList.add(max);
            }
        }
        Log.e("LOG_TAG", "*********** OptimalValuesList ***********");
        Log.e("LOG_TAG", "" + (Arrays.toString(optimalValuesList.toArray())));
        Log.e("LOG_TAG", "**********************");

    }

    public void getRelativeValues() {
        relativeValuesList.clear();
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex ++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {

                int itemPosition = rowIndex * numberOfColumns + columnIndex;

                if (rowIndex == 0 || columnIndex == 0 || columnIndex == numberOfColumns - 2 || columnIndex == numberOfColumns - 1) {
                    relativeValuesList.add(originalData.get(itemPosition));
                } else {

                    String stringItemValue = originalData.get(itemPosition);
                    stringItemValue = stringItemValue.isEmpty() ? "0" : stringItemValue;
                    float itemValue = Float.valueOf(stringItemValue);
                    float optimalValue = optimalValuesList.get(rowIndex - 1);

                    float relativeValue = itemValue / optimalValue;
                    relativeValuesList.add("" + relativeValue);
                }
            }
        }
        Log.e("LOG_TAG", "*********** RelativeValuesList ***********");
        Log.e("LOG_TAG", "" + (Arrays.toString(relativeValuesList.toArray())));
        Log.e("LOG_TAG", "**********************");
    }

    public void getRelativeRatingValues() {
        relativeRatingValuesList.clear();
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex ++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {

                int itemPosition = rowIndex * numberOfColumns + columnIndex;
                int ratingPosition = (rowIndex + 1) * numberOfColumns - 1;

                if (rowIndex == 0 || columnIndex == 0 || columnIndex == numberOfColumns - 2 || columnIndex == numberOfColumns - 1) {
                    relativeRatingValuesList.add(originalData.get(itemPosition));
                } else {

                    String stringItemValue = relativeValuesList.get(itemPosition);
                    String stringRatingValue = originalData.get(ratingPosition);
                    stringItemValue = stringItemValue.isEmpty() ? "0" : stringItemValue;
                    stringRatingValue = stringRatingValue.isEmpty() ? "0" : stringRatingValue;
                    float itemValue = Float.valueOf(stringItemValue);
                    float ratingValue = Float.valueOf(stringRatingValue);
                    float relativeRatingValue = itemValue/ratingValue;
                    relativeRatingValuesList.add("" + relativeRatingValue);
                }
            }
        }
        Log.e("LOG_TAG", "*********** RelativeRatingValuesList ***********");
        Log.e("LOG_TAG", "" + (Arrays.toString(relativeRatingValuesList.toArray())));
        Log.e("LOG_TAG", "**********************");
    }

    public void getIntegralValues() {
        integralValuesList.clear();

        for (int columnIndex = 1; columnIndex < numberOfColumns - 2; columnIndex++) {
            integralValuesList.add(originalData.get(columnIndex));
        }

        for (int columnIndex = 1; columnIndex < numberOfColumns - 2; columnIndex++) {
            float integralValue = 0.0F;
            for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex ++) {
                int itemPosition = rowIndex * numberOfColumns + columnIndex;
                String stringItemValue = relativeRatingValuesList.get(itemPosition);
                stringItemValue = stringItemValue.isEmpty() ? "0" : stringItemValue;
                float itemValue = Float.valueOf(stringItemValue);
                integralValue += itemValue;
            }
            integralValuesList.add("" + integralValue);
        }

        Log.e("LOG_TAG", "*********** IntegralValuesList ***********");
        Log.e("LOG_TAG", "" + (Arrays.toString(integralValuesList.toArray())));
        Log.e("LOG_TAG", "**********************");
    }

}
