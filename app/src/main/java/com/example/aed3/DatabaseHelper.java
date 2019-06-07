package com.example.aed3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "movimientos";
    private static final String COL1 = "id";
    private static final String COL2 = "monto";
    private static final String COL3 = "concepto";
    private static final String COL4 = "categoria";
    private static final String COL5 = "fecha";
    private static final String COL6 = "tipo";

    public DatabaseHelper(MainActivity context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE movimientos (id INTEGER PRIMARY KEY AUTOINCREMENT, monto TEXT, concepto TEXT, categoria TEXT, fecha TEXT, tipo INTEGER)";
        db.execSQL(sql);
    }

    /**
     * Dropea y crea las tables de vuelta.
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserta data y retorna true o false si se inserto de forma correcta.
     * @param monto
     * @param concepto
     * @param categoria
     * @param fecha
     * @param tipo
     * @return
     */
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

    /**
     * Retorna cursor con los egresos con la categoria Mercados.
     * @return
     */
    public Cursor getEgresoMercados() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query2 = "SELECT * FROM movimientos WHERE tipo = '2' AND categoria = 'Mercados'";
        Cursor data = db.rawQuery(query2, null);
        Integer cantidad = new Integer(data.getColumnCount());
        Log.d(TAG, cantidad.toString());
        return data;
    }

    /**
     * Retorna cursor con los egresos con la categoria Alquiler.
     * @return
     */
    public  Cursor getEgresoAlquier() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query2 = "SELECT * FROM movimientos WHERE tipo = '2' AND categoria = 'Alquiler'";
        Cursor data = db.rawQuery(query2, null);
        Integer cantidad = new Integer(data.getColumnCount());
        Log.d(TAG, cantidad.toString());
        return data;
    }

    /**
     * Retorna cursor con los egresos con la categoria Transporte.
     * @return
     */
    public Cursor getEgresoTransporte() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query2 = "SELECT * FROM movimientos WHERE tipo = '2' AND categoria = 'Transporte'";
        Cursor data = db.rawQuery(query2, null);
        Integer cantidad = new Integer(data.getColumnCount());
        Log.d(TAG, cantidad.toString());
        return data;
    }

    /**
     * Retorna cursor con los egresos con la categoria Impuestos.
     * @return
     */
    public Cursor getEgresoImpuestos() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query2 = "SELECT * FROM movimientos WHERE tipo = '2' AND categoria = 'Impuestos'";
        Cursor data = db.rawQuery(query2, null);
        Integer cantidad = new Integer(data.getColumnCount());
        Log.d(TAG, cantidad.toString());
        return data;
    }

    /**
     * Retorna cursor con los egresos con la categoria Otros.
     * @return
     */
    public Cursor getEgresoOtros() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query2 = "SELECT * FROM movimientos WHERE tipo = '2' AND categoria = 'Otros'";
        Cursor data = db.rawQuery(query2, null);
        Integer cantidad = new Integer(data.getColumnCount());
        Log.d(TAG, cantidad.toString());
        return data;
    }

    /**
     * Retorna cursor con los ingresos del mes.
     * @return
     */
    public Cursor getIngresosDelMes() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT monto, fecha FROM movimientos WHERE tipo = '1' AND concepto != 'Tarjeta de Credito'";
        Cursor data = db.rawQuery(query,null);
        if(data.moveToFirst()) {
            Log.d(TAG, data.getString(1));
        }
        return data;
    }

    /**
     * Retorna cursor con los egresos del mes.
     * @return
     */
    public Cursor getEgresosDelMes() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT monto, fecha FROM movimientos WHERE tipo = '2' AND concepto != 'Tarjeta de Credito'";
        String query2 = "WHERE strftime('%m',fecha) = strftime('%m','now')  AND tipo = '2' AND concepto != 'Tarjeta de Credito'";
        Cursor data = db.rawQuery(query,null);
        //Double monto = new Double(data.getInt(0));
        if(data.moveToFirst()) {
            Log.d(TAG, data.getString(1));
        }
        return data;
    }

    /**
     * Retorna cursor con los Egresos con tarjeta de credito del mes.
     * @return
     */
    public Cursor getGastosTC() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT monto, fecha FROM movimientos WHERE tipo = '2' AND concepto = 'Tarjeta de Credito'";
        Cursor data = db.rawQuery(query,null);
        if(data.moveToFirst()) {
            Log.d(TAG, data.getString(1));
        }
        return data;
    }

    /**
     * Retorna la cantidad de filas obtenidas en la tabla movimientos.
     * @return
     */
    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return count;
    }
}