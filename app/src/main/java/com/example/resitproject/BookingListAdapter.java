package com.example.resitproject;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.BookingViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Booking> mbookingList;
    private Context mContext;

    public class BookingViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView munitNumber;
        private TextView mPrice;
        private CheckBox mPaid;
        private Button mDelete;
        final BookingListAdapter mAdapter;
        private BookingDataSource mdataSource;

        public BookingViewHolder(@NonNull View itemView, BookingListAdapter adapter){
            super(itemView);

            mName = (TextView)itemView.findViewById(R.id.list_tvName);
            munitNumber = (TextView)itemView.findViewById(R.id.list_tvUnitNo);
            mPrice = (TextView)itemView.findViewById(R.id.list_tvPrice);
            mPaid = (CheckBox) itemView.findViewById(R.id.list_cbPaid);
            mDelete = (Button) itemView.findViewById(R.id.list_btnRemove);
            mAdapter = adapter;
            mdataSource = new BookingDataSource(mContext);
        }
    }

    public BookingListAdapter(Context context, ArrayList<Booking> bookingList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mbookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.list_item_unit, viewGroup, false);
        return new BookingViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder bookingViewHolder, final int i) {
        final Booking mCurrent = mbookingList.get(i);
        bookingViewHolder.mName.setText(mCurrent.getOwnerName());
        bookingViewHolder.munitNumber.setText("Floor " + mCurrent.getFloorNumber() + " Unit " + mCurrent.getUnitNumber());
        bookingViewHolder.mPrice.setText("RM " + mCurrent.getPrice());

        if(mCurrent.isPaid() == 1){
            bookingViewHolder.mPaid.setChecked(true);
            bookingViewHolder.mPaid.setEnabled(false);
            bookingViewHolder.mDelete.setEnabled(true);
        }
        else{
            bookingViewHolder.mPaid.setChecked(false);
            bookingViewHolder.mPaid.setEnabled(true);
            bookingViewHolder.mDelete.setEnabled(true);
        }

        bookingViewHolder.mPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Payment Confirmation");
                alert.setMessage("Mark booking as paid?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingViewHolder.mdataSource.open();
                        bookingViewHolder.mdataSource.payBooking(mCurrent.getId());
                        bookingViewHolder.mdataSource.close();
                        bookingViewHolder.mPaid.setEnabled(false);
                        bookingViewHolder.mDelete.setEnabled(true);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingViewHolder.mPaid.setChecked(false);
                    }
                });
                alert.setCancelable(false);
                alert.show();
            }
        });

        bookingViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Delete Booking");
                alert.setMessage("Delete this booking?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingViewHolder.mdataSource.open();
                        bookingViewHolder.mdataSource.deleteBooking(mCurrent.getId());
                        bookingViewHolder.mdataSource.close();

                        mbookingList.remove(mCurrent);
                        notifyDataSetChanged();
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
        });
    }

    @Override
    public int getItemCount() {
        return mbookingList.size();
    }
}
