package com.example.aed3;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static String app_name = "AhoRap";
    public String fecha_actual = "";
    public static String formato = "EEEE dd MMMM yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instancio el toolbar y le pongo el titulo.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(app_name);
        setSupportActionBar(toolbar);

        //instancio la fecha actual.
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
        //actualizo la fecha actual cada vez que se instancia la aplicacion
        actualizar_fecha(fecha_actual);
    }

    protected void actualizar_fecha(String fecha_actual) {
        final TextView textViewToChange = (TextView) findViewById(R.id.fecha_id);
        textViewToChange.setText(fecha_actual);
    }
}
