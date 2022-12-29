package com.codecraft.sifardenggi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView title;
    public String stateSelected;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.toolbarText);

        boolean isConnectedInternet = isConnectedToInternet(this);
        if (!isConnectedInternet){
            Snackbar.make(findViewById(android.R.id.content), "Tiada capaian internet didapati", Snackbar.LENGTH_INDEFINITE)
                    .setAction("TUTUP", view -> {
                        finishAffinity();
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        }
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);

        stateSelected = sharedPref.getString("statename", "JOHOR");
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()); //view pager adapter
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) title.setText("SIFAR DENGGI");
                else if (position == 1) title.setText("Statistik");
                else if (position == 2) title.setText("Informasi Kesedaran Bahaya Denggi");
                else if (position == 3) {
                    title.setText("Klinik Berdekatan");
                    mapFragment.openKlinik();
                }
                else if (position == 4) title.setText("Risiko Jangkitan");
                else if (position == 5) title.setText("Info");

                if (position == 0) title.setGravity(Gravity.CENTER_HORIZONTAL);
                else title.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        AnimatedBottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setupWithViewPager(viewPager);
    }

    public void setData(List<String> state, List<Integer> daily, List<Integer> cumulative, String yesterdayDate, String cumDate){
        int index = 0;

        for (int i=0;i < state.size();i++) {
//            homeFragment.stateList.add(state.get(i));
            if (state.get(i).equals(stateSelected)) {
                Log.e("state", stateSelected);
                index = i;
            };
        }

        String stateDaily = daily.get(index).toString();
        String stateCumulative = cumulative.get(index).toString();

        homeFragment.setText(stateDaily, stateCumulative, sum(daily).toString(), sum(cumulative).toString(), yesterdayDate, cumDate);
    }

    Integer sum(List<Integer> m){
        Integer sum = 0;
        for(int i = 0; i < m.size(); i++)
            sum += m.get(i);
        return sum;
    }

    HomeFragment homeFragment;
    MapFragment mapFragment;

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HomeFragment(stateSelected);//create any fragment
                    homeFragment = (HomeFragment) fragment;

                    break;
                case 1:
                    fragment = new StatisticFragment(MainActivity.this);//create any fragment
                    break;
                case 2:
                    fragment = new PosterFragment();//create any fragment
                    break;
                case 3:
                    fragment = new MapFragment();//create any fragment
                    mapFragment = (MapFragment) fragment;
                    break;
                case 4:
                    fragment = new RiskFragment();//create any fragment
                    break;
                case 5:
                    fragment = new AboutFragment();//create any fragment
                    break;
            }
            assert fragment != null;
            return fragment;
        }

        @Override
        public int getCount() {
            // Show total number of pages.
            return 6;
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar dari aplikasi ini", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}