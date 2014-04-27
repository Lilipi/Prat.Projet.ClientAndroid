package prat.xml.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import classes.model.*;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Emilie on 27/04/14.
 */
public class AddCVActivity extends Activity {


    private CV cv;

    // The connection URL
    private String url = "http://cv.lilipi.cloudbees.net/cv";

    // Create a new RestTemplate instance
    private RestTemplate restTemplate;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_cv);

     /*   Bundle bundle2 = getIntent().getExtras();
        int id = bundle2.getInt("id");

        url += "/" + id;*/

        new Connection().execute();

    }

    private class Connection extends AsyncTask {

        private Connection() {
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            Toast.makeText(getApplicationContext(), "Chargement terminé", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Début du téléchargement", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Object... values){
            super.onProgressUpdate(values);
        }

    }

    private void connect() {

        restTemplate = new RestTemplate();
        // Add the Simple XML message converter
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

       // cv = restTemplate.getForObject(url, CV.class);

    }

}
