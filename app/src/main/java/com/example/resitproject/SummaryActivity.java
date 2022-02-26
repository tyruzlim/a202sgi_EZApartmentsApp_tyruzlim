package com.example.resitproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {

    private TextView mTVunitsBooked;
    private TextView mTVunpaid;
    private TextView mTVpaid;
    private TextView mTVearnings;
    private TextView mTVpotentialGain;
    private BookingDataSource mdataSource = new BookingDataSource(this);
    private ArrayList<Booking> mbookingList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Total Summary");
        setContentView(R.layout.activity_summary);

        mdataSource.open();

        mTVunitsBooked = (TextView) findViewById(R.id.tvUnitsBooked);
        mTVunpaid = (TextView) findViewById(R.id.tvUnpaid);
        mTVpaid = (TextView) findViewById(R.id.tvPaid);
        mTVearnings = (TextView) findViewById(R.id.tvEarnings);
        mTVpotentialGain = (TextView) findViewById(R.id.tvPotentialGain);
        mbookingList = mdataSource.getAllBookings();

        mTVunitsBooked.setText(mbookingList.size() + " units");

        int unpaid=0,paid=0,potential=0,earnings=0;
        for (int i=0;i<mbookingList.size();i++){
            switch(mbookingList.get(i).isPaid()){
                case 0:
                    unpaid++;
                    potential += mbookingList.get(i).getPrice();
                    break;
                case 1:
                    paid++;
                    earnings += mbookingList.get(i).getPrice();
                    break;
            }
        }
        mTVunpaid.setText(unpaid + " units");
        mTVpaid.setText(paid + " units");
        mTVpotentialGain.setText("RM " + potential);
        mTVearnings.setText("RM " + earnings);

        mdataSource.close();

    }
}
