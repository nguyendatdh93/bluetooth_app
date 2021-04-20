package com.infinity.EBacSens.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapteRCVMenuDraw;
import com.infinity.EBacSens.adapters.AdapterPagerMain;
import com.infinity.EBacSens.data_sqllite.DBManager;
import com.infinity.EBacSens.model_objects.Sensor;
import com.infinity.EBacSens.model_objects.VerticalSpaceItemDecoration;
import com.infinity.EBacSens.views.ViewRCVMenuDrawListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewRCVMenuDrawListener {

    private DBManager dbManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;

    private RecyclerView rcvMenuDrawLayout;
    private ArrayList<Sensor> arrMenuDraw;
    private AdapteRCVMenuDraw adapteRCVMenuDraw;

    private TextView txtSensorName;

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
        drawerLayout = findViewById(R.id.drw_layout);
        txtSensorName = findViewById(R.id.txt_name_sensor);

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

        rcvMenuDrawLayout = findViewById(R.id.rcv_menu_drawer);
        rcvMenuDrawLayout.setHasFixedSize(true);
        rcvMenuDrawLayout.addItemDecoration(new VerticalSpaceItemDecoration(20));
        rcvMenuDrawLayout.setLayoutManager(new LinearLayoutManager(this));
        arrMenuDraw = new ArrayList<>();
        arrMenuDraw.add(new Sensor("Cảm biến nhiệt độ" , true , true));
        arrMenuDraw.add(new Sensor("Cảm biến độ ẩm" , false , false));
        arrMenuDraw.add(new Sensor("Cảm biến nhiệt độ" , false , false));
        arrMenuDraw.add(new Sensor("Cảm biến nhiệt độ" , false , false));
        arrMenuDraw.add(new Sensor("Cảm biến nhiệt độ" , false , false));
        arrMenuDraw.add(new Sensor("Cảm biến nhiệt độ" , false , false));
        arrMenuDraw.add(new Sensor("Cảm biến nhiệt độ" , false , false));
        adapteRCVMenuDraw = new AdapteRCVMenuDraw(this , arrMenuDraw , this);
        rcvMenuDrawLayout.setAdapter(adapteRCVMenuDraw);
    }

    public void onNavigator(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClickRCVMenuDraw(int position) {
        for (int i = 0 ; i < arrMenuDraw.size();i++){
            if (arrMenuDraw.get(i).isSelected()){
                arrMenuDraw.get(i).setSelected(false);
                adapteRCVMenuDraw.notifyItemChanged(i);
            }
        }
        arrMenuDraw.get(position).setSelected(true);
        adapteRCVMenuDraw.notifyItemChanged(position);

        txtSensorName.setText(arrMenuDraw.get(position).getName());
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}