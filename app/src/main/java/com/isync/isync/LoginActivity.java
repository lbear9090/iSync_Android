package com.isync.isync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isync.isync.DataObject.ResponseData;
import com.isync.isync.helper.Global;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editEmail) EditText editEmail;
    @BindView(R.id.editPassword) EditText editPassword;
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void onSignIn(View v){
        final KProgressHUD hud = KProgressHUD.create(LoginActivity.this).setLabel("Login...").setDimAmount(0.5f).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().clear();
        String url = Global.baseURL + "/login.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("email", editEmail.getText().toString());
        params.put("password", editPassword.getText().toString());

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, response -> {
                    String text = "Response: " + response.toString();
                    Log.d("TAG", text);
                    hud.dismiss();

                    if(response.length() == 0){
                        Toast.makeText(LoginActivity.this, "Response Error", Toast.LENGTH_LONG).show();
                    }else{
                        ResponseData data = gson.fromJson(response.toString(), ResponseData.class);
                        if(data.success == 0){
                            Toast.makeText(LoginActivity.this, data.message, Toast.LENGTH_LONG).show();
                        }else{
                            Global.g_token = data.token;
                            Global.bLoggedIn = true;
                            finish();
                        }
                    }
                }, error -> {
                    // TODO: Handle error
                    hud.dismiss();
                    Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                    //Check Storage Data
                });
        queue.add(jsonObjectRequest);
    }

    public void onSignUp(View v){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    public void onForgotPW(View v){
        Intent i = new Intent(this, ForgotPWActivity.class);
        startActivity(i);
    }
}