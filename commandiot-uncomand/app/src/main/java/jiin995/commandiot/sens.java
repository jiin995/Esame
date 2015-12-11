package jiin995.commandiot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;



public class  sens extends Activity
{

    private LineChart graficot; //crea un grafico di linee
    LineData dattemp = new LineData(); // crea una nuova linea sul grafico
    String connreft = new String("wss://iot-jiin995.mybluemix.net/ws/datatemp"); //stringa di connessione ai dati della temperatura
    private static final int GO_TO_MAIN= Menu.FIRST;
    private static final int GO_TO_STREAMING = GO_TO_MAIN + 2;
    private static final int GO_TO_SENSORDIST = GO_TO_MAIN+1;
    private float posi,posf;
    private int MIN_DISTANCE=50;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sens); //correla l' activity con il layout giusto
        graficot=new LineChart(this);
        graficot=(LineChart) findViewById(R.id.sensoretemperatura); //dopo aver creato un nuovo oggetto grafico lo collego al grafico nel file di layout
        creategraph(graficot); //definisce le caratteristiche del grafico
        createline(dattemp, graficot); // definisce le caratteristiche della linea e le collega al grafico
        final Handler handler = new Handler(); //creao un handel che ogni 4 secondi va un refresh dei dati
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                refresh("senstemp", connreft); //refresh dei dati della temperatura e quindi del grafico
                handler.postDelayed(this, 4000);
            }
        }, 4000);

    }
    private void creategraph(LineChart grafico)
    {
        // definisce tutte le caratteristiche del grafico
        grafico.setDescription("");
        grafico.setNoDataText("nessun dato disponibile");
        grafico.setHighlightEnabled(true);
        grafico.setTouchEnabled(true);
        grafico.setScaleEnabled(true);
        grafico.setDragEnabled(true);
        grafico.setDrawGridBackground(false);
        grafico.setPinchZoom(true);
        grafico.setBackgroundColor(Color.GRAY);

    }
    public void createline(LineData dat,LineChart grafico)
    {
        // determina le caratteristiche della linea
        dat.setValueTextColor(Color.WHITE);
        grafico.setData(dat);

        // crea la legenda
        Legend l = grafico.getLegend();
        l.setForm(LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = grafico.getXAxis();//asse reale
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);

        YAxis leftAxis = grafico.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = grafico.getAxisRight(); //valori di x a destra del grafico non asse
        rightAxis.setEnabled(false);
    }
    private void addValue(final float x,LineChart grafico)
    {
            // aggiunge al grafico i valori in real-time
        LineData d =grafico.getData();

        if (d != null)
        {

            //se la linea non ha valori si crea un nuovo set di dati per la linea
            LineDataSet set = d.getDataSetByIndex(0);

            // se il set di dati è vuoto lo si crea e lo si correla alla linea
            if (set == null)
            {
                set = createSet();
                d.addDataSet(set);
            }

            d.addXValue("");
            d.addEntry(new Entry(x, set.getEntryCount()), 0); //inserisce un nuovo valore
            grafico.notifyDataSetChanged(); //segnala al grafico che i dati sono cambiati
            grafico.setVisibleXRangeMaximum(6);
            grafico.moveViewToX(d.getXValCount() - 7); //sposta automaticamente l' asse a l' ultimo valore
        }

    }
    private LineDataSet createSet()
    {
        // definisce le caratteristiche del set di valori per la linea
        LineDataSet set = new LineDataSet(null, "");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //crea il menù a tendina per scorrere tra una activity e l' altra
        menu.add(0, GO_TO_MAIN, 0, "Main");
        menu.add(0, GO_TO_SENSORDIST, 0, "Sensdist");
        menu.add(0, GO_TO_STREAMING, 0, "Streaming");
        return super.onCreateOptionsMenu(menu);
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        // listener che ascolta quale dei tasti del menù a tendina è stato premuto
        int id = item.getItemId();
        switch (item.getItemId())
        {
            case GO_TO_MAIN:
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
                break;
            case GO_TO_STREAMING:
            {
                startActivity(new Intent(getApplicationContext(), streaming.class));
                finish();
            }
                break;
            case GO_TO_SENSORDIST:
            {
                startActivity(new Intent(getApplicationContext(), sensoridist.class));
                finish();
            }
            break;
        }
        if (id == R.id.action_settings)
            return true;


        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onTouchEvent(MotionEvent swipe)
    {
        switch(swipe.getAction())
        {
            //gestisce lo swipe tra applicazioni
            case MotionEvent.ACTION_DOWN:
                posi = swipe.getX();
                break;
            case MotionEvent.ACTION_UP:
                posf = swipe.getX();
                float deltax = posf - posi;
                if (Math.abs(deltax) > MIN_DISTANCE)
                {
                    if (deltax<0) //swipe da destra a sinistra
                    {
                        startActivity(new Intent(getApplicationContext(), sensoridist.class)); //apre una nuova activity
                        finish(); // chiude l' activity corrente
                    }
                    else //swipe da sinistra a destra
                    {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }
                break;
        }
        return super.onTouchEvent(swipe);
    }

    @Override
    public void onBackPressed()
    {
        //gestisce il listener del pulsante indietro
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        return ;
    }
    public void refresh(final String tipo,final String connection)
    {
            // gestisce la connessione tra appicazione android e bluemix tramite web socket per un refresh dei dati
            AsyncHttpClient.WebSocketConnectCallback mWebSocketConnectCallback = new AsyncHttpClient.WebSocketConnectCallback()
            {
                @Override
                public void onCompleted(Exception ex, final WebSocket webSocket)
                {
                    if (ex != null)
                    {
                        runOnUiThread(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                Toast.makeText(getApplicationContext(), "Error Refresh", Toast.LENGTH_SHORT).show();
                            }
                        });
                        ex.printStackTrace();
                        return;
                    }
                    webSocket.send("refresh"); // invia il messaggio al socket in ascolto su bluemix per il refresh

                    webSocket.setStringCallback(new WebSocket.StringCallback() {
                        @Override
                        public void onStringAvailable(final String s) {

                            //Log.d("CLIENTTAG", s);

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    float t = Float.parseFloat(s);
                                    addValue(t, graficot);
                                    TextView textView = (TextView) findViewById(R.id.ValoreTemperatura); // mostra valore corrente della temperatura
                                    textView.setText(s); // mostra valore corrente della temperatura


                                    /*   case "sensdist1": {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            float u1 = Float.parseFloat(s);
                                            addValue(u1, graficou);
                                            Toast.makeText(getApplicationContext(), "plotto sens 1", Toast.LENGTH_SHORT).show();
                                            TextView textView = (TextView) findViewById(R.id.ValoreSensore);
                                            textView.setText(s);
                                        }

                                    });
                                }
                                break;*/
                                    //case "senstemp": {


                                    //break;

                                /*case "sensdist2": {

                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            float u2 = Float.parseFloat(s);
                                            addValue(u2, graficou);
                                            Toast.makeText(getApplicationContext(), "plotto sens 2", Toast.LENGTH_SHORT).show();
                                            TextView textView = (TextView) findViewById(R.id.ValoreSensore);
                                            textView.setText(s);
                                        }
                                    });
                                }
                                break;*/
                                }
                            });


                        }
                    });
                    webSocket.close();
                }
            };
            AsyncHttpClient mAsyncHttpClient = AsyncHttpClient.getDefaultInstance();
            mAsyncHttpClient.websocket(connection, "http", mWebSocketConnectCallback);
    }



    public void msgOnclick(View view)
    {
        // gestisce il listener del pulsante che premuto invia dati allo schermo tramite bluemix
        EditText msg = (EditText) findViewById(R.id.message);
        String m="T" +msg.getText().toString();
        message(m);
        msg.setText("");
        Toast.makeText(getApplicationContext(), "Comando inviato", Toast.LENGTH_SHORT).show();
    }
    public void message (final String m)
    {
        // gestisce la connessione tra appicazione android e bluemix tramite web socket per inviare dati allo schermo
        AsyncHttpClient.WebSocketConnectCallback mWebSocketConnectCallback = new AsyncHttpClient.WebSocketConnectCallback()
        {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket)
            {
                if (ex != null)
                {
                    runOnUiThread(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            Toast.makeText(getApplicationContext(), "Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    ex.printStackTrace();
                    return;
                }
                webSocket.send(m);

                webSocket.setStringCallback(new WebSocket.StringCallback()
                {
                    @Override
                    public void onStringAvailable(final String s)
                    {

                        Log.d("CLIENTTAG", s);
                        runOnUiThread(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                //EditText editText = (EditText) findViewById(R.id.editText);
                                //editText.setText(s);
                            }
                        });

                    }
                });
                webSocket.close();

            }

        };
        AsyncHttpClient mAsyncHttpClient = AsyncHttpClient.getDefaultInstance();
        mAsyncHttpClient.websocket("wss://iot-jiin995.mybluemix.net/ws/message", "http", mWebSocketConnectCallback);
    }
}

