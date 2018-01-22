package com.mp2l.clubi;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;


import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.mp2l.clubi.module.*;
/*import butterknife.BindView;
import butterknife.ButterKnife;*/

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener, BottomNavigationBar.OnTabSelectedListener, AdapterView.OnItemSelectedListener {

    private Context context;
   /* @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout)DrawerLayout drawerLayout;
*/
   Toolbar toolbar;
     NavigationView navView;
    DrawerLayout drawerLayout;

    BottomNavigationBar bottomNavigationBar;

    public static final int
            MODULE_NEWS = 0,
            MODULE_FANS = 1;

    private NewsFragment mNewsFragment;
    private FansFragment mFansFragment;
    int lastSelectedPosition = 0;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private List<Fragment> fragments;



    @Nullable
    TextBadgeItem numberBadgeItem;

    @Nullable
    ShapeBadgeItem shapeBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
       // ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
         navView = (NavigationView) findViewById(R.id.nav_view);
         drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        initView();

       bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();

        this.initFragments(savedInstanceState);
        this.refresh();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void initView() {
        context = getApplicationContext();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        View headerView = navView.getHeaderView(0);
        navView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("Acceuil");

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void refresh() {

        bottomNavigationBar.clearAll();
      //  getSupportActionBar().hide();

        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(false);

        shapeBadgeItem = new ShapeBadgeItem()
                .setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setShapeColorResource(R.color.teal)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(false);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);



            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Acceuil").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Amis").setActiveColorResource(R.color.teal))
                    .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Jour").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Fun").setActiveColorResource(R.color.brown))
                    .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Profil").setActiveColorResource(R.color.grey))
                    .setFirstSelectedPosition(lastSelectedPosition)
                    .initialise();
        }

    private final void createFragment() {
        mNewsFragment = NewsFragment.newInstance();
        mFansFragment = FansFragment.newInstance();

        fragments = Arrays.asList(mNewsFragment,mFansFragment);

    }

    private final void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            createFragment();
            mTransaction = mFragmentManager.beginTransaction();
            for (Fragment f : fragments) {
                mTransaction.add(R.id.llayout, f);
            }
            mTransaction.commit();
        } else {
            restoreFragment();
            hideAllFragments();
            mTransaction.show(fragments.get(MODULE_NEWS)).commit();
        }
    }

    private final void restoreFragment() {
        List<Fragment> fragmentList = mFragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof NewsFragment) {
                mNewsFragment = (NewsFragment) fragment;
            }
        }
        fragments = Arrays.asList(mNewsFragment,mFansFragment);
    }

    private final void showSelectFragment(int position) {
        hideAllFragments();
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.show(fragments.get(position)).commit();
    }

    private final void hideAllFragments() {
        for (Fragment f : fragments) {
            mTransaction.hide(f);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return false;
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        refresh();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        refresh();
    }


    @Override
    public void onTabSelected(int position) {
    lastSelectedPosition = position;
    setMessageText(position + " Tab Selected");
    if (numberBadgeItem != null) {
        numberBadgeItem.setText(Integer.toString(position));
    }
    setScrollableText(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
        setMessageText(position + " Tab Reselected");
    }

    private void setMessageText(String messageText) {

        Toast.makeText(getApplicationContext(),messageText, Toast.LENGTH_LONG).show();

    }

    private void setScrollableText(int position) {

        mTransaction = mFragmentManager.beginTransaction();
        hideAllFragments();
        mTransaction.show(fragments.get(position)).commit();
        Toast.makeText(getApplicationContext(),position+"", Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}