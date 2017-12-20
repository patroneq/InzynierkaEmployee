package com.client.inzynierkaemployee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.inzynierkaemployee.Fragment.ChangePassword;
import com.client.inzynierkaemployee.Fragment.ListOfTasks;
import com.client.inzynierkaemployee.Model.EmployeeModel;
import com.client.inzynierkaemployee.Utils.Utils;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static EmployeeModel employeeModel;
    Gson gson;

    private TextView mNickNameView;
    private TextView mFullNameView;
    private TextView mSpecView;
    private ImageView mImageView;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        employeeModel = new EmployeeModel();
        gson = Utils.getGsonInstance();
        employeeModel = gson.fromJson(getIntent().getStringExtra("user_profile"), EmployeeModel.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void refreshMenu()
    {
        if (mMenu!=null)
        {
            mMenu.clear();
            this.onCreateOptionsMenu(this.mMenu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the mMenu; this adds items to the action bar if it is present.
        this.setMenu(menu);
        employeeModel = gson.fromJson(getIntent().getStringExtra("user_profile"), EmployeeModel.class);
        System.out.println("USER:" +employeeModel.getFullName());
//        getIntent().putExtra("ham-menu", new Gson().toJson(menu));
        getMenuInflater().inflate(R.menu.main, menu);
        mNickNameView = (TextView) findViewById(R.id.nickNameView);
        mFullNameView = (TextView) findViewById(R.id.fullNameView);
        mImageView = (ImageView) findViewById(R.id.logOffImageView);
        mNickNameView.setText(employeeModel.email);
        passObject(employeeModel);
        mFullNameView.setText(employeeModel.name + " " + employeeModel.lastName);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOff();
            }
        });
        return true;
    }

    private void logOff()
    {
        Intent LogingIntent = new Intent(this, LoginActivity.class);
        startActivity(LogingIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Fragment fragment = null;
            fragment = new ChangePassword();

            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id) {
        Fragment fragment = null;

        switch(id) {
            case R.id.list_of_tasks:
                fragment = new ListOfTasks();
                break;
            case R.id.current_tasks:
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        displaySelectedScreen(id);

        return true;
    }

    private void passObject(EmployeeModel employee) {
        Bundle bundle = new Bundle();
        bundle.putString("employee", new Gson().toJson(employee));
        Fragment fragment = new ListOfTasks();
        fragment.setArguments(bundle);
    }

    public EmployeeModel getEmployeeModel()
    {
        return employeeModel;
    }

    private void setMenu(Menu menu) {
        this.mMenu = menu;
    }
}