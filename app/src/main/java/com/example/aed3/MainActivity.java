package com.example.aed3;


import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public static String app_name = "AhoRap";
    public String fecha_actual = "";
    public static String formato = "EEEE dd MMMM yyyy";
    public TextView mTv;
    public Button mBtn;
    Calendar cal;
    DatePickerDialog dat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* actualizo la fecha actual cada vez que se instancia la aplicacion */
        actualizar_fecha();

        TextView saldo = (TextView) findViewById(R.id.saldo_actual);
        int saldo_actual = 15200;
        if (saldo_actual > 15200) {
            saldo.setText(Integer.toString(saldo_actual));
        }


    }

    protected void actualizar_fecha() {
        /* instancio la fecha actual. */
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

        /* actualizo la fecha actual cada vez que se instancia la aplicacion */
        final TextView textViewToChange = (TextView) findViewById(R.id.fecha_id);
        textViewToChange.setText(fecha_actual);
    }

    public void cargarCalendario() {
        mTv = (TextView)findViewById(R.id.fecha_selected);
        mBtn = (Button)findViewById(R.id.agregar_fecha);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);
                int año = cal.get(Calendar.YEAR);

                dat = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mTv.setText(dayOfMonth + "/" + month + "/" + year );
                    }
                }, dia, mes, año);
                dat.show();
            }
        });
    }
    public void nuevoIngreso(View view) {

        setContentView(R.layout.ingreso);
        cargarCalendario();
    }


    public void Inicio(View view) {
        setContentView(R.layout.activity_main);
        actualizar_fecha();
    }

    public void nuevoEgreso(View view) {
        setContentView(R.layout.egreso);
        cargarCalendario();
    }
}
