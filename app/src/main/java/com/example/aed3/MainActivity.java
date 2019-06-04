package com.example.aed3;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
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
    private Double totalMercado = new Double(0.00);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actualizar_fecha();
        mDatabaseHelper = new DatabaseHelper(this);
    }
    public void totalIngresosDelMes() {

    }
    public void cargarIngreso(View view) {
        EditText monto = (EditText)findViewById(R.id.editText2);
        Spinner concepto = (Spinner) findViewById(R.id.lista_conceptos);
        TextView fecha = (TextView) findViewById(R.id.fecha_selected);
        AddData(monto.getText().toString() , concepto.getSelectedItem().toString(), "", fecha.getText().toString(), 1);
        setContentView(R.layout.activity_main);
        actualizar_fecha();
    }

    public void cargarEgreso(View view) {
        EditText monto = (EditText)findViewById(R.id.editText2Egreso);
        Spinner concepto = (Spinner) findViewById(R.id.conceptosEgreso);
        TextView fecha = (TextView) findViewById(R.id.fecha_selectedEgreso);
        Spinner categoria = (Spinner) findViewById(R.id.categoriasEgresos);
        AddData(monto.getText().toString(), concepto.getSelectedItem().toString(), categoria.getSelectedItem().toString(), fecha.getText().toString(),2);
        setContentView(R.layout.activity_main);
    }

    public void mostrarDetalle(View view) {
        //Cargo Database
        mDatabaseHelper.getReadableDatabase();
        long cantidad = mDatabaseHelper.getProfilesCount();
        Cursor fila = mDatabaseHelper.getData();
        setContentView(R.layout.detalle);
        TableLayout ll = (TableLayout) findViewById(R.id.table_detalle_header);
        TableRow.LayoutParams lt = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT);
        lt.setMargins(1,1,1,1);
        int negroC = Color.parseColor("#000000");
        int blancoC = Color.parseColor("#FFFFFF");
        int grisC = Color.parseColor("#FFADADB3");
        int redC = Color.parseColor("#FF0000");
        int greenC = Color.parseColor("#FF01DF01");
        TableRow row = new TableRow(this);
        row.setBackgroundColor(negroC);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        //Creo los TextView
        TextView tdate = new TextView(this);
        tdate.setLayoutParams(lt);
        tdate.setBackgroundColor(grisC);
        tdate.setTextSize(22);
        TextView ttipo = new TextView(this);
        ttipo.setLayoutParams(lt);
        ttipo.setBackgroundColor(grisC);
        ttipo.setTextSize(22);
        TextView tmonto = new TextView(this);
        tmonto.setLayoutParams(lt);
        tmonto.setBackgroundColor(grisC);
        tmonto.setTextSize(22);
        //Texto centrado
        tdate.setGravity(Gravity.CENTER);
        tmonto.setGravity(Gravity.CENTER);
        ttipo.setGravity(Gravity.CENTER);
        //Cambios en titulos
        tdate.setText("Fecha");
        tdate.setPadding(30,40,90,40);
        ttipo.setText("Tipo");
        ttipo.setPadding(132,40,135,40);
        tmonto.setText("Monto");
        tmonto.setPadding(60,40,60,40);
        //Estilo de los titulos
        tdate.setTypeface(null, Typeface.BOLD);
        ttipo.setTypeface(null, Typeface.BOLD);
        tmonto.setTypeface(null, Typeface.BOLD);
        //Añado los titulos
        row.addView(tdate);
        row.addView(ttipo);
        row.addView(tmonto);
        ll.addView(row,0);

        TableLayout lll = (TableLayout) findViewById(R.id.table_detalle);
        for (int i = 0; i < cantidad; i++) {
            if (fila.moveToNext()) {
                row= new TableRow(this);
                row.setBackgroundColor(negroC);
                lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tdate = new TextView(this);
                tdate.setBackgroundColor(blancoC);
                tdate.setLayoutParams(lt);
                tdate.setTextSize(16);
                ttipo = new TextView(this);
                ttipo.setBackgroundColor(blancoC);
                ttipo.setLayoutParams(lt);
                ttipo.setTextSize(16);
                tmonto = new TextView(this);
                tmonto.setLayoutParams(lt);
                tmonto.setBackgroundColor(blancoC);
                tmonto.setTextSize(16);
                if (fila.getInt(5) == 2) {
                    tmonto.setTextColor(redC);
                }
                else{
                    tmonto.setTextColor(greenC);
                };
                //Cargo las filas
                tdate.setText(fila.getString(4));
                tdate.setPadding(10,40,60,40);
                ttipo.setText(fila.getString(2));
                ttipo.setPadding(10,40,30,40);
                Double monto = new Double(fila.getDouble(1));
                tmonto.setText("$ " + monto.toString());
                tmonto.setPadding(10,40,60,40);
                //Texto centrado
                tdate.setGravity(Gravity.CENTER);
                tmonto.setGravity(Gravity.CENTER);
                ttipo.setGravity(Gravity.CENTER);
                row.addView(tdate);
                row.addView(ttipo);
                row.addView(tmonto);
                lll.addView(row,i);
            }
        }
        mDatabaseHelper.close();
        tablaEgresos();
    }

    public void tablaEgresos() {
        TableLayout le = (TableLayout) findViewById(R.id.table_egresos);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        int negroC = Color.parseColor("#000000");
        row.setBackgroundColor(negroC);
        TableRow.LayoutParams lt = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT);
        lt.setMargins(0,1,0,1);
        //Creo el TextView Mercados
        TextView mercados = new TextView(this);
        int blankC = Color.parseColor("#FFFFFF");
        mercados.setBackgroundColor(blankC);
        mercados.setLayoutParams(lt);
        mercados.setTextSize(18);
        mercados.setText("Mercados:");
        mercados.setGravity(Gravity.START);
        mercados.setPadding(60,30,40,35);
        mercados.setTypeface(null, Typeface.BOLD);
        //
        TextView vmercados = new TextView(this);
        vmercados.setBackgroundColor(blankC);
        vmercados.setLayoutParams(lt);
        vmercados.setGravity(Gravity.CENTER);
        vmercados.setTextSize(18);
        vmercados.setPadding(358,30,60,35);
        String smercados = obtenerEgresoMercado().toString();
        vmercados.setText("$ " + smercados);
        row.addView(mercados);
        row.addView(vmercados);
        le.addView(row,0);
        //Creo el TextView Alquiler
        TableRow row1 = new TableRow(this);
        row1.setBackgroundColor(negroC);
        row1.setLayoutParams(lp);
        TextView alquiler = new TextView(this);
        alquiler.setLayoutParams(lt);
        alquiler.setBackgroundColor(blankC);
        alquiler.setText("Alquiler:");
        alquiler.setGravity(Gravity.START);
        alquiler.setTextSize(18);
        alquiler.setPadding(60,30,40,35);
        alquiler.setTypeface(null, Typeface.BOLD);
        //
        TextView valquiler = new TextView(this);
        valquiler.setLayoutParams(lt);
        valquiler.setBackgroundColor(blankC);
        valquiler.setGravity(Gravity.CENTER);
        valquiler.setPadding(358,30,60,35);
        valquiler.setTextSize(18);
        String salquiler = obtenerEgresoAlquier().toString();
        valquiler.setText("$ " + salquiler);
        row1.addView(alquiler);
        row1.addView(valquiler);
        le.addView(row1,1);
        //Creo el TextView Transporte
        TableRow row2 = new TableRow(this);
        row2.setBackgroundColor(negroC);
        row2.setLayoutParams(lp);
        TextView transporte = new TextView(this);
        transporte.setLayoutParams(lt);
        transporte.setBackgroundColor(blankC);
        transporte.setText("Transporte:");
        transporte.setGravity(Gravity.START);
        transporte.setTextSize(18);
        transporte.setPadding(60,30,40,35);
        transporte.setTypeface(null, Typeface.BOLD);
        //
        TextView vtransporte = new TextView(this);
        vtransporte.setLayoutParams(lt);
        vtransporte.setBackgroundColor(blankC);
        vtransporte.setGravity(Gravity.CENTER);
        vtransporte.setPadding(358,30,60,35);
        vtransporte.setTextSize(18);
        String stransporte = obtenerEgresoTransporte().toString();
        vtransporte.setText("$ " + stransporte);
        row2.addView(transporte);
        row2.addView(vtransporte);
        le.addView(row2,2);
        //Creo el TextView Impuestos
        TableRow row3 = new TableRow(this);
        row3.setBackgroundColor(negroC);
        row3.setLayoutParams(lp);
        TextView impuestos = new TextView(this);
        impuestos.setLayoutParams(lt);
        impuestos.setBackgroundColor(blankC);
        impuestos.setText("Impuestos:");
        impuestos.setGravity(Gravity.START);
        impuestos.setTextSize(18);
        impuestos.setPadding(60,30,40,35);
        impuestos.setTypeface(null, Typeface.BOLD);
        //
        TextView vimpuestos= new TextView(this);
        vimpuestos.setLayoutParams(lt);
        vimpuestos.setBackgroundColor(blankC);
        vimpuestos.setGravity(Gravity.CENTER);
        vimpuestos.setPadding(358,30,60,35);
        vimpuestos.setTextSize(18);
        String simpuestos = obtenerEgresoImpuestos().toString();
        vimpuestos.setText("$ " + simpuestos);
        row3.addView(impuestos);
        row3.addView(vimpuestos);
        le.addView(row3,3);
        //Creo el TextView Otros
        TableRow row4 = new TableRow(this);
        row4.setBackgroundColor(negroC);
        row4.setLayoutParams(lp);
        TextView otros = new TextView(this);
        otros.setLayoutParams(lt);
        otros.setBackgroundColor(blankC);
        otros.setText("Otros:");
        otros.setGravity(Gravity.START);
        otros.setTextSize(18);
        otros.setPadding(60,30,40,35);
        otros.setTypeface(null, Typeface.BOLD);
        //
        TextView votros= new TextView(this);
        votros.setLayoutParams(lt);
        votros.setBackgroundColor(blankC);
        votros.setPadding(358,30,60,35);
        votros.setGravity(Gravity.CENTER);
        votros.setTextSize(18);
        String sotros = obtenerEgresoOtros().toString();
        votros.setText("$ " + sotros);
        row4.addView(otros);
        row4.addView(votros);
        le.addView(row4,4);
    }
    public void setearColoresDetalle() {

    }
    public Double obtenerEgresoMercado() {
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
    }

    public Double obtenerEgresoAlquier() {
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
    }

    public Double obtenerEgresoTransporte() {
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
    }

    public Double obtenerEgresoImpuestos() {
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
    }

    public Double obtenerEgresoOtros() {
        mDatabaseHelper.getReadableDatabase();
        Cursor otros = mDatabaseHelper.getEgresoOtros();
        Double d = new Double(0.00);
        for (int i = 1; i < otros.getColumnCount(); i++)
            if (otros.moveToNext()) {
                d = d + otros.getDouble(1);
                Log.d("Monto: ", d.toString());
            }
        mDatabaseHelper.close();
        return d;
    }


    public void AddData(String monto, String concepto, String categoria, String fecha, int tipo) {
        boolean insertData = mDatabaseHelper.addData(monto, concepto, categoria, fecha, tipo);

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
                Date date = new Date();
                cal = Calendar.getInstance();
                cal.setTime(date);
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

    public void cargarCalendarioEgreso() {
        mTv = (TextView)findViewById(R.id.fecha_selectedEgreso);
        mBtn = (Button)findViewById(R.id.agregar_fechaEgreso);

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

    public void nuevoEgreso(View view) {
        setContentView(R.layout.egreso);
        cargarCalendarioEgreso();
    }

    public void Inicio(View view) {
        setContentView(R.layout.activity_main);
        actualizar_fecha();
    }
}
