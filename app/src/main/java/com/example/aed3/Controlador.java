package com.example.aed3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Controlador {
    public Calendar cal;
    public DatePickerDialog dat;
    public String fecha_actual;
    public static String formato;

    /**
     * Instancio controlador
     */
    public Controlador() {
        fecha_actual = "";
        formato = "EEEE dd MMMM yyyy";
    }

    /**
     * Agrega boton back en los toolbar.
     * @param toolbar
     */
    public void agregarBtnBack(Toolbar toolbar) {
        int blankC = Color.parseColor("#FFFFFF");
        Toolbar t = toolbar;
        t.setTitleTextColor(blankC);
        t.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
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
    public void AddData(String monto, String concepto, String categoria, String fecha, int tipo, DatabaseHelper mDatabaseHelper, Context context) {
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
    public void actualizar_fecha(TextView fecha, Context context) {
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato,dateFormatSymbols);
            fecha_actual = simpleDateFormat.format(new Date());
            final TextView textViewToChange = fecha;
            textViewToChange.setText(fecha_actual);
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(context,
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

}
