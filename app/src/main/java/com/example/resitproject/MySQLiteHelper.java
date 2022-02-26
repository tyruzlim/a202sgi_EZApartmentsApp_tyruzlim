package com.example.resitproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_BOOKINGS = "bookings";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_OWNER = "owner";
    public static final String COLUMN_PAID = "is_paid";
    public static final String COLUMN_FLOOR = "floor";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_PRICE = "price";

    private static final String DATABASE_NAME = "unitbookinglist.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_BOOKINGS + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_OWNER + " TEXT NOT NULL, "
            + COLUMN_PAID + " INTEGER NOT NULL, "
            + COLUMN_FLOOR + " INTEGER NOT NULL, "
            + COLUMN_UNIT + " INTEGER NOT NULL, "
            + COLUMN_PRICE + " INTEGER NOT NULL);";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Database upgraded from ver. " + oldVersion + " to " + newVersion + ", all old data destroyed!");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }
}