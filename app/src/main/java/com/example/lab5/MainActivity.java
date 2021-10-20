package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button button;
    public static String usernameKey = "username";
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(usernameKey, "").equals("")){
            String username = sharedPreferences.getString(usernameKey, "");
            Login(username);
        }else {
            setContentView(R.layout.activity_main);


            button = findViewById(R.id.LoginButton);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    EditText userNameET = (EditText) findViewById(R.id.UserNameText);
                    String userName = userNameET.getText().toString();
                    Login(userName);
                }
            });
        }
    }

    private void Login(String userName) {
        Intent intent = new Intent(this, NoteAppMain.class);
        sharedPreferences.edit().putString(usernameKey, userName).apply();
        startActivity(intent);


    }
}