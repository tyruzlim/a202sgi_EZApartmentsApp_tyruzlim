package com.example.resitproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mETloginUsername, mETloginPassword;
    SharedPreferences mSP;
    public static final String ACCOUNT_PREF = "UserAccountPrefs";
    public static final String LOGIN = "loginKey";
    private Menu menu;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Admin Login");
        setContentView(R.layout.activity_main);


        mETloginUsername = (EditText) findViewById(R.id.etLoginUsername);
        mETloginPassword = (EditText) findViewById(R.id.etLoginPassword);

        mSP = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);

        if(mSP.contains(LOGIN)){
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mETloginUsername.getText().toString().equals("admin") && mETloginPassword.getText().toString().equals("admin")) {
                    SharedPreferences.Editor editor = mSP.edit();
                    editor.putString(LOGIN, mETloginUsername.getText().toString());
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(MainActivity.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_login,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_info:
                Intent loginhelpIntent = new Intent(MainActivity.this, LoginHelpActivity.class);
                startActivity(loginhelpIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}