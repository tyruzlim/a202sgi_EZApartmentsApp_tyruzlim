package com.example.resitproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BookingDataSource {

    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;
    private String[] dbAllColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_OWNER,
            MySQLiteHelper.COLUMN_PAID,
            MySQLiteHelper.COLUMN_FLOOR,
            MySQLiteHelper.COLUMN_UNIT,
            MySQLiteHelper.COLUMN_PRICE};

    public BookingDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Booking createBooking(String ownerName, int paid, int floorNumber, int unitNumber, int price){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_OWNER, ownerName);
        values.put(MySQLiteHelper.COLUMN_PAID, paid);
        values.put(MySQLiteHelper.COLUMN_FLOOR, floorNumber);
        values.put(MySQLiteHelper.COLUMN_UNIT, unitNumber);
        values.put(MySQLiteHelper.COLUMN_PRICE, price);

        long insertId = db.insert(MySQLiteHelper.TABLE_BOOKINGS, null, values);
        Cursor cursor = db.query(MySQLiteHelper.TABLE_BOOKINGS, dbAllColumns,MySQLiteHelper.COLUMN_ID + " = " + insertId,null,null,null,null);
        cursor.moveToFirst();
        Booking newBooking = cursorToBooking(cursor);
        cursor.close();

        return newBooking;
    }

    public void deleteBooking(long id){
        db.delete(MySQLiteHelper.TABLE_BOOKINGS,MySQLiteHelper.COLUMN_ID + " = " + id,null);
    }

    public void payBooking(long id){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PAID, 1);
        db.update(MySQLiteHelper.TABLE_BOOKINGS, values,MySQLiteHelper.COLUMN_ID + " = " + id,null);
    }

    public Booking getBooking(long id){
        Cursor cursor = db.query(MySQLiteHelper.TABLE_BOOKINGS, dbAllColumns, MySQLiteHelper.COLUMN_ID + " = " + id,null,null,null,null);

        Booking booking = cursorToBooking(cursor);
        cursor.close();

        return booking;
    }

    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        Cursor cursor = db.query(MySQLiteHelper.TABLE_BOOKINGS, dbAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Booking booking = cursorToBooking(cursor);
            bookings.add(booking);
            cursor.moveToNext();
        }
        cursor.close();

        return bookings;
    }

    private Booking cursorToBooking(Cursor cursor){
        Booking booking = new Booking();

        booking.setId(cursor.getLong(0));
        booking.setOwnerName(cursor.getString(1));
        booking.setPaid(cursor.getInt(2));
        booking.setFloorNumber(cursor.getInt(3));
        booking.setUnitNumber(cursor.getInt(4));
        booking.setPrice(cursor.getInt(5));

        return booking;
    }
}
