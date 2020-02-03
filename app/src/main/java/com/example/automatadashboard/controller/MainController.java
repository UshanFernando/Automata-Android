package com.example.automatadashboard.controller;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;


public class MainController {
    private static final String TAG = "Request";

    private final OnRequestComplete mOnRequestComplete;


    public  interface OnRequestComplete {
        void onSuccessRequest(JSONObject result);
        void onFailedRequest(String error);
    }


    public MainController(OnRequestComplete onRequestComplete ) {
        mOnRequestComplete = onRequestComplete;

        Log.d(TAG, "Request: construct called");
    }

    public void newRequest(Context context,  String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        extractJason(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "That didn't work!" + error);
            }
        });
        RequestHandler handler = RequestHandler.getInstance(context);
        handler.addToRequestQueue(stringRequest);
// Add the request to the RequestQueue.
    }

    private void extractJason(String data)  {
        String output = "";
        output= Html.fromHtml(data).toString();
        output=output.substring(output.indexOf("{"),output.lastIndexOf("}") +2);
        Log.i(TAG, "onResponse: "+output);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mOnRequestComplete.onSuccessRequest(jsonObject);

    }


}


