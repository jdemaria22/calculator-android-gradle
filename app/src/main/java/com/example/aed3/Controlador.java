package com.example.aed3;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Controlador {
    private String fecha_actual;
    private static String formato;
    private MainActivity appCompatActivity;
    /**
     * Instancio controlador
     */
    Controlador(MainActivity appCompatActivity) {
        fecha_actual = "";
        formato = "EEEE dd MMMM yyyy";
        this.appCompatActivity = appCompatActivity;
    }

    /**
     * Agrega boton back en los toolbar.
     * @param toolbar
     */
    void agregarBtnBack(final Toolbar toolbar, final Context context, final int id) {
        int blankC = Color.parseColor("#FFFFFF");
        toolbar.setTitleTextColor(blankC);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String res = context.getResources().getResourceName(id);
                String nameLayout = res.substring(res.indexOf("/") + 1);
                if (nameLayout.equals("activity_main")) {
                    System.exit(0);
                }
                else {appCompatActivity.inicio();}
            }
        });
    }

    /**
     * Metodo para insertar una row en la tabla "movimientos".
     * @param monto
     * @param concepto
     * @param categoria
     * @param fecha
     * @param tipo
     * @param mDatabaseHelper
     * @param context
     */
    void AddData(String monto, String concepto, String categoria, String fecha, int tipo, DatabaseHelper mDatabaseHelper, Context context) {
        try {
            boolean insertData = mDatabaseHelper.addData(monto, concepto, categoria, fecha, tipo);

            if (insertData) {
                Toast.makeText(context,"Se cargo correctamente.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context,"con Errores", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Actualiza la fecha actual en la pantalla Inicio.
     */
    void actualizar_fecha(TextView fecha, Context context) {
        try {
            Locale locale = new Locale("es", "AR");
            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
            dateFormatSymbols.setWeekdays(new String[]{
                    "Unused",
                    "Domingo",
                    "Lunes",
                    "Martes",
                    "Miercoles",
                    "Jueves",
                    "Viernes",
                    "Sabado",
            });
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato,dateFormatSymbols);
            fecha_actual = simpleDateFormat.format(new Date());
            fecha.setText(fecha_actual);
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

}
