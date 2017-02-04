package org.mobiletrain.encryptiondemo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import org.mobiletrain.encryptiondemo.fragment.DrawerFragment;

public class MainActivity extends AppCompatActivity implements DrawerFragment.OnDrawerCloseListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DrawerFragment drawerFragment = new DrawerFragment();
        drawerFragment.setListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, drawerFragment).commit();
    }

    @Override
    public void onDrawerClose() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}
