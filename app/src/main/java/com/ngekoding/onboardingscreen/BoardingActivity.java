package com.ngekoding.onboardingscreen;

import android.animation.ArgbEvaluator;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardingActivity extends AppCompatActivity {

    private SectionsPagerAdapter boardingPagerAdapter;
    private ViewPager boardingViewPager;

    private ImageButton btnNext;
    private Button btnSkip, btnFinish;

    private ImageView imgZero, imgOne, imgTwo;
    private ImageView[] imgIndicators;

    private int lastLeftValue = 0;
    private int page = 0;

    private CoordinatorLayout boardingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);

        getInit();
    }

    private void getInit() {
        btnNext = (ImageButton) findViewById(R.id.ib_boarding_next);
        btnSkip = (Button) findViewById(R.id.btn_boarding_skip);
        btnFinish = (Button) findViewById(R.id.btn_boarding_finish);

        imgZero = (ImageView) findViewById(R.id.iv_boarding_indicator_0);
        imgOne = (ImageView) findViewById(R.id.iv_boarding_indicator_1);
        imgTwo = (ImageView) findViewById(R.id.iv_boarding_indicator_2);
        imgIndicators = new ImageView[]{imgZero, imgOne, imgTwo};

        boardingViewPager = (ViewPager) findViewById(R.id.boarding_container);

        boardingPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        boardingViewPager.setAdapter(boardingPagerAdapter);

        boardingViewPager.setCurrentItem(page);
        updateIndicators(page);

        final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color2 = ContextCompat.getColor(this, R.color.orange);
        final int color3 = ContextCompat.getColor(this, R.color.green);

        final int[] colorList = new int[]{color1, color2, color3};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        boardingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                boardingViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                updateIndicators(page);
                switch (position) {
                    case 0:
                        boardingViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        boardingViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        boardingViewPager.setBackgroundColor(color3);
                        break;
                }

                btnNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                btnFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page += 1;
                boardingViewPager.setCurrentItem(page, true);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                // Save to cache
            }
        });
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < imgIndicators.length; i++) {
            imgIndicators[i].setBackgroundResource(
                    i == position ? R.drawable.boarding_indicator_selected : R.drawable.boarding_indicator_unselected
            );
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private ImageView img;
        private int[] backgrounds = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_flight_24dp, R.drawable.ic_flight_24dp};

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_boarding, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            img = (ImageView) rootView.findViewById(R.id.section_img);
            img.setBackgroundResource(backgrounds[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
