package com.example.automatadashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.automatadashboard.controller.MainController;
import com.example.automatadashboard.controller.SensorViewAdapter;
import com.example.automatadashboard.util.AlertUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainController.OnRequestComplete{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        if (mWifi.isConnected()) {
            AlertUtil.showProgressDialog(this,"Wait a Second ..","Connecting to MCU");

            MainController controller = new MainController(this);
            controller.newRequest(this,"http://192.168.1.11/");

        }else {

            AlertUtil.showErrorDialog(this,getString(R.string.cant_connect_wifi)
                    ,getString(R.string.wifi_error_description));

        }

    }

    @Override
    public void onSuccessRequest(JSONObject result) {
                Intent intent = new Intent(this,Dashboard.class);
                startActivity(intent);
    }

    @Override
    public void onFailedRequest(String error) {
        AlertUtil.showErrorDialog(this,"MCU NOT DETECTED !"
                ,"Please Make sure MCU and Your SmartPhone is Connected to the same WIFI Network");
    }
}