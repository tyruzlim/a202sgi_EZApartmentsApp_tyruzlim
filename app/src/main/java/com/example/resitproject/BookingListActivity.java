package com.example.resitproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingListActivity extends AppCompatActivity {

    private BookingDataSource mdataSource = new BookingDataSource(this);
    private ArrayList<Booking> mbookingList = new ArrayList<>();
    private BookingListAdapter mAdapter;
    private RecyclerView mRV;
    Button buttonShowPaid;
    Button buttonShowAll;
    Button buttonShowUnpaid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("All Bookings");
        setContentView(R.layout.activity_bookinglist);

        mdataSource.open();
        mRV = (RecyclerView) findViewById(R.id.rvBookingList);

        mbookingList = mdataSource.getAllBookings();

        mAdapter = new BookingListAdapter(this, mbookingList);
        mRV.setAdapter(mAdapter);
        mRV.setLayoutManager(new LinearLayoutManager(this));

        buttonShowPaid = (Button) findViewById(R.id.buttonShowPaid);

        buttonShowPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Paid Bookings");

                mbookingList = mdataSource.getAllBookings();
                ArrayList<Booking> mpaidBookings = new ArrayList<>();

                for (int i=0;i<mbookingList.size();i++){
                    if(mbookingList.get(i).isPaid()==1)
                        mpaidBookings.add(mbookingList.get(i));
                }

                mAdapter = new BookingListAdapter(BookingListActivity.this, mpaidBookings);
                mRV.setAdapter(mAdapter);
                mRV.setLayoutManager(new LinearLayoutManager(BookingListActivity.this));
            }
        });

        buttonShowAll = (Button) findViewById(R.id.buttonShowAll);

        buttonShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("All Bookings");

                mbookingList = mdataSource.getAllBookings();

                mAdapter = new BookingListAdapter(BookingListActivity.this, mbookingList);
                mRV.setAdapter(mAdapter);
                mRV.setLayoutManager(new LinearLayoutManager(BookingListActivity.this));
            }
        });

        buttonShowUnpaid = (Button) findViewById(R.id.buttonShowUnpaid);

        buttonShowUnpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Unpaid Bookings");

                mbookingList = mdataSource.getAllBookings();
                ArrayList<Booking> munpaidBookings = new ArrayList<>();

                for (int i=0;i<mbookingList.size();i++){
                    if(mbookingList.get(i).isPaid()==0)
                        munpaidBookings.add(mbookingList.get(i));
                }

                mAdapter = new BookingListAdapter(BookingListActivity.this, munpaidBookings);
                mRV.setAdapter(mAdapter);
                mRV.setLayoutManager(new LinearLayoutManager(BookingListActivity.this));

            }
        });


    }

    @Override
    protected void onResume() {
        mdataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mdataSource.close();
        super.onPause();
    }

}
