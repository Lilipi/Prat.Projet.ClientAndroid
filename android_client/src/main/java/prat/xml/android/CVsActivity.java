package prat.xml.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import classes.manager.CVManager;
import classes.model.CV;
import com.sun.javaws.progress.Progress;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Emilie on 22/04/14.
 */
public class CVsActivity  extends Activity {

    ProgressDialog progress;
    private ListView list;

    private String[] val;

    // The connection URL
    private final String url = "http://cv.lilipi.cloudbees.net/cv";

    // Create a new RestTemplate instance
    private RestTemplate restTemplate;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.cvs_activity);
        list = (ListView) findViewById(R.id.listView);
        new Connection().execute();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(getApplicationContext(), " " + position, Toast.LENGTH_SHORT).show();
                // On créé une activity
                Intent i = new Intent(CVsActivity.this, CVActivity.class);

                // On passe des arguments
                Bundle bundle = new Bundle();
                bundle.putInt("id", position);
                i.putExtras(bundle);

                // On la démarre
                startActivity(i);
            }
        });
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CVsActivity.this, android.R.layout.simple_list_item_1, val);
            list.setAdapter(adapter);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            //Toast.makeText(getApplicationContext(), "Chargement terminé", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(CVsActivity.this);
            progress.setMessage("Chargement en cours...");
            progress.show();
            //Toast.makeText(getApplicationContext(), "Début du téléchargement", Toast.LENGTH_SHORT).show();
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

        CVManager CVManager = restTemplate.getForObject(url, CVManager.class);
        List<CV> CVs = CVManager.getResume();

        val = new String[CVs.size()];
        for (int i = 0; i < val.length; i++) {
            CV cv = CVs.get(i);
            val[i] = cv.getNom() + " " + cv.getPrenom();
        }

    }
}
