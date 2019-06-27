package com.example.aed3;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public TextView mTv;
    public Button mBtn;
    public Controlador controlador = new Controlador(this);
    public Calculos calculos = new Calculos();
    DatabaseHelper mDatabaseHelper;
    Calendar cal;
    DatePickerDialog dat;

    /**
     * onCreate
     * @param savedInstanceState
     */
    @RequiresApi(api = VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            actualizar_fecha();
            mDatabaseHelper = new DatabaseHelper(this);
            actualizarSaldo();
            Toolbar t = findViewById(R.id.toolbar);
            controlador.agregarBtnBack(t, getApplicationContext(),R.layout.activity_main);

        }   catch (Exception e) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en onCreate" + e.toString() , Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Actualiza el saldo en la pantalla Inicio.
     */
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @RequiresApi(api = VERSION_CODES.O)
    public void actualizarSaldo(){
        try {
            Double egreso = calculos.totalEgresosDelMes(mDatabaseHelper);
            Double ingreso = calculos.totalIngresosDelMes(mDatabaseHelper);
            Double total = new Double(0.00);
            total = ingreso - egreso;
            float d = total.floatValue();
            String str = String.format("%.02f", d);
            total = new Double(str.replace(",", "."));
            TextView saldo = findViewById(R.id.saldo_actual);
            int redC = Color.parseColor("#FF0000");
            int greenC = Color.parseColor("#FF01DF01");//FFE981
            int yellowC = Color.parseColor("#FFFFEB3B");
            if (total >= 15000){
                saldo.setTextColor(greenC);
            }
            else{
                if (total < 7000){
                    saldo.setTextColor(redC);
                }
                else{
                    saldo.setTextColor(yellowC);
                }
            }
            saldo.setText("$ " + str);
            TextView total_egreso = findViewById(R.id.total_egreso);
            total_egreso.setText("$ "+ String.format("%.02f",calculos.totalEgresosDelMes(mDatabaseHelper)));
            TextView total_ingreso = findViewById(R.id.total_ingreso);
            total_ingreso.setText("$ "+ String.format("%.02f",calculos.totalIngresosDelMes(mDatabaseHelper)));
            TextView total_tc = findViewById(R.id.total_tc);
            total_tc.setText("$ " + String.format("%.02f", calculos.totalTarjetaCredito(mDatabaseHelper)));
            //seteo en negrito los labels
            TextView total_egreso_label = findViewById(R.id.row);
            total_egreso_label.setTypeface(null, Typeface.BOLD);
            TextView total_ingreso_label = findViewById(R.id.row1);
            total_ingreso_label.setTypeface(null, Typeface.BOLD);
            TextView total_tc_label = findViewById(R.id.row2);
            total_tc_label.setTypeface(null, Typeface.BOLD);
        } catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en actualizarSaldo" + e.toString() , Toast.LENGTH_LONG);
            toast1.show();
        }

    }

    /**
     * Metodo que carga un Ingreso en la tabla movimientos.
     * @param view
     */
    @RequiresApi(api = VERSION_CODES.O)
    public void cargarIngreso(View view) {
        try{
            EditText monto = findViewById(R.id.editText2);
            Spinner concepto = findViewById(R.id.lista_conceptos);
            TextView fecha = findViewById(R.id.fecha_selected);
            if (validarInput(monto.getText().toString(),"Monto")
                    & validarInput(concepto.getSelectedItem().toString(), "Concepto")
                    & validarInput(fecha.getText().toString(), "Fecha")) {
                controlador.AddData(monto.getText().toString() , concepto.getSelectedItem().toString(), "", fecha.getText().toString(), 1, mDatabaseHelper, getApplicationContext());
                inicio();
            }
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en cargarIngreso" + e.toString() , Toast.LENGTH_LONG);
            toast1.show();
        }

    }

    /**
     * Metodo que carga un Egreso en la tabla movimientos.
     * @param view
     */
    @RequiresApi(api = VERSION_CODES.O)
    public void cargarEgreso(View view) {
        try {
            EditText monto = findViewById(R.id.editText2Egreso);
            Spinner concepto = findViewById(R.id.conceptosEgreso);
            TextView fecha = findViewById(R.id.fecha_selectedEgreso);
            Spinner categoria = findViewById(R.id.categoriasEgresos);

            if (validarInput(monto.getText().toString(),"Monto")
                    & validarInput(concepto.getSelectedItem().toString(), "Concepto")
                    & validarInput(fecha.getText().toString(), "Fecha")
                    & validarInput(categoria.getSelectedItem().toString(),"Categoria")) {
                controlador.AddData(monto.getText().toString(), concepto.getSelectedItem().toString(), categoria.getSelectedItem().toString(), fecha.getText().toString(),2,mDatabaseHelper,getApplicationContext());
                inicio();
            }
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en cargarEgreso" + e.toString() , Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Valida todos los input al momento de hacer un ingres u egreso, en ambas pantallas.
     * @param input
     * @param tipo
     * @return
     */
    public boolean validarInput(String input, String tipo) {
        try {
            if (tipo.equals("Fecha")) {
                assert input != null;
                if ((input.length() == 0) | input.equals("dd/mm/aa")) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Debe ingresar " + tipo, Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                } else {
                    return true;
                }
            }else {
                assert input != null;
                if (input.length() == 0) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Debe ingresar " + tipo, Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en validarInput" +e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return false;
        }
    }

    /**
     * Redirige hacia la pantalla Detalle y dibuja la tabla dinamica con los movimientos.
     * @param view
     */
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @RequiresApi(api = VERSION_CODES.O)
    public void mostrarDetalle(View view) {
        try {
            //Cargo database

            mDatabaseHelper.getReadableDatabase();
            int negroC = Color.parseColor("#000000");
            int blancoC = Color.parseColor("#FFFFFF");
            int grisC = Color.parseColor("#FFADADB3");
            int redC = Color.parseColor("#FF0000");
            int greenC = Color.parseColor("#FF01DF01");
            long cantidad = mDatabaseHelper.getProfilesCount();
            Cursor fila = mDatabaseHelper.getData();
            setContentView(R.layout.detalle);
            Toolbar t = findViewById(R.id.toolbarD);
            controlador.agregarBtnBack(t,getApplicationContext(),R.layout.detalle);
            TableLayout ll = findViewById(R.id.table_detalle_header);
            //////
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int lff = (int) ((displayMetrics.widthPixels - 40) * 0.30);
            int lmm = (int) ((displayMetrics.widthPixels - 40) * 0.30);
            int ltt = (int) ((displayMetrics.widthPixels - 40) * 0.35);
            TableRow.LayoutParams lt = new TableRow.LayoutParams(lff ,200);
            lt.setMargins(1,1,1,1);
            TableRow.LayoutParams lttipo = new TableRow.LayoutParams(lmm,200);
            lttipo.setMargins(1,1,1,1);
            LinearLayout lnt1 = findViewById(R.id.linea_detalle);
            TableRow.LayoutParams ltmonto = new TableRow.LayoutParams(ltt,200);
            ltmonto.setMargins(1,1,1,1);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(negroC);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView tdate = new TextView(this);
            tdate.setLayoutParams(lt);
            tdate.setBackgroundColor(grisC);
            tdate.setTextSize(22);
            TextView ttipo = new TextView(this);
            ttipo.setLayoutParams(lttipo);
            ttipo.setBackgroundColor(grisC);
            ttipo.setTextSize(22);
            TextView tmonto = new TextView(this);
            tmonto.setLayoutParams(ltmonto);
            tmonto.setBackgroundColor(grisC);
            tmonto.setTextSize(22);
            tdate.setGravity(Gravity.CENTER);
            tmonto.setGravity(Gravity.CENTER);
            ttipo.setGravity(Gravity.CENTER);
            tdate.setText("Fecha");
            tdate.setPadding(0,40,0,40);
            ttipo.setText("Tipo");
            ttipo.setPadding(50,40,50,40);
            tmonto.setText("Monto");
            tmonto.setPadding(5,40,5,40);
            tdate.setTypeface(null, Typeface.BOLD);
            ttipo.setTypeface(null, Typeface.BOLD);
            tmonto.setTypeface(null, Typeface.BOLD);
            //Añado los titulos
            row.addView(tdate);
            row.addView(ttipo);
            row.addView(tmonto);
            ll.addView(row,0);

            if (cantidad < 1) {
                @SuppressLint("CutPasteId") LinearLayout lnt = findViewById(R.id.linea_detalle);
                lnt.setBackgroundColor(blancoC);
                TextView nt = findViewById(R.id.txtv_nt);
                nt.setVisibility(View.VISIBLE);
            }else
            {
                TextView nt = findViewById(R.id.txtv_nt);
                nt.setVisibility(View.GONE);
            }

            TableLayout lll = findViewById(R.id.table_detalle);

            for (int i = 0; i < cantidad; i++)
                if (fila.moveToNext()) {
                    row = new TableRow(this);
                    row.setBackgroundColor(negroC);
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    tdate = new TextView(this);
                    tdate.setBackgroundColor(blancoC);
                    tdate.setLayoutParams(lt);
                    tdate.setTextSize(16);
                    ttipo = new TextView(this);
                    ttipo.setBackgroundColor(blancoC);
                    ttipo.setLayoutParams(lttipo);
                    ttipo.setTextSize(16);
                    tmonto = new TextView(this);
                    tmonto.setLayoutParams(ltmonto);
                    tmonto.setBackgroundColor(blancoC);
                    tmonto.setTextSize(16);
                    if (fila.getInt(5) == 2) {
                        tmonto.setTextColor(redC);
                    } else tmonto.setTextColor(greenC);

                    tdate.setText(fila.getString(4));
                    ttipo.setText(fila.getString(2));
                    Double monto = fila.getDouble(1);
                    tmonto.setText("$ " + String.format("%.02f", monto));
                    tdate.setPadding(0, 0, 0, 0);
                    ttipo.setPadding(0, 0, 0, 0);
                    tmonto.setPadding(0, 0, 0, 0);
                    tdate.setGravity(Gravity.CENTER);
                    tmonto.setGravity(Gravity.CENTER);
                    ttipo.setGravity(Gravity.CENTER);
                    row.addView(tdate);
                    row.addView(ttipo);
                    row.addView(tmonto);
                    lll.addView(row, i);
                }
            mDatabaseHelper.close();
            tablaEgresos();
        }catch (Exception e ) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en mostrarDetalle" +  e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Dibuja en la pantalla Detalle la tabla con los totales de egresos de cada Categoria
     */
    @SuppressLint("SetTextI18n")
    public void tablaEgresos() {
        try {

            TableLayout le = findViewById(R.id.table_egresos);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            int negroC = Color.parseColor("#000000");
            row.setBackgroundColor(negroC);
            TableRow.LayoutParams lt = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            float spTextSize = 6;
            float textSize = spTextSize * getResources().getDisplayMetrics().scaledDensity;
            lt.setMargins(0,1,0,1);
            TextView mercados = new TextView(this);
            int blankC = Color.parseColor("#FFFFFF");
            mercados.setBackgroundColor(blankC);
            mercados.setLayoutParams(lt);
            mercados.setTextSize(18);
            mercados.setText("Mercados:");
            mercados.setGravity(Gravity.START);
            mercados.setPadding(60,30,40,35);
            mercados.setTypeface(null, Typeface.BOLD);
            TextView vmercados = new TextView(this);
            vmercados.setBackgroundColor(blankC);
            vmercados.setLayoutParams(lt);
            vmercados.setGravity(Gravity.CENTER);
            vmercados.setTextSize(18);
            vmercados.setPadding(300,30,250,35);
            @SuppressLint("DefaultLocale") String smercados = String.format("%.02f",calculos.obtenerEgresoMercado(mDatabaseHelper,getApplicationContext()));
            vmercados.setText("$ " + smercados);
            row.addView(mercados);
            row.addView(vmercados);
            le.addView(row,0);
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
            valquiler.setPadding(300,30,250,35);
            valquiler.setTextSize(18);
            @SuppressLint("DefaultLocale") String salquiler = String.format("%.02f",calculos.obtenerEgresoAlquier(mDatabaseHelper,getApplicationContext()));
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
            vtransporte.setPadding(300,30,250,35);
            vtransporte.setTextSize(18);
            @SuppressLint("DefaultLocale") String stransporte = String.format("%.02f",calculos.obtenerEgresoTransporte(mDatabaseHelper,getApplicationContext()));
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
            vimpuestos.setPadding(300,30,250,35);
            vimpuestos.setTextSize(18);
            @SuppressLint("DefaultLocale") String simpuestos = String.format("%.02f",calculos.obtenerEgresoImpuestos(mDatabaseHelper,getApplicationContext()));
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
            votros.setPadding(300,30,250,35);
            votros.setGravity(Gravity.CENTER);
            votros.setTextSize(18);
            @SuppressLint("DefaultLocale") String sotros = String.format("%.02f", calculos.obtenerEgresoOtros(mDatabaseHelper,getApplicationContext()));
            votros.setText("$ " + sotros);
            row4.addView(otros);
            row4.addView(votros);
            le.addView(row4,4);
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en tablaEgresos" + e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Actualiza la fecha actual en la pantalla Inicio.
     */
    public void actualizar_fecha() {
        TextView fecha = findViewById(R.id.fecha_id);
        controlador.actualizar_fecha(fecha ,getApplicationContext());
    }

    /**
     * Cargar un nuevo calendario en la pantalla Ingresos.
     */
    public void cargarCalendario() {
        try {
            mTv = findViewById(R.id.fecha_selected);
            mBtn = findViewById(R.id.agregar_fecha);

            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    int dia = cal.get(Calendar.DAY_OF_MONTH);
                    int mes = cal.get(Calendar.MONTH);
                    int año = cal.get(Calendar.YEAR);
                    dat = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mTv.setText(dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, año, mes, dia);
                    dat.show();
                }
            });
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en cargarCalendario" +  e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }

    }

    /**
     * Cargar un nuevo calendario en la pantalla Egresos.
     */
    public void cargarCalendarioEgreso() {
        try {
            mTv = findViewById(R.id.fecha_selectedEgreso);
            mBtn = findViewById(R.id.agregar_fechaEgreso);

            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    int dia = cal.get(Calendar.DAY_OF_MONTH);
                    int mes = cal.get(Calendar.MONTH);
                    int año = cal.get(Calendar.YEAR);

                    dat = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mTv.setText(dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, año, mes, dia);
                    dat.show();
                }
            });
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Error en cargarCalendarioEgreso" + e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Va hacia la pantalla de carga de Ingresos.
     * @param view
     */
    public void nuevoIngreso(View view) {
        setContentView(R.layout.ingreso);
        Toolbar t = findViewById(R.id.toolbarI);
        this.findViewById(android.R.id.content);
        controlador.agregarBtnBack(t,getApplicationContext(),R.layout.ingreso);
        cargarCalendario();
    }

    /**
     * Va hacia la pantalla de carga de Egresos.
     * @param view
     */
    public void nuevoEgreso(View view) {
        setContentView(R.layout.egreso);
        Toolbar t = findViewById(R.id.toolbarE);
        controlador.agregarBtnBack(t,getApplicationContext(),R.layout.egreso);
        cargarCalendarioEgreso();
    }

    /**
     * Vuelve al la pantalla del inicio desde el detalle.
     */
    @RequiresApi(api = VERSION_CODES.O)
    public void Inicio(View view) {
        inicio();
    }

    /**
     * vuelve a la pantalla inicio.
     */
    @RequiresApi(api = VERSION_CODES.O)
    public void inicio() {
        setContentView(R.layout.activity_main);
        Toolbar t = findViewById(R.id.toolbar);
        controlador.agregarBtnBack(t,getApplicationContext(),R.layout.activity_main);
        actualizar_fecha();
        actualizarSaldo();
    }
}
