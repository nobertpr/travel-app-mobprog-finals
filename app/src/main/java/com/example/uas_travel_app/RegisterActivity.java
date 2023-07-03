package com.example.uas_travel_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText uNameTxt, passTxt, regionTxt, emailTxt, phoneTxt;
    Button registerBtn;
    TextView loginTV;

    DatabaseHelper dbHelper;

    private void init(){
        uNameTxt = findViewById(R.id.registerUname);
        passTxt = findViewById(R.id.registerPassword);
        regionTxt = findViewById(R.id.registerRegion);
        emailTxt = findViewById(R.id.registerEmail);
        phoneTxt = findViewById(R.id.registerPhone);
        loginTV = findViewById(R.id.loginTV_from_register);
        registerBtn =findViewById(R.id.registerBtn);
    }

    private boolean valEmpty(EditText uNameTxt, EditText passTxt, EditText regionTxt, EditText emailTxt, EditText phoneTxt){
        if(uNameTxt.getText().toString().trim().length() == 0 || passTxt.getText().toString().trim().length() == 0 || regionTxt.getText().toString().trim().length() == 0 || emailTxt.getText().toString().trim().length() == 0 || phoneTxt.getText().toString().trim().length() == 0){
            return false;
        }

        return true;
    }

    private boolean valEmail(EditText emailTxt){
        String emailStr = emailTxt.getText().toString().trim();
        if(!emailStr.contains("@") || !emailStr.endsWith(".com")){
            return false;
        }
        return true;
    }

    private boolean isAlphaNum(EditText temp){
        String tempStr = temp.getText().toString().trim();
        if (!tempStr.matches("[A-Za-z\\d]+")){
            return false;
        }

        return true;
    }

    private boolean valPassLen(EditText temp){
        String tempStr = temp.getText().toString().trim();
        if (tempStr.length() < 5){
            return false;
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        dbHelper = new DatabaseHelper(this);

        // Login text listener
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        // Register Button listener
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // empty field validation
                if(!valEmpty(uNameTxt,  passTxt,  regionTxt,  emailTxt,  phoneTxt)){
                    Toast.makeText(RegisterActivity.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                }

                // input tidak empty
                else {

                    if (!isAlphaNum(uNameTxt)){
                        Toast.makeText(RegisterActivity.this, "Name must be alphanumeric!", Toast.LENGTH_SHORT).show();
                    } else if (!isAlphaNum(passTxt)) {
                        Toast.makeText(RegisterActivity.this, "Password must be alphanumeric!", Toast.LENGTH_SHORT).show();
                    } else if (!valPassLen(passTxt)) {
                        Toast.makeText(RegisterActivity.this, "Password must be at least 5 characters!", Toast.LENGTH_SHORT).show();
                    } else if(!valEmail(emailTxt)){ // validasi email
                        Toast.makeText(RegisterActivity.this, "Must be a valid email!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Sukses
                        String uName = uNameTxt.getText().toString();
                        String uPass = passTxt.getText().toString();
                        String uEmail = emailTxt.getText().toString();
                        String uRegion = regionTxt.getText().toString();
                        String uPhoneNum = phoneTxt.getText().toString();

                        if(!dbHelper.checkUserNameUnique(uName)){
                            Toast.makeText(RegisterActivity.this, "Username already exist!", Toast.LENGTH_SHORT).show();
                        } else {
                            // username sudah unik
                            dbHelper.insertUser(uName,uPass,uEmail,uRegion,uPhoneNum);

                            // pindah menuju login form
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }


                    }
                }
                



            }
        });
    }
}