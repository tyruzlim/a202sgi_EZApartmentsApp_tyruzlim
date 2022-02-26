package com.example.resitproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class TabFragmentBooking extends Fragment {
    private Context mContext;
    private EditText mETfloor;
    private EditText mETunit;
    private EditText mETname;
    private TextView mTVbookingCheck;
    private Button mBTNcheckBooking;
    private Button mBTNconfirmBooking;
    private BookingDataSource mdataSource;

    public TabFragmentBooking(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_booking, container, false);

        mContext = (Context)getContext();
        mETfloor = (EditText)view.findViewById(R.id.etFloorNumber);
        mETunit = (EditText)view.findViewById(R.id.etUnitNumber);
        mETname = (EditText)view.findViewById(R.id.etOwnerName);
        mTVbookingCheck = (TextView)view.findViewById(R.id.tvCheckBookingText);
        mBTNcheckBooking = (Button)view.findViewById(R.id.buttonCheckBooking);
        mBTNconfirmBooking = (Button)view.findViewById(R.id.buttonConfirmBooking);
        mdataSource = new BookingDataSource(mContext);

        mETfloor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTVbookingCheck.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mETunit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTVbookingCheck.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBTNcheckBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdataSource.open();
                int floorNumber,unitNumber;

                if(mETfloor.getText().toString().isEmpty() || mETunit.getText().toString().isEmpty()){
                    Toast.makeText(mContext, "Please enter both floor and unit numbers!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    floorNumber = Integer.parseInt(mETfloor.getText().toString());
                    unitNumber = Integer.parseInt(mETunit.getText().toString());
                }
                catch(NumberFormatException e){
                    Toast.makeText(mContext, "Invalid Input, only numbers allowed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (floorNumber<1 || floorNumber>10 || unitNumber<1 || unitNumber>4)
                    Toast.makeText(mContext, "Floor and Unit number range only accepted as shown above!", Toast.LENGTH_SHORT).show();
                else{
                    ArrayList<Booking> bookings = mdataSource.getAllBookings();

                    for (int i=0;i< bookings.size();i++){
                        //If a booking is not vacant
                    if(bookings.get(i).getFloorNumber()==floorNumber && bookings.get(i).getUnitNumber()==unitNumber) {
                        mTVbookingCheck.setText("Not Available");
                        mTVbookingCheck.setTextColor(Color.RED);
                        return;
                    }
                    }
                    //If a booking is vacant
                    mTVbookingCheck.setText("Available");
                    mTVbookingCheck.setTextColor(Color.GREEN);
                }
                mdataSource.close();
            }
        });

        mBTNconfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdataSource.open();
                final int floor,unit;
                final String ownerName;

                //Check if all fields are filled in
                if(mETfloor.getText().toString().isEmpty() || mETunit.getText().toString().isEmpty() || mETname.getText().toString().isEmpty()){
                    Toast.makeText(mContext, "Please make sure you have filled in all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    floor = Integer.parseInt(mETfloor.getText().toString());
                    unit = Integer.parseInt(mETunit.getText().toString());
                    ownerName = mETname.getText().toString().trim();
                }
                catch (NumberFormatException e){
                    Toast.makeText(mContext, "Invalid Input! Only numbers are allowed in floor and unit!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (floor<1 || floor>10 || unit<1 || unit>4)
                    Toast.makeText(mContext, "Floor and Unit number range only accepted as shown above!", Toast.LENGTH_SHORT).show();
                else{
                    ArrayList<Booking> bookings = mdataSource.getAllBookings();

                    for (int i=0;i< bookings.size();i++){
                        //If booking exists for current floor and unit
                        if (bookings.get(i).getFloorNumber()==floor && bookings.get(i).getUnitNumber()==unit){
                            Toast.makeText(mContext, "Booking already exists for this floor and unit, please choose another!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    //If booking is vacant
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Add Booking");
                    alert.setMessage("Add Booking for this Floor and Unit?\n\nOwner: " + ownerName + "\nFloor " + floor + " Unit " + unit);

                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mdataSource.open();

                            int price = 200+(100*(floor-1));
                            mdataSource.createBooking(ownerName,0,floor,unit,price);
                            Toast.makeText(mContext, "Booking successfully created for: " + ownerName + "!", Toast.LENGTH_SHORT).show();

                            mETfloor.setText("");
                            mETunit.setText("");
                            mETname.setText("");
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.setCancelable(false);
                    alert.show();
                }
                mdataSource.close();
            }
        });
        return view;
    }
}
