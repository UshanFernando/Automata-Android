package com.example.automatadashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.example.automatadashboard.controller.MainController;
import com.example.automatadashboard.controller.SensorViewAdapter;
import com.example.automatadashboard.controller.SwitchViewAdapter;
import com.example.automatadashboard.model.Sensor;
import com.example.automatadashboard.model.Switch;
import com.example.automatadashboard.util.AlertUtil;
import com.example.automatadashboard.view.RecyclerItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainController.OnRequestComplete {

    RecyclerView sensorView, switchView;
    ArrayList<Sensor> mSensors;
    ArrayList<Switch> mSwitches;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    SensorViewAdapter mSensorViewAdapter;
    SwitchViewAdapter mSwitchViewAdapter;
    LinearLayoutManager HorizontalLayout;
    Button nextBtn;
    Button addBtn;
    View ChildView;
    int RecyclerViewItemPosition;
    boolean scrolled = false;
    ProgressDialog progressDialog;

    private final String mcuUrl ="http://192.168.1.11/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sensorView = (RecyclerView) findViewById(R.id.sensor_view);
        switchView = (RecyclerView) findViewById(R.id.switch_view);
        nextBtn = findViewById(R.id.sensor_next_btn);
        addBtn = findViewById(R.id.add_btn);

        readFromMcu(mcuUrl);

        initilizeSensorView();
        initilizeSwitchView();

        progressDialog = new ProgressDialog(this);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!scrolled) {
                    sensorView.post(new Runnable() {
                        @Override
                        public void run() {
                            sensorView.smoothScrollToPosition(mSensorViewAdapter.getItemCount() - 1);
                        }
                    });
                    nextBtn.setBackgroundResource(R.drawable.arrow_back_ico);
                    scrolled = true;
                } else {
                    sensorView.post(new Runnable() {
                        @Override
                        public void run() {
                            sensorView.smoothScrollToPosition(0);
                        }
                    });
                    nextBtn.setBackgroundResource(R.drawable.arrow_ico);
                    scrolled = false;
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

    }



    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(Dashboard.this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_menu_dashboard, popup.getMenu());
        popup.show();
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initilizeSensorView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        sensorView.setLayoutManager(RecyclerViewLayoutManager);
        sensorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener so we don't get called again.
                sensorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // Capture the height of the parent view group.
                int width = ((ViewGroup) sensorView.getParent()).getWidth();
                // And let the adapter know the height.
                mSensorViewAdapter.setItemWidth(width);
                // Now that we know the height of the RecyclerView and its items, we
                // can set the adapter so the items can be created with the proper height.
                sensorView.setAdapter(mSensorViewAdapter);
            }
        });
        mSensors = new ArrayList<>();
//        mSensors.add(new Sensor("Temperature", "24", "°C", Sensor.SENSOR_TYPE_TEMP));
//        mSensors.add(new Sensor("Humidity", "54", "%", Sensor.SENSOR_TYPE_HUMIDITY));
//        mSensors.add(new Sensor("AirQuality", "98", "%", Sensor.SENSOR_TYPE_AIR_QUALITY));
//        mSensors.add(new Sensor("People", "5", "Inside", Sensor.SENSOR_TYPE_PEOPLE));

        mSensorViewAdapter = new SensorViewAdapter(mSensors, this);

        HorizontalLayout = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
        sensorView.setLayoutManager(HorizontalLayout);
//        sensorView.setLayoutManager(new GridLayoutManager(this, 2));
//        sensorView.setAdapter(mSensorViewAdapter);


    }

    private void initilizeSwitchView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        switchView.setLayoutManager(RecyclerViewLayoutManager);

        mSwitches = new ArrayList<>();

        mSwitchViewAdapter = new SwitchViewAdapter(mSwitches, this);


        switchView.setLayoutManager(new GridLayoutManager(this, 2));
        switchView.setAdapter(mSwitchViewAdapter);


        // Adding on item click listener to RecyclerView.
        switchView.addOnItemTouchListener(
                new RecyclerItemClickListener(Dashboard.this, switchView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(Dashboard.this, mSwitchViewAdapter.getSwitch(position)
                                .getSwitchName() + " changing state", Toast.LENGTH_SHORT).show();
                        changeState(mSwitchViewAdapter.getSwitch(position));
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(Dashboard.this, "Launching Edit Activity", Toast.LENGTH_SHORT).show();
                    }
                })
        );


    }
    private void readFromMcu(String url){
        MainController controller = new MainController(this);
        controller.newRequest(this, url);
    }

    @Override
    public void onSuccessRequest(JSONObject result) {
        mSensors.clear();
        mSwitches.clear();
        try {
            JSONObject sensors = result.getJSONObject("sensors");
            if (sensors.has("temp")) {
                mSensors.add(new Sensor("Temperature", sensors.getString("temp"), Sensor.SENSOR_TYPE_TEMP));
            }
            if (sensors.has("hum")) {
                mSensors.add(new Sensor("Humidity", sensors.getString("hum"), Sensor.SENSOR_TYPE_HUMIDITY));
            }
            JSONObject switches = result.getJSONObject("switch");
            if (switches.has("L1")) {
                try {
                    if (switches.getString("L1").equals("1")) {
                        mSwitches.add(new Switch("L1", "My Room Light 1", Switch.SWITCH_STATUS_ON, Switch.SWITCH_TYPE_LIGHT_BULB));
                    } else {
                        mSwitches.add(new Switch("L1", "My Room Light 1", Switch.SWITCH_STATUS_OFF, Switch.SWITCH_TYPE_LIGHT_BULB));

                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            if (switches.has("L2")) {
                try {
                    if (switches.getString("L2").equals("1")) {
                        mSwitches.add(new Switch("L2", "My Room Light 2", Switch.SWITCH_STATUS_ON, Switch.SWITCH_TYPE_LIGHT_BULB));
                    } else {
                        mSwitches.add(new Switch("L2", "My Room Light 2", Switch.SWITCH_STATUS_OFF, Switch.SWITCH_TYPE_LIGHT_BULB));

                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            if (switches.has("L3")) {
                try {
                    if (switches.getString("L3").equals("1")) {
                        mSwitches.add(new Switch("L3", "My Room Fan", Switch.SWITCH_STATUS_ON, Switch.SWITCH_TYPE_FAN));
                    } else {
                        mSwitches.add(new Switch("L3", "My Room Fan", Switch.SWITCH_STATUS_OFF, Switch.SWITCH_TYPE_FAN));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            if (switches.has("L4")) {
                try {
                    if (switches.getString("L4").equals("1")) {
                        mSwitches.add(new Switch("L4", "Plug top Table", Switch.SWITCH_STATUS_ON, Switch.SWITCH_TYPE_WALL_PLUG));
                    } else {
                        mSwitches.add(new Switch("L4", "Plug top Table", Switch.SWITCH_STATUS_OFF, Switch.SWITCH_TYPE_WALL_PLUG));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSensorViewAdapter.notifyDataSetChanged();
        mSwitchViewAdapter.notifyDataSetChanged();

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                readFromMcu(mcuUrl);
            }
        }, 7000);
    }

    @Override
    public void onFailedRequest(String error) {
    }

    public void changeState(Switch sw){
        String url = "http://192.168.1.11/";

        if (sw.getSwitchStatus().equals(Switch.SWITCH_STATUS_ON)){
            url = url+sw.getSwitchId()+"=OFF";
        }else {
            url = url+sw.getSwitchId()+"=ON";
        }
        progressDialog.setTitle("Sending Command");
        progressDialog.setMessage("Wait...");
        progressDialog.show();
        readFromMcu(url);
    }
}

