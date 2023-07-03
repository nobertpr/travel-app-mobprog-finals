package com.example.uas_travel_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameTxt, passTxt;
    Button loginBtn;
    TextView registerTxt;

    DatabaseHelper dbHelper;

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    private void init(){
        usernameTxt = findViewById(R.id.userName);
        passTxt = findViewById(R.id.userPass);
        loginBtn = findViewById(R.id.loginBtn);
        registerTxt = findViewById(R.id.registerTV_login);
    }

    private boolean valUsername(EditText userName){
        if(userName.getText().toString().trim().length() == 0){
            return false;
        } else {
            return true;
        }
    }

    private boolean valPass(EditText pass){
        if(pass.getText().toString().trim().length() == 0){
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        dbHelper = new DatabaseHelper(this);

        loginBtn.setOnClickListener(this);
        registerTxt.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        initDestinationJSON();

        sp = getSharedPreferences("LoggedInSession", MODE_PRIVATE);
        spEditor = sp.edit();


    }

    @Override
    public void onClick(View view) {
        if (view == loginBtn){
            if(!valUsername(usernameTxt)){
                // username salah
                Toast.makeText(this, "Username must not be empty!", Toast.LENGTH_SHORT).show();
            }

            if(!valPass(passTxt)){
                // pass salah
                Toast.makeText(this, "Password must not be empty", Toast.LENGTH_SHORT).show();
            }

            // setiap field terisi
            if(valUsername(usernameTxt) && valPass(passTxt)){
                String uName = usernameTxt.getText().toString();
                String uPass = passTxt.getText().toString();

                // check apakah username dan password match
                if(dbHelper.checkLoginMatch(uName,uPass)){

                    // Store to session
                    spEditor.putString("username",uName);
                    spEditor.apply();

                    Intent intent = new Intent(this,HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Username and Password does not match!", Toast.LENGTH_SHORT).show();
                }


                // validasi benar
//                Intent intent = new Intent(this,HomeActivity.class);
//                intent.putExtra("username", usernameTxt.getText().toString());
//                intent.putExtra("pass", passTxt.getText().toString());
//                startActivity(intent);
            }
        }
    }

    private void initDestinationJSON(){
        Vector<Destination> destinationInitJSON = new Vector<>();
        DestinationHelper destinationHelper = new DestinationHelper(this.getApplicationContext());

        if (destinationHelper.isDestinationEmpty()){
            RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

            String url = "https://api.jsonserve.com/gIMCYk";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("destinations");

                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject dest = jsonArray.getJSONObject(i);

                            String destName = dest.getString("name");
                            Double destRating = dest.getDouble("rating");
                            String destPrice = dest.getString("price");
                            String destImage = dest.getString("image");
                            String destImageAlt = dest.getString("imageAlt");
                            String destDescription = dest.getString("description");

                            Destination dest_data = new Destination(destName, destPrice, destImage, destImageAlt, destRating, destDescription);

                            destinationInitJSON.add(dest_data);
                        }

                        destinationHelper.open();
                        destinationHelper.initDestinationJSON(destinationInitJSON);
                        destinationHelper.close();



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                }
            });

            requestQueue.add(jsonObjectRequest);
        }

    }

}