package com.example.sargiskh.raya;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.sargiskh.raya.adapter.RecyclerViewAdapter_I;
import com.example.sargiskh.raya.adapter.ViewPagerAdapter;
import com.example.sargiskh.raya.fragments.Fragment_II;
import com.example.sargiskh.raya.fragments.Fragment_III;
import com.example.sargiskh.raya.fragments.Fragment_IV;
import com.example.sargiskh.raya.fragments.Fragment_V;
import com.example.sargiskh.raya.fragments.MainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RecyclerViewAdapter_I.EditTextChangedListener {

    public ArrayList<String> originalValuesList = new ArrayList<>();
    public ArrayList<Float> optimalValuesList = new ArrayList<>();
    public ArrayList<String> relativeValuesList = new ArrayList<>();
    public ArrayList<String> relativeRatingValuesList = new ArrayList<>();
    public ArrayList<String> integralValuesList = new ArrayList<>();
    public ArrayList<String> integralIndicatorValuesCouple = new ArrayList<>();
    public float integralIndicatorValue = 0.0F;

    public boolean isOriginalDataChanged = false;

    public int numberOfRows = 1;
    public int numberOfColumns = 1;


    private NavigationView navigationView;
//    private DrawerLayout drawer;
    private View navHeader;

    public EditText editTextNumberOfRows;
    public EditText editTextNumberOfColumns;
    public Button buttonSave;

    public MainFragment mainFragment = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);

        editTextNumberOfRows = navHeader.findViewById(R.id.editTextNumberOfRows);
        editTextNumberOfColumns = navHeader.findViewById(R.id.editTextNumberOfColumns);
        buttonSave = navHeader.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRecyclerView();
                isOriginalDataChanged = true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
            getIntegralIndicatorValue();

            ViewPagerAdapter viewPagerAdapter = mainFragment.viewPagerAdapter;
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
        originalValuesList.set(columnIndex + rowIndex*numberOfColumns, value);
        isOriginalDataChanged = true;
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void getOptimalValues() {
        optimalValuesList.clear();
        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex ++) {

            int columnStartPosition = (rowIndex) * numberOfColumns + 1;
            int ratingPosition = (rowIndex + 1) * numberOfColumns - 2;
            String ratingValue = originalValuesList.get(ratingPosition);

            String stringColumnStartPosition = originalValuesList.get(columnStartPosition);
            stringColumnStartPosition = stringColumnStartPosition.isEmpty() ? "0" : stringColumnStartPosition;
            float min = Float.valueOf(stringColumnStartPosition);
            float max = Float.valueOf(stringColumnStartPosition);

            for(int k = columnStartPosition; k < columnStartPosition + numberOfColumns - 3; k ++) {
                String stringItemValue = originalValuesList.get(k);
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
    }

    public void getRelativeValues() {
        relativeValuesList.clear();
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex ++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {

                int itemPosition = rowIndex * numberOfColumns + columnIndex;

                if (rowIndex == 0 || columnIndex == 0 || columnIndex == numberOfColumns - 2 || columnIndex == numberOfColumns - 1) {
                    relativeValuesList.add(originalValuesList.get(itemPosition));
                } else {

                    String stringItemValue = originalValuesList.get(itemPosition);
                    stringItemValue = stringItemValue.isEmpty() ? "0" : stringItemValue;
                    float itemValue = Float.valueOf(stringItemValue);
                    float optimalValue = optimalValuesList.get(rowIndex - 1);

                    float relativeValue = itemValue / optimalValue;
                    relativeValuesList.add("" + relativeValue);
                }
            }
        }
    }

    public void getRelativeRatingValues() {
        relativeRatingValuesList.clear();
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex ++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {

                int itemPosition = rowIndex * numberOfColumns + columnIndex;
                int ratingPosition = (rowIndex + 1) * numberOfColumns - 1;

                if (rowIndex == 0 || columnIndex == 0 || columnIndex == numberOfColumns - 2 || columnIndex == numberOfColumns - 1) {
                    relativeRatingValuesList.add(originalValuesList.get(itemPosition));
                } else {

                    String stringItemValue = relativeValuesList.get(itemPosition);
                    String stringRatingValue = originalValuesList.get(ratingPosition);
                    stringItemValue = stringItemValue.isEmpty() ? "0" : stringItemValue;
                    stringRatingValue = stringRatingValue.isEmpty() ? "0" : stringRatingValue;
                    float itemValue = Float.valueOf(stringItemValue);
                    float ratingValue = Float.valueOf(stringRatingValue);
                    float relativeRatingValue = itemValue/ratingValue;
                    relativeRatingValuesList.add("" + relativeRatingValue);
                }
            }
        }
    }

    public void getIntegralValues() {
        integralValuesList.clear();

        for (int columnIndex = 1; columnIndex < numberOfColumns - 2; columnIndex++) {
            integralValuesList.add(originalValuesList.get(columnIndex));
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
    }

    public void getIntegralIndicatorValue() {
        integralIndicatorValuesCouple.clear();

        if (integralValuesList.size() == 0) {
            return;
        }
        int startPosition = numberOfColumns - 3;
        String stringStartPosition = integralValuesList.get(startPosition);
        stringStartPosition = stringStartPosition.isEmpty() ? "0" : stringStartPosition;
        float min = Float.valueOf(stringStartPosition);

        for(int itemPosition = startPosition; itemPosition < 2 * startPosition; itemPosition ++) {
            String stringItemValue = integralValuesList.get(itemPosition);
            stringItemValue = stringItemValue.isEmpty() ? "0" : stringItemValue;
            float f = Float.valueOf(stringItemValue);
            if(f < min) min = f;
        }
        integralIndicatorValue = min;
    }

    private void createRecyclerView() {
        String stringNumberOfRows = editTextNumberOfRows.getText().toString();
        String stringNumberOfColumns = editTextNumberOfColumns.getText().toString();

        if(stringNumberOfRows.isEmpty()) stringNumberOfRows = "0";
        if(stringNumberOfColumns.isEmpty()) stringNumberOfColumns = "0";

        numberOfRows = Integer.parseInt(stringNumberOfRows) + 1;
        numberOfColumns = Integer.parseInt(stringNumberOfColumns) + 3;

        originalValuesList.clear();
        for (int i = 0; i < numberOfColumns * numberOfRows; i++) {
            if (i == numberOfColumns - 2) {
                originalValuesList.add("Optimal");
            } else if (i == numberOfColumns - 1) {
                originalValuesList.add("Rating");
            } else if (i < numberOfColumns || (i % numberOfColumns) == 0) {
                originalValuesList.add("");
            } else {
                originalValuesList.add("");
            }
        }

        getOptimalValues();
        getRelativeValues();
        getRelativeRatingValues();
        getIntegralValues();
        getIntegralIndicatorValue();

        mainFragment.notifyOriginalDataChanged();
    }
}
