package com.infinity.EBacSens.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapterPagerMain;
import com.infinity.EBacSens.data_sqllite.DBManager;

public class MainActivity extends AppCompatActivity  {

    private DBManager dbManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addController();
        addEvents();
    }

    private void addEvents() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TextView tvhome, tvstore, tvevent, tvuser;

                tvhome = tabLayout.getTabAt(0).getCustomView().findViewById(R.id.txt_title_fragment_1);
                tvstore = tabLayout.getTabAt(1).getCustomView().findViewById(R.id.txt_title_fragment_2);
                tvevent = tabLayout.getTabAt(2).getCustomView().findViewById(R.id.txt_title_fragment_3);
                tvuser = tabLayout.getTabAt(3).getCustomView().findViewById(R.id.txt_title_fragment_4);

                if (position == 0) {
                    tvhome.setTextColor(Color.WHITE);
                    tvevent.setTextColor(Color.BLACK);
                    tvstore.setTextColor(Color.BLACK);
                    tvuser.setTextColor(Color.BLACK);
                }
                if (position == 1) {
                    tvstore.setTextColor(Color.WHITE);
                    tvhome.setTextColor(Color.BLACK);
                    tvevent.setTextColor(Color.BLACK);
                    tvuser.setTextColor(Color.BLACK);
                }
                if (position == 2) {
                    tvevent.setTextColor(Color.WHITE);
                    tvhome.setTextColor(Color.BLACK);
                    tvstore.setTextColor(Color.BLACK);
                    tvuser.setTextColor(Color.BLACK);
                }
                if (position == 3) {
                    tvuser.setTextColor(Color.WHITE);
                    tvhome.setTextColor(Color.BLACK);
                    tvevent.setTextColor(Color.BLACK);
                    tvstore.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addController() {
        viewPager = findViewById(R.id.view_pager_main);
        tabLayout = findViewById(R.id.tab_layout_main);
        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab4"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        AdapterPagerMain adapterPagerMain = new AdapterPagerMain(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapterPagerMain);
        tabLayout.setupWithViewPager(viewPager);
        (tabLayout.getTabAt(0)).setCustomView(R.layout.custom_icon_tab_1_main);
        (tabLayout.getTabAt(1)).setCustomView(R.layout.custom_icon_tab_2_main);
        (tabLayout.getTabAt(2)).setCustomView(R.layout.custom_icon_tab_3_main);
        (tabLayout.getTabAt(3)).setCustomView(R.layout.custom_icon_tab_4_main);

        Intent i = getIntent();
        device = i.getParcelableExtra("device");

    }

    public void onBack(View view) {
        finish();
    }
}