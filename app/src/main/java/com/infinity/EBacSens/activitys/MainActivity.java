package com.infinity.EBacSens.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapterPagerMain;
import com.infinity.EBacSens.model_objects.SensorInfor;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static SensorInfor device;
    public static boolean toggle;
    private TextView txtNameDevice;
    private int offset = 0;

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
                if (offset < position){
                    offset = position;
                    viewPager.setOffscreenPageLimit(offset);
                }

                TextView[] arrTxtTitle = new TextView[4];
                LinearLayout[] arrlinearLayout = new LinearLayout[4];
                arrTxtTitle[0] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.txt_title_fragment_1);
                arrTxtTitle[1] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getCustomView()).findViewById(R.id.txt_title_fragment_2);
                arrTxtTitle[2] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(2)).getCustomView()).findViewById(R.id.txt_title_fragment_3);
                arrTxtTitle[3] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(3)).getCustomView()).findViewById(R.id.txt_title_fragment_4);

                arrlinearLayout[0] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.container_fragment_1);
                arrlinearLayout[1] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getCustomView()).findViewById(R.id.container_fragment_2);
                arrlinearLayout[2] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(2)).getCustomView()).findViewById(R.id.container_fragment_3);
                arrlinearLayout[3] = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(3)).getCustomView()).findViewById(R.id.container_fragment_4);

                for (int i = 0 ; i < arrTxtTitle.length ; i++){
                    arrTxtTitle[i].setTextColor(i == position ? Color.WHITE : Color.BLACK);
                    arrlinearLayout[i].setBackground(i == position ? ContextCompat.getDrawable(MainActivity.this , R.drawable.circle_bg_menu_active) : ContextCompat.getDrawable(MainActivity.this , R.drawable.circle_bg_menu_not_active));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addController() {
        device = (SensorInfor) getIntent().getSerializableExtra("device");
        toggle = getIntent().getBooleanExtra("active" , false);

        viewPager = findViewById(R.id.view_pager_main);
        txtNameDevice = findViewById(R.id.txt_name_sensor);
        tabLayout = findViewById(R.id.tab_layout_main);
        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab4"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        AdapterPagerMain adapterPagerMain = new AdapterPagerMain(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapterPagerMain);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(R.layout.custom_icon_tab_1_main);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(R.layout.custom_icon_tab_2_main);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(R.layout.custom_icon_tab_3_main);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setCustomView(R.layout.custom_icon_tab_4_main);

        if (device != null){
            txtNameDevice.setText(device.getName());
        }
    }

    public void onBack(View view) {
        finish();
    }
}