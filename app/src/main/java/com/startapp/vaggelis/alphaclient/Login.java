package com.startapp.vaggelis.alphaclient;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.startapp.vaggelis.alphaclient.activities.BaseActivity;
import com.startapp.vaggelis.alphaclient.callables.NetworkCallable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends BaseActivity {

    private static EditText username;
    private static EditText password;
    private static TextView attempt;
    private static Button login_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticContext.setCurrentActivity(this);
        setContentView(R.layout.activity_main);
        LoginButton();
    }

    public void LoginButton(){
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login_button = (Button)findViewById(R.id.button_login);

        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        NetworkCallable<JSONObject> loginHandle = new NetworkCallable<JSONObject>() {
                            @Override
                            public void before() {

                            }

                            @Override
                            public void success(JSONObject response) {
                                Intent userIntent = new Intent(Login.this,User.class);
                                userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(userIntent);
                                try{
                                    Login.this.api.token = response.getString("token");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void fail(VolleyError error) {
                                Toast.makeText(Login.this,"Username and password is NOT correct", Toast.LENGTH_SHORT).show();
                            }
                        };

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username.getText().toString());
                        params.put("password", password.getText().toString());

                        Login.this.api.login(loginHandle, params);
                    }
                }
        );
    }

}


