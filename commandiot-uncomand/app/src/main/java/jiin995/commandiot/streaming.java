package jiin995.commandiot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
//import android.webkit.WebSettings;
import android.webkit.WebView;

public class streaming extends Activity
{
    private static final int GO_TO_MAIN = Menu.FIRST;
    private static final int GO_TO_SENSDIST = GO_TO_MAIN + 1;
    private static final int GO_TO_SENS = GO_TO_MAIN + 2;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.streaming);
        WebView streaming = (WebView) findViewById(R.id.webview); // correla l' activity all' oggetto layout dello streaming


       /* WebSettings streamingsettings = streaming.getSettings();
       streamingsettings.setJavaScriptEnabled(true);
        streamingsettings.setJavaScriptCanOpenWindowsAutomatically(true);
        streamingsettings.setSupportZoom(true);*/

        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        // codice html dello streaming

        String html ="<html>";
        html=html+"<img id=\"webframe\" style=\"-webkit-user-select: none\" " ;
        html=html+"alt=\"Errore:Il dottor criceto è al lavoro per trovare una soluzione,probabilmente ha disinserito la ruota!\" ";
        html=html+"src=\"http://143.225.229.12:8080/?action=stream\">";
        html=html+"</html>";

        streaming.loadData(html, mimeType, encoding); // carica lo streaming
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, GO_TO_MAIN , 0 , "Main" );
        menu.add(0, GO_TO_SENSDIST, 0, "Sensdist");
        menu.add(0, GO_TO_SENS, 0, "Sens");
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
            case GO_TO_SENS:
            {
                startActivity(new Intent(getApplicationContext(), sens.class));
                finish();
            }
                break;
            case GO_TO_SENSDIST:
            {
                startActivity(new Intent(getApplicationContext(), sensoridist.class));
                finish();
            }
            break;
        }
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private float posi,posf;
    private int MIN_DISTANCE=50;
    @Override
    public boolean onTouchEvent(MotionEvent swipe)
    {
        switch (swipe.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                posi = swipe.getX();
                break;
            case MotionEvent.ACTION_UP:
                posf = swipe.getX();
                float deltax = posf - posi;
                if ((Math.abs(deltax) > MIN_DISTANCE) && (deltax > 0))
                {
                    startActivity(new Intent(getApplicationContext(), sensoridist.class));
                    finish();
                }
                break;
        }
        return super.onTouchEvent(swipe);
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),sens.class));
        finish();
        return ;
    }
}
