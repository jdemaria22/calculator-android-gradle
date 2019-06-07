package com.example.aed3;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public String fecha_actual = "";
    public static String formato = "EEEE dd MMMM yyyy";
    public TextView mTv;
    public Button mBtn;
    DatabaseHelper mDatabaseHelper;
    Calendar cal;
    DatePickerDialog dat;

    @RequiresApi(api = VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actualizar_fecha();
        mDatabaseHelper = new DatabaseHelper(this);
        actualizarSaldo();
    }

    /**
     * Metodo que retorna el total de ingresos del mes actual.
     * @return
     */
    @RequiresApi(api = VERSION_CODES.O)
    public Double totalIngresosDelMes() {
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
     * Metodo que retorna el total de egresos en el mes actual.
     * @return
     */
    @RequiresApi(api = VERSION_CODES.O)
    public Double totalEgresosDelMes() {
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
    @RequiresApi(api = VERSION_CODES.O)
    public Double totalTarjetaCredito(){
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
     * Actualiza el saldo en la pantalla Inicio.
     */
    @RequiresApi(api = VERSION_CODES.O)
    public void actualizarSaldo(){
        try {
            Double egreso = new Double(totalEgresosDelMes());
            Double ingreso = new Double(totalIngresosDelMes());
            Double total = new Double(0.00);
            total = ingreso - egreso;
            float d = total.floatValue();
            String str = String.format("%.02f", d);
            total = new Double(str);
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
            total_egreso.setText("$ "+ String.format("%.02f",totalEgresosDelMes()));
            TextView total_ingreso = findViewById(R.id.total_ingreso);
            total_ingreso.setText("$ "+ String.format("%.02f",totalIngresosDelMes()));
            TextView total_tc = findViewById(R.id.total_tc);
            total_tc.setText("$ " + String.format("%.02f", totalTarjetaCredito()));
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
                            e.toString() , Toast.LENGTH_LONG);
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
            EditText monto = (EditText)findViewById(R.id.editText2);
            Spinner concepto = (Spinner) findViewById(R.id.lista_conceptos);
            TextView fecha = (TextView) findViewById(R.id.fecha_selected);
            int flag = 0;
            if (validarInput(monto.getText().toString(),"Monto")
              & validarInput(concepto.getSelectedItem().toString(), "Concepto")
              & validarInput(fecha.getText().toString(), "Fecha")) {
                AddData(monto.getText().toString() , concepto.getSelectedItem().toString(), "", fecha.getText().toString(), 1);
                setContentView(R.layout.activity_main);
                actualizar_fecha();
                actualizarSaldo();
            }
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString() , Toast.LENGTH_LONG);
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
            EditText monto = (EditText)findViewById(R.id.editText2Egreso);
            Spinner concepto = (Spinner) findViewById(R.id.conceptosEgreso);
            TextView fecha = (TextView) findViewById(R.id.fecha_selectedEgreso);
            Spinner categoria = (Spinner) findViewById(R.id.categoriasEgresos);

            if (validarInput(monto.getText().toString(),"Monto")
                    & validarInput(concepto.getSelectedItem().toString(), "Concepto")
                    & validarInput(fecha.getText().toString(), "Fecha")
                    & validarInput(categoria.getSelectedItem().toString(),"Categoria")) {
                AddData(monto.getText().toString(), concepto.getSelectedItem().toString(), categoria.getSelectedItem().toString(), fecha.getText().toString(),2);
                setContentView(R.layout.activity_main);
                actualizar_fecha();
                actualizarSaldo();
            }
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString() , Toast.LENGTH_LONG);
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
                if (input == null | input.length() == 0 | input.equals("dd/mm/aa")) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Debe ingresar " + tipo, Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                } else {
                    return true;
                }
            }else {
                if (input == null | input.length() == 0) {
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
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return false;
        }
    }

    /**
     * Redirige hacia la pantalla Detalle y dibuja la tabla dinamica con los movimientos.
     * @param view
     */
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
            LinearLayout linea = findViewById(R.id.linea_detalle);

            TableLayout ll = (TableLayout) findViewById(R.id.table_detalle_header);
            //fecha
            TableRow.LayoutParams lt = new TableRow.LayoutParams(280,200);
            lt.setMargins(1,1,1,1);
            //tipo
            TableRow.LayoutParams lttipo = new TableRow.LayoutParams(380,200);
            lttipo.setMargins(1,1,1,1);
            //Monto
            TableRow.LayoutParams ltmonto = new TableRow.LayoutParams(300,200);
            ltmonto.setMargins(1,1,1,1);
            //

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
            ttipo.setLayoutParams(lttipo);
            ttipo.setBackgroundColor(grisC);
            ttipo.setTextSize(22);
            TextView tmonto = new TextView(this);
            tmonto.setLayoutParams(ltmonto);
            tmonto.setBackgroundColor(grisC);
            tmonto.setTextSize(22);
            //Texto centrado
            tdate.setGravity(Gravity.CENTER);
            tmonto.setGravity(Gravity.CENTER);
            ttipo.setGravity(Gravity.CENTER);
            //Cambios en titulos
            tdate.setText("Fecha");
            tdate.setPadding(0,40,0,40);
            ttipo.setText("Tipo");
            ttipo.setPadding(50,40,50,40);
            tmonto.setText("Monto");
            tmonto.setPadding(5,40,5,40);
            //Estilo de los titulos
            tdate.setTypeface(null, Typeface.BOLD);
            ttipo.setTypeface(null, Typeface.BOLD);
            tmonto.setTypeface(null, Typeface.BOLD);
            //Añado los titulos
            row.addView(tdate);
            row.addView(ttipo);
            row.addView(tmonto);
            ll.addView(row,0);

            if (cantidad < 1) {
                LinearLayout lnt = findViewById(R.id.linea_detalle);
                lnt.setBackgroundColor(blancoC);
                TextView nt = findViewById(R.id.txtv_nt);
                nt.setVisibility(View.VISIBLE);
            }else
            {
                TextView nt = findViewById(R.id.txtv_nt);
                nt.setVisibility(View.GONE);
            }

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
                    ttipo.setLayoutParams(lttipo);
                    ttipo.setTextSize(16);
                    tmonto = new TextView(this);
                    tmonto.setLayoutParams(ltmonto);
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
                    ttipo.setText(fila.getString(2));
                    Double monto = new Double(fila.getDouble(1));
                    tmonto.setText("$ " + String.format("%.02f",monto));
                    //Seteo padding dinamicos

                    tdate.setPadding(0,0,0,0);
                    ttipo.setPadding(0,0,0,0);
                    tmonto.setPadding(0,0,0,0);

                    //Texto centrado
                    tdate.setGravity(Gravity.CENTER);
                    tmonto.setGravity(Gravity.CENTER);
                    ttipo.setGravity(Gravity.CENTER);
                    //ttipo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    row.addView(tdate);
                    row.addView(ttipo);
                    row.addView(tmonto);
                    lll.addView(row,i);
                }
            }
            mDatabaseHelper.close();
            tablaEgresos();
        }catch (Exception e ) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Dibuja en la pantalla Detalle la tabla con los totales de egresos de cada Categoria
     */
    public void tablaEgresos() {
        try {
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
            vmercados.setPadding(300,30,250,35);
            String smercados = String.format("%.02f",obtenerEgresoMercado());
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
            valquiler.setPadding(300,30,250,35);
            valquiler.setTextSize(18);
            String salquiler = String.format("%.02f",obtenerEgresoAlquier());
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
            String stransporte = String.format("%.02f",obtenerEgresoTransporte());
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
            String simpuestos = String.format("%.02f",obtenerEgresoImpuestos());
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
            String sotros = String.format("%.02f", obtenerEgresoOtros());
            votros.setText("$ " + sotros);
            row4.addView(otros);
            row4.addView(votros);
            le.addView(row4,4);
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Retorna los egresos de categoria "Mercado".
     * @return
     */
    public Double obtenerEgresoMercado() {
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
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Alquiler".
     * @return
     */
    public Double obtenerEgresoAlquier() {
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
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Transporte".
     * @return
     */
    public Double obtenerEgresoTransporte() {
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
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Impuestos".
     * @return
     */
    public Double obtenerEgresoImpuestos() {
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
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Retorna los egresos de Categoria "Otros".
     * @return
     */
    public Double obtenerEgresoOtros() {
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
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
            return 0.00;
        }
    }

    /**
     * Metodo para insertar una row en la tabla "movimientos".
     * @param monto
     * @param concepto
     * @param categoria
     * @param fecha
     * @param tipo
     */
    public void AddData(String monto, String concepto, String categoria, String fecha, int tipo) {
        try {
            boolean insertData = mDatabaseHelper.addData(monto, concepto, categoria, fecha, tipo);

            if (insertData) {
                Toast.makeText(getApplicationContext(),"Se cargo correctamente.", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(getApplicationContext(),"con Errores", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }

    }


    /**
     * Actualiza la fecha actual en la pantalla Inicio.
     */
    public void actualizar_fecha() {
        try {

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
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }


    /**
     * Cargar un nuevo calendario en la pantalla Ingresos.
     */
    public void cargarCalendario() {
        try {
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
                            mTv.setText(dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, año, mes, dia);
                    dat.show();
                }
            });
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }

    }

    /**
     * Cargar un nuevo calendario en la pantalla Egresos.
     */
    public void cargarCalendarioEgreso() {
        try {
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
                            mTv.setText(dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, año, mes, dia);
                    dat.show();
                }
            });
        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    /**
     * Va hacia la pantalla de carga de Ingresos.
     * @param view
     */
    public void nuevoIngreso(View view) {
        setContentView(R.layout.ingreso);
        cargarCalendario();
    }

    /**
     * Va hacia la pantalla de carga de Egresos.
     * @param view
     */
    public void nuevoEgreso(View view) {
        setContentView(R.layout.egreso);
        cargarCalendarioEgreso();
    }

    /**
     * Vuelve al la pantalla del inicio.
     * @param view
     */
    @RequiresApi(api = VERSION_CODES.O)
    public void Inicio(View view) {
        setContentView(R.layout.activity_main);
        actualizar_fecha();
        actualizarSaldo();
    }
}
