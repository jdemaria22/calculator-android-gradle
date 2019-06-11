package com.example.aed3;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;
import java.time.LocalDate;

class Calculos {
    /**
     * Instacio calculos.
     */
    Calculos() {

    }

    /**
     * Obtiene ingresos del mes.
     * @param mDatabaseHelper
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    Double totalIngresosDelMes(DatabaseHelper mDatabaseHelper) {
        mDatabaseHelper.getReadableDatabase();
        Cursor fila = mDatabaseHelper.getIngresosDelMes();
        LocalDate currentDate = LocalDate.now();
        Integer mes = currentDate.getMonthValue();
        double total = 0.00;
        if(fila.moveToFirst()) {
            String s = fila.getString(1);
            String s1 = s.substring(s.indexOf("/") + 1 );
            String s2 = s1.substring(0,s1.indexOf("/"));
            Integer is2 = Integer.valueOf(s2);
            Log.d("Mes_actual:", is2.toString());
            if (is2.equals(mes)){
                total += fila.getDouble(0);
            }
            Log.d("Total", Double.toString(total));

            for (int i = 0 ; i < fila.getCount(); i++){
                if (fila.moveToNext()){
                    s = fila.getString(1);
                    s1 = s.substring(s.indexOf("/") + 1 );
                    s2 = s1.substring(0,s1.indexOf("/"));
                    is2 = Integer.valueOf(s2);
                    Log.d("Mes_actual:", is2.toString());
                    if (is2.equals(mes)){
                        total += fila.getDouble(0);
                    }
                    Log.d("Total", Double.toString(total));
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
    Double totalEgresosDelMes(DatabaseHelper mDatabaseHelper) {
        mDatabaseHelper.getReadableDatabase();
        Cursor fila = mDatabaseHelper.getEgresosDelMes();
        LocalDate currentDate = LocalDate.now();
        Integer mes = currentDate.getMonthValue();
        Log.d("MES: ", mes.toString());
        double total = 0.00;
        if(fila.moveToFirst()) {
            String s = fila.getString(1);
            String s1 = s.substring(s.indexOf("/") + 1 );
            String s2 = s1.substring(0,s1.indexOf("/"));
            Integer is2 = Integer.valueOf(s2);
            Log.d("Mes_actual:", is2.toString());
            if (is2.equals(mes)){
                total += fila.getDouble(0);
            }
            for (int i = 1 ; i < fila.getCount(); i++){
                if (fila.moveToNext()){
                    s = fila.getString(1);
                    s1 = s.substring(s.indexOf("/") + 1 );
                    s2 = s1.substring(0,s1.indexOf("/"));
                    is2 = Integer.valueOf(s2);
                    Log.d("Mes_actual:", is2.toString());
                    if (is2.equals(mes)){
                        total += fila.getDouble(0);
                    }
                    Log.d("Total", Double.toString(total));
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
    Double totalTarjetaCredito(DatabaseHelper mDatabaseHelper){
        mDatabaseHelper.getReadableDatabase();
        Cursor fila = mDatabaseHelper.getGastosTC();
        LocalDate currentDate = LocalDate.now();
        Integer mes = currentDate.getMonthValue();
        Log.d("MES: ", mes.toString());
        double total = 0.00;
        if(fila.moveToFirst()) {
            String s = fila.getString(1);
            String s1 = s.substring(s.indexOf("/") + 1 );
            String s2 = s1.substring(0,s1.indexOf("/"));
            Integer is2 = Integer.valueOf(s2);
            Log.d("Mes_actual:", is2.toString());
            if (is2.equals(mes)){
                total += fila.getDouble(0);
            }
            Log.d("Total", Double.toString(total));

            for (int i = 1 ; i < fila.getCount(); i++){
                if (fila.moveToNext()){
                    s = fila.getString(1);
                    s1 = s.substring(s.indexOf("/") + 1 );
                    s2 = s1.substring(0,s1.indexOf("/"));
                    is2 = Integer.valueOf(s2);
                    Log.d("Mes_actual:", is2.toString());
                    if (is2.equals(mes)){
                        total += fila.getDouble(0);
                    }
                    Log.d("Total", Double.toString(total));
                }
            }
        }
        return total;
    }

    /**
     * Retorna los egresos de categoria "Mercado".
     * @return
     */
    Double obtenerEgresoMercado(DatabaseHelper mDatabaseHelper, Context context) {
        try {

            mDatabaseHelper.getReadableDatabase();
            Cursor mercado = mDatabaseHelper.getEgresoMercados();
            double d = 0.00;
            for (int i = 1; i < mercado.getColumnCount(); i++)
                if (mercado.moveToNext()) {
                    d = d + mercado.getDouble(1);
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
    Double obtenerEgresoAlquier(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor alquiler = mDatabaseHelper.getEgresoAlquier();
            double d = 0.00;
            for (int i = 1; i < alquiler.getColumnCount(); i++)
                if (alquiler.moveToNext()) {
                    d = d + alquiler.getDouble(1);
                    Log.d("Monto: ", Double.toString(d));
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
    Double obtenerEgresoTransporte(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor transporte = mDatabaseHelper.getEgresoTransporte();
            double d = 0.00;
            for (int i = 1; i < transporte.getColumnCount(); i++)
                if (transporte.moveToNext()) {
                    d = d + transporte.getDouble(1);
                    Log.d("Monto: ", Double.toString(d));
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
    Double obtenerEgresoImpuestos(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor impuestos = mDatabaseHelper.getEgresoImpuestos();
            double d = 0.00;
            for (int i = 1; i < impuestos.getColumnCount(); i++)
                if (impuestos.moveToNext()) {
                    d = d + impuestos.getDouble(1);
                    Log.d("Monto: ", Double.toString(d));
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
    Double obtenerEgresoOtros(DatabaseHelper mDatabaseHelper, Context context) {
        try {
            mDatabaseHelper.getReadableDatabase();
            Cursor otros = mDatabaseHelper.getEgresoOtros();
            double d = 0.00;
            for (int i = 0; i < otros.getColumnCount(); i++)
                if (otros.moveToNext()) {
                    d = d + otros.getDouble(1);
                    Log.d("Monto: ", Double.toString(d));
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
