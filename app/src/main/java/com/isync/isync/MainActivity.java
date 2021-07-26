package com.isync.isync;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isync.isync.DataObject.DailyPerformance;
import com.isync.isync.DataObject.DashboardData;
import com.isync.isync.DataObject.ResponseData;
import com.isync.isync.DataObject.Snapshot;
import com.isync.isync.databinding.ActivityMainBinding;
import com.isync.isync.helper.Global;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    KProgressHUD hud;
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hud = KProgressHUD.create(MainActivity.this).setDimAmount(0.5f);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_sendemail, R.id.nav_setting)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(Global.g_token)){
            openLogin();
        }else{
            getDashboard();
        }
    }

    void openLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    void getDashboard(){
        hud.setLabel("Loading dashboard...").show();
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().clear();
        String url = Global.baseURL + "/partner-dashboard.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    String text = "Response: " + response.toString();
                    Log.d("TAG", text);
                    hud.dismiss();

                    if(response.length() == 0){
                        Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_LONG).show();
                    }else{
                        DashboardData data = gson.fromJson(response.toString(), DashboardData.class);
                        if(data.success == 0){
                            Toast.makeText(MainActivity.this, data.message, Toast.LENGTH_LONG).show();
                        }else{
                            hud.dismiss();
                            Global.dashboardData = data;
                            getSnapshot();
                        }
                    }
                }, error -> {
                    // TODO: Handle error
                    hud.dismiss();
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                    //Check Storage Data
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Apitoken", "Bearer " + Global.g_token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    void getSnapshot(){
        hud.setLabel("Loading snapshot...").show();
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().clear();
        String url = Global.baseURL + "/partner-snapshot.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    String text = "Response: " + response.toString();
                    Log.d("TAG", text);
                    hud.dismiss();

                    if(response.length() == 0){
                        Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_LONG).show();
                    }else{
                        Snapshot data = gson.fromJson(response.toString(), Snapshot.class);
                        if(data.success == 0){
                            Toast.makeText(MainActivity.this, data.message, Toast.LENGTH_LONG).show();
                        }else{
                            hud.dismiss();
                            Global.snapshot = data;
                            getDailyPerformance();
                        }
                    }
                }, error -> {
                    // TODO: Handle error
                    hud.dismiss();
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                    //Check Storage Data
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Apitoken", "Bearer " + Global.g_token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    void getDailyPerformance(){
        hud.setLabel("Loading DailyPerformance...").show();
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().clear();
        String url = Global.baseURL + "/daily-performance.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    String text = "Response: " + response.toString();
                    Log.d("TAG", text);
                    hud.dismiss();

                    if(response.length() == 0){
                        Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_LONG).show();
                    }else{
                        DailyPerformance data = gson.fromJson(response.toString(), DailyPerformance.class);
                        if(data.success == 0){
                            Toast.makeText(MainActivity.this, data.message, Toast.LENGTH_LONG).show();
                        }else{
                            hud.dismiss();
                            Global.dailyPerformance = data;
                        }
                    }
                }, error -> {
                    // TODO: Handle error
                    hud.dismiss();
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                    //Check Storage Data
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Apitoken", "Bearer " + Global.g_token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}