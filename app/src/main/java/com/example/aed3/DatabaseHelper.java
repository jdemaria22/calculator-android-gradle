package com.example.aed3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 2/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "movimientos";
    private static final String COL1 = "id";
    private static final String COL2 = "monto";
    private static final String COL3 = "concepto";
    private static final String COL4 = "categoria";
    private static final String COL5 = "fecha";
    private static final String COL6 = "tipo";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE movimientos (id INTEGER PRIMARY KEY AUTOINCREMENT, monto TEXT, concepto TEXT, categoria TEXT, fecha TEXT, tipo INTEGER)";
        /*String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" DOUBLE, " + COL3 + " TEXT, " + COL4 + " TEXT)";*/
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String monto, String concepto, String categoria, String fecha, int tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, monto);
        contentValues.put(COL3, concepto);
        contentValues.put(COL4, categoria);
        contentValues.put(COL5, fecha);
        contentValues.put(COL6, tipo);

        Log.d(TAG, "addData: Adding MONTO:" + monto + " - CONCEPTO:" + concepto + " - CATEGORIA:" + categoria + " - FECHA:" + fecha + "tipo :" + tipo + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param id
     * @return
     */
    public Cursor getItemID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return count;
    }
}