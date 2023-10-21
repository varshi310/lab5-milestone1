package com.cs407.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = "username";


        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone1", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("username","")!=""){
            username = sharedPreferences.getString("username","");
            goToActivity(username);

        }
        else{
            setContentView(R.layout.activity_main);
        }

    }

    public void clickOnLogin(View view){
        EditText editText = findViewById(R.id.username);
        String userName = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone1", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", userName).apply();
        String username = sharedPreferences.getString("username","");
        goToActivity(userName);
    }
    public void goToActivity(String s){
        Intent intent = new Intent(this,Notes.class);
        intent.putExtra("message",s);
        startActivity(intent);

    }
}