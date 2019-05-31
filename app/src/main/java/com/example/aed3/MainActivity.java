package com.example.aed3;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
    DatabaseHelper mDatabaseHelper;
    Calendar cal;
    DatePickerDialog dat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actualizar_fecha();
        mDatabaseHelper = new DatabaseHelper(this);
    }

    public void cargarIngreso(View view) {
        EditText monto = (EditText)findViewById(R.id.editText2);
        Spinner concepto = (Spinner) findViewById(R.id.lista_conceptos);
        TextView fecha = (TextView) findViewById(R.id.fecha_selected);
        AddData(monto.getText().toString() , concepto.getSelectedItem().toString(), "", fecha.getText().toString());
        setContentView(R.layout.activity_main);
    }

    public void mostrarDetalle(View view) {
        mDatabaseHelper.getReadableDatabase();
        long cantidad = mDatabaseHelper.getProfilesCount();
        Cursor fila = mDatabaseHelper.getData();
        setContentView(R.layout.detalle);
        TableLayout ll = (TableLayout) findViewById(R.id.table_detalle);
        int negroC = Color.parseColor("#000000");
        int grisC = Color.parseColor("#FFADADB3");
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tdate = new TextView(this);
        tdate.setBackgroundColor(grisC);
        tdate.setTextSize(18);
        TextView ttipo = new TextView(this);
        ttipo.setBackgroundColor(grisC);
        ttipo.setTextSize(18);
        TextView tmonto = new TextView(this);
        tmonto.setBackgroundColor(grisC);
        tmonto.setTextSize(18);

        tdate.setText("Fecha");
        tdate.setPadding(3, 3, 3, 3);
        ttipo.setText("Tipo");
        ttipo.setPadding(3, 3, 3, 3);
        tmonto.setText("Monto");
        tmonto.setPadding(3,3,3,3);
        row.addView(tdate);
        row.addView(ttipo);
        row.addView(tmonto);
        ll.addView(row,0);


        for (int i = 1; i < cantidad; i++) {
            if (fila.moveToNext()) {
                row= new TableRow(this);
                lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tdate = new TextView(this);
                tdate.setTextSize(16);
                ttipo = new TextView(this);
                ttipo.setTextSize(16);
                tmonto = new TextView(this);
                tmonto.setTextSize(16);
                tdate.setText(fila.getString(4));
                tdate.setPadding(3, 3, 3, 3);
                ttipo.setText(fila.getString(2));
                ttipo.setPadding(3, 3, 3, 3);
                Double monto = new Double(fila.getDouble(1));
                tmonto.setText(monto.toString());
                tmonto.setPadding(3,3,3,3);
                row.addView(tdate);
                row.addView(ttipo);
                row.addView(tmonto);
                ll.addView(row,i);
            }
            mDatabaseHelper.close();
        }
    }

    public void AddData(String monto, String concepto, String categoria, String fecha) {
        boolean insertData = mDatabaseHelper.addData(monto, concepto, categoria, fecha);

        if (insertData) {
            Toast.makeText(this,"Sin errores", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(getApplicationContext(),"con Errores", Toast.LENGTH_SHORT).show();
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
