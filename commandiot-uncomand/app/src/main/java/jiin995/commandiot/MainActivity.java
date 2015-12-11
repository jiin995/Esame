package jiin995.commandiot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;



public class MainActivity extends Activity
{
    String conncom = new String("wss://iot-jiin995.mybluemix.net/ws/command"); //stringa di connessione al socket dei comandi
    private static final int GO_TO_SENSOR = Menu.FIRST;
    private static final int GO_TO_STREAMING = GO_TO_SENSOR + 2;
    private static final int GO_TO_SENSORDIST = GO_TO_SENSOR+1;

 /* String connref = new String("wss://iot-jiin995.mybluemix.net/ws/data");
    String connreft = new String("wss://iot-jiin995.mybluemix.net/ws/datatemp");*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //correla l' activity con il giusto layout


        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                refresh("null", connref); //refresh
                refresh("null", connreft); //refreshtemp
                handler.postDelayed(this, 4000);
            }
        }, 4000);*/
    }

    public void ledOnClick(View view)
    {
        //gestisce il listener del tasto che controlla il led
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Led");
        command("l", conncom);
    }

    public void avantiOnclick(View view)
    {
        //gestisce il listener del tasto che controlla il movimento in avanti
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Avanti");
        command("A", conncom);
    }

    public void indietroOnclick(View view)
    {
        //gestisce il listener del tasto che controlla il movimento in indietro
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Indietro");
        command("I", conncom);
    }

    public void stopOnclick(View view)
    {
        //gestisce il listener del tasto che ferma il robot
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Stop");
        command("s", conncom);
    }

    public void destraOnclick(View view)
    {
        //gestisce il listener del tasto che controlla il movimento  a destra
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Destra");
        command("D", conncom);
    }

    public void sinistraOnclick(View view)
    {
        //gestisce il listener del tasto che controlla il movimento a sinistra
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Sinistra");
        command("S", conncom);
    }

    public void gasmenoOnclick(View view)
    {
        //gestisce il listener del tasto che diminuisce la velocità del robot
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Gas -");
        command("g", conncom);
    }

    public void gaspiùOnclick(View view)
    {
        //gestisce il listener del tasto che aumenta la velocità del robot
        TextView textView = (TextView) findViewById(R.id.ComandText);
        textView.setText("Gas +");
        command("G", conncom);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //crea il menù a tendina per scorrere tra una activity e l' altra
        menu.add(0, GO_TO_SENSOR, 0, "Sens");
        menu.add(0, GO_TO_SENSORDIST, 0, "SensDist");
        menu.add(0, GO_TO_STREAMING, 0, "Stream");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        // listener che ascolta quale dei tasti del menù a tendina è stato premuto
        int id = item.getItemId();
        switch (item.getItemId())
        {
            case GO_TO_SENSOR:
            {
                startActivity(new Intent(getApplicationContext(), sens.class));
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
        if (id == R.id.action_settings) // se viene premuto un pulsante del menu ritorna vero
            return true;


        return super.onOptionsItemSelected(item);
    }

    private float posi,posf;
    private int MIN_DISTANCE=50;
    @Override
    public boolean onTouchEvent(MotionEvent swipe)
    {
        //gestisce lo swipe tra applicazioni
        switch(swipe.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                posi = swipe.getX();
                break;
            case MotionEvent.ACTION_UP:
                posf = swipe.getX();
                float deltax = posf - posi;
                if ((Math.abs(deltax) > MIN_DISTANCE) &&(deltax<0)) //swipe da destra a sinistra
                {
                    startActivity(new Intent(getApplicationContext(), sens.class)); //apre una nuova activity
                    finish(); // chiude l' activity corrente
                }

        }
        return super.onTouchEvent(swipe);
    }

    @Override
    public void onBackPressed()
    {
        //gestisce il listener del pulsante indietro
        finish();//chiude l' activity e quindi l' applicazione
    }
    public void command(final String message, final String connection) {
        // gestisce la connessione tra appicazione android e bluemix tramite web socket
        AsyncHttpClient.WebSocketConnectCallback mWebSocketConnectCallback = new AsyncHttpClient.WebSocketConnectCallback() // configura il socket
        {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket)
            {
                if (ex != null)  // in caso in cui la connessione non avviene si genera un messaggio di errore fatto eseguire in un thread
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
                webSocket.send(message); // invia il messaggio al socket in ascolto su bluemix

                webSocket.setStringCallback(new WebSocket.StringCallback()
                {
                //gestisce la connessione i dati che provengono da bluemix all' applicazione
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
                webSocket.close(); //chiude il socket aperto nell' applicazione

            }

        };
        AsyncHttpClient mAsyncHttpClient = AsyncHttpClient.getDefaultInstance();
        mAsyncHttpClient.websocket(connection, "http", mWebSocketConnectCallback); // da al socket i parametri di connessione al socket in ascolto su bluemix
    }
}




 /*   public void refresh() {
        AsyncHttpClient.WebSocketConnectCallback mWebSocketConnectCallback = new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, final WebSocket webSocket) {
                if (ex != null) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error Refresh",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    ex.printStackTrace();
                    return;
                }
                webSocket.send("refresh");

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(final String s) {

                        //Log.d("CLIENTTAG", s);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                TextView textView = (TextView) findViewById(R.id.ValoreSensore);
                                textView.setText(s);
                            }
                        });
                        webSocket.close();

                    }
                });

            }

        };
        AsyncHttpClient mAsyncHttpClient = AsyncHttpClient.getDefaultInstance();
        mAsyncHttpClient.websocket("wss://iot-jiin995.mybluemix.net/ws/data", "http", mWebSocketConnectCallback);
    }
   public void refreshtemp() {
        AsyncHttpClient.WebSocketConnectCallback mWebSocketConnectCallback = new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, final WebSocket webSocket) {
                if (ex != null) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error Refresh",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    ex.printStackTrace();
                    return;
                }
                webSocket.send("refresh");

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(final String s) {

                        //Log.d("CLIENTTAG", s);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                TextView textView = (TextView) findViewById(R.id.ValoreTemperatura);
                                textView.setText(s);
                            }
                        });
                        webSocket.close();

                    }
                });

            }

        };
        AsyncHttpClient mAsyncHttpClient = AsyncHttpClient.getDefaultInstance();
        mAsyncHttpClient.websocket("wss://iot-jiin995.mybluemix.net/ws/datatemp", "http", mWebSocketConnectCallback);
    }

}*/