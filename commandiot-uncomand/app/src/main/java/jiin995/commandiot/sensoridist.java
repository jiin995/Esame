package jiin995.commandiot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;


public class sensoridist extends Activity
{
    String connref1= new String("wss://iot-jiin995.mybluemix.net/ws/sensback");
    String connref = new String("wss://iot-jiin995.mybluemix.net/ws/sensfront");
    private LineChart graficou1;
    private LineData datasens1 = new LineData();
    private LineChart graficou2;
    private LineData datasens2 = new LineData();
    private static final int GO_TO_MAIN= Menu.FIRST;
    private static final int GO_TO_SENS = GO_TO_MAIN + 1;
    private static final int GO_TO_STREAMING = GO_TO_MAIN + 2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensoridist);
        graficou1=new LineChart(this);
        graficou1=(LineChart) findViewById(R.id.sensoreultrasuono1);
        creategraph(graficou1);
        createline(datasens1, graficou1);
        graficou2=new LineChart(this);
        graficou2=(LineChart) findViewById(R.id.sensoreultrasuono2);
        creategraph(graficou2);
        createline(datasens2, graficou2);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                refresh("sensdist1", connref); //refresh
                refresh("sensdist2", connref1);
                handler.postDelayed(this, 4000);
            }
        }, 4000);
        }
    private void creategraph(LineChart grafico)
    {
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
        dat.setValueTextColor(Color.WHITE);
        grafico.setData(dat);


        Legend l = grafico.getLegend();
        l.setForm(Legend.LegendForm.LINE);
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
        LineData d =grafico.getData();

        if (d != null)
        {

            LineDataSet set = d.getDataSetByIndex(0);

            if (set == null)
            {
                set = createSet();
                d.addDataSet(set);
            }
            d.addXValue("");
            d.addEntry(new Entry(x, set.getEntryCount()), 0);
            grafico.notifyDataSetChanged();
            grafico.setVisibleXRangeMaximum(6);
            grafico.moveViewToX(d.getXValCount() - 7);
        }

    }
    private LineDataSet createSet()
    {

        LineDataSet set = new LineDataSet(null, "");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, GO_TO_MAIN, 0, "Main");
        menu.add(0, GO_TO_SENS, 0, "Sens");
        menu.add(0, GO_TO_STREAMING, 0, "Streaming");
        return super.onCreateOptionsMenu(menu);
    };

    public boolean onOptionsItemSelected(MenuItem item)
    {
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
            case GO_TO_SENS:
            {
                startActivity(new Intent(getApplicationContext(), sens.class));
                finish();
            }
            break;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private float posi,posf;
    private int MIN_DISTANCE=50;
    @Override
    public boolean onTouchEvent(MotionEvent swipe)
    {
        switch(swipe.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                posi = swipe.getX();
                break;
            case MotionEvent.ACTION_UP:
                posf = swipe.getX();
                float deltax = posf - posi;
                if (Math.abs(deltax) > MIN_DISTANCE)
                {
                    if (deltax<0)
                    {
                        startActivity(new Intent(getApplicationContext(), streaming.class));
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(getApplicationContext(), sens.class));
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
        startActivity(new Intent(getApplicationContext(), sens.class));
        finish();
        return ;
    }
    public void refresh(final String tipo,final String connection)
    {
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
                webSocket.send("refresh");

                webSocket.setStringCallback(new WebSocket.StringCallback()
                {
                    @Override
                    public void onStringAvailable(final String s)
                    {

                        //Log.d("CLIENTTAG", s);

                        switch (tipo)
                        {
                            case "sensdist1":
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        float u1 = Float.parseFloat(s);
                                        addValue(u1, graficou1);
                                        TextView textView = (TextView) findViewById(R.id.Sensoredist1);
                                        textView.setText(s);
                                    }
                                });
                            }
                            break;
                            /*case "senstemp": {
                            runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            float t = Float.parseFloat(s);
                            addValue(t, graficot);
                            TextView textView = (TextView) findViewById(R.id.ValoreTemperatura);
                            textView.setText(s);
                            }
                            });
                            }
                            break;*/
                            case "sensdist2":
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        float u2 = Float.parseFloat(s);
                                        addValue(u2, graficou2);
                                        TextView textView = (TextView) findViewById(R.id.Sensoredist2);
                                        textView.setText(s);
                                    }
                                });
                            }
                            break;
                        }
                        webSocket.close();

                    }
                }
                );
            }
        };
        AsyncHttpClient mAsyncHttpClient = AsyncHttpClient.getDefaultInstance();
        mAsyncHttpClient.websocket(connection, "http", mWebSocketConnectCallback);
    }
}