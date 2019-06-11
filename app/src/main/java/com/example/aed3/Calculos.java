package com.example.aed3;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDate;

public class Calculos {
    /**
     * Instacio calculos.
     */
    public Calculos () {

    }

    /**
     * Obtiene ingresos del mes.
     * @param mDatabaseHelper
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Double totalIngresosDelMes(DatabaseHelper mDatabaseHelper) {
        mDatabaseHelper.getReadableDatabase();
        Cursor fila = mDatabaseHelper.getIngresosDelMes();
        LocalDate currentDate = LocalDate.now();
        Integer mes = new Integer(currentDate.getMonthValue());
        Log.d("MES: ", mes.toString());
        Double total = new Double(0.00);
        if(fila.moveToFirst()) {
            String s = fila.getString(1);
            String s1 = s.substring(s.indexOf("/") + 1 );
            String s2 = s1.substring(0,s1.indexOf("/"));
            Double monto = new Double(fila.getDouble(0));
            Integer is2 = new Integer(s2);
            Log.d("Mes_actual:", is2.toString());
            if (is2.equals(mes)){
                total += fila.getDouble(0);
            }
            Log.d("Total",total.toString());

            for (int i = 0 ; i < fila.getCount(); i++){
                if (fila.moveToNext()){
                    s = fila.getString(1);
                    s1 = s.substring(s.indexOf("/") + 1 );
                    s2 = s1.substring(0,s1.indexOf("/"));
                    monto = new Double(fila.getDouble(0));
                    is2 = new Integer(s2);
                    Log.d("Mes_actual:", is2.toString());
                    if (is2.equals(mes)){
                        total += fila.getDouble(0);
                    }
                    Log.d("Total",total.toString());
                }
            }
        }
        return total;
    }

    /**
     * Obtiene egresos del mes.
     * @param mDatabaseHelper
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Double totalEgresosDelMes(DatabaseHelper mDatabaseHelper) {
        mDatabaseHelper.getReadableDatabase();
        Cursor fila = mDatabaseHelper.getEgresosDelMes();
        LocalDate currentDate = LocalDate.now();
        Integer mes = new Integer(currentDate.getMonthValue());
        Log.d("MES: ", mes.toString());
        Double total = new Double(0.00);
        if(fila.moveToFirst()) {
            String s = fila.getString(1);
            String s1 = s.substring(s.indexOf("/") + 1 );
            String s2 = s1.substring(0,s1.indexOf("/"));
            Double monto = new Double(fila.getDouble(0));
            Integer is2 = new Integer(s2);
            Log.d("Mes_actual:", is2.toString());
            if (is2.equals(mes)){
                total += fila.getDouble(0);
            }
            Log.d("Total",total.toString());

            for (int i = 1 ; i < fila.getCount(); i++){
                if (fila.moveToNext()){
                    s = fila.getString(1);
                    s1 = s.substring(s.indexOf("/") + 1 );
                    s2 = s1.substring(0,s1.indexOf("/"));
                    monto = new Double(fila.getDouble(0));
                    is2 = new Integer(s2);
                    Log.d("Mes_actual:", is2.toString());
                    if (is2.equals(mes)){
                        total += fila.getDouble(0);
                    }
                    Log.d("Total",total.toString());
                }
            }
        }
        return total;
    }

    /**
     * Metodo que retorna el total de gastos con tarjeta de credito en el mes actual.
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Double totalTarjetaCredito(DatabaseHelper mDatabaseHelper){
        mDatabaseHelper.getReadableDatabase();
        Cursor fila = mDatabaseHelper.getGastosTC();
        LocalDate currentDate = LocalDate.now();
        Integer mes = new Integer(currentDate.getMonthValue());
        Log.d("MES: ", mes.toString());
        Double total = new Double(0.00);
        if(fila.moveToFirst()) {
            String s = fila.getString(1);
            String s1 = s.substring(s.indexOf("/") + 1 );
            String s2 = s1.substring(0,s1.indexOf("/"));
            Double monto = new Double(fila.getDouble(0));
            Integer is2 = new Integer(s2);
            Log.d("Mes_actual:", is2.toString());
            if (is2.equals(mes)){
                total += fila.getDouble(0);
            }
            Log.d("Total",total.toString());

            for (int i = 1 ; i < fila.getCount(); i++){
                if (fila.moveToNext()){
                    s = fila.getString(1);
                    s1 = s.substring(s.indexOf("/") + 1 );
                    s2 = s1.substring(0,s1.indexOf("/"));
                    monto = new Double(fila.getDouble(0));
                    is2 = new Integer(s2);
                    Log.d("Mes_actual:", is2.toString());
                    if (is2.equals(mes)){
                        total += fila.getDouble(0);
                    }
                    Log.d("Total",total.toString());
                }
            }
        }
        return total;
    }

    /**
     * Retorna los egresos de categoria "Mercado".
     * @return
     */
    public Double obtenerEgresoMercado(DatabaseHelper mDatabaseHelper, Context context) {
        try {

            mDatabaseHelper.getReadableDatabase();
            Cursor mercado = mDatabaseHelper.getEgresoMercados();
            Double d = new Double(0.00);
            for (int i = 1; i < mercado.getColumnCount(); i++)
                if (mercado.moveToNext()) {
                    d = d + mercado.getDouble(1);
                    Log.d("Monto: ", d.toString());
                }
            mDatabaseHelper.close();
            return d;
        } catch (Exception e){
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Alquiler".
     * @return
     */
    public Double obtenerEgresoAlquier(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor alquiler = mDatabaseHelper.getEgresoAlquier();
            Double d = new Double(0.00);
            for (int i = 1; i < alquiler.getColumnCount(); i++)
                if (alquiler.moveToNext()) {
                    d = d + alquiler.getDouble(1);
                    Log.d("Monto: ", d.toString());
                }
            mDatabaseHelper.close();
            return d;
        }catch (Exception e) {
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Transporte".
     * @return
     */
    public Double obtenerEgresoTransporte(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor transporte = mDatabaseHelper.getEgresoTransporte();
            Double d = new Double(0.00);
            for (int i = 1; i < transporte.getColumnCount(); i++)
                if (transporte.moveToNext()) {
                    d = d + transporte.getDouble(1);
                    Log.d("Monto: ", d.toString());
                }
            mDatabaseHelper.close();
            return d;
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Impuestos".
     * @return
     */
    public Double obtenerEgresoImpuestos(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor impuestos = mDatabaseHelper.getEgresoImpuestos();
            Double d = new Double(0.00);
            for (int i = 1; i < impuestos.getColumnCount(); i++)
                if (impuestos.moveToNext()) {
                    d = d + impuestos.getDouble(1);
                    Log.d("Monto: ", d.toString());
                }
            mDatabaseHelper.close();
            return d;
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Otros".
     * @return
     */
    public Double obtenerEgresoOtros(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor otros = mDatabaseHelper.getEgresoOtros();
            Double d = new Double(0.00);
            for (int i = 0; i < otros.getColumnCount(); i++)
                if (otros.moveToNext()) {
                    d = d + otros.getDouble(1);
                    Log.d("Monto: ", d.toString());
                }
            mDatabaseHelper.close();
            return d;
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }
}
