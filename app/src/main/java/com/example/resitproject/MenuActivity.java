package com.example.resitproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    SharedPreferences mSP;
    public static final String ACCOUNT_PREF = "UserAccountPrefs";
    public static final String LOGIN = "loginKey";

    private Menu menu;

    Button unitBooking;
    Button viewBookings;
    Button viewSummary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Booking Master");
        setContentView(R.layout.activity_menu);

        unitBooking = (Button) findViewById(R.id.buttonUnitBooking);
        viewBookings = (Button) findViewById(R.id.buttonBookings);
        viewSummary = (Button) findViewById(R.id.buttonSummary);

        unitBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unitbookingIntent = new Intent(MenuActivity.this, UnitBookingActivity.class);
                startActivity(unitbookingIntent);
            }
        });

        viewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewbookingsIntent = new Intent(MenuActivity.this, BookingListActivity.class);
                startActivity(viewbookingsIntent);
            }
        });

        viewSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewsummaryIntent = new Intent(MenuActivity.this, SummaryActivity.class);
                startActivity(viewsummaryIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
            AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
            alert.setTitle("Logging Out");
            alert.setMessage("Are you sure you want to logout?");

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mSP = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSP.edit();
                    editor.clear();
                    editor.apply();

                    Intent menulogoutIntent = new Intent(MenuActivity.this, MainActivity.class);
                    startActivity(menulogoutIntent);
                    MenuActivity.this.finish();
                    Toast.makeText(MenuActivity.this, "You have successfully logged out", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.setCancelable(false);
            alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
