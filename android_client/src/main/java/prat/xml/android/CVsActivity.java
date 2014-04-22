package prat.xml.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import classes.manager.CVManager;
import classes.model.CV;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Emilie on 22/04/14.
 */
public class CVsActivity  extends Activity {

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
                Toast.makeText(getApplicationContext(), " " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CVsActivity.this, android.R.layout.simple_list_item_1, val);
            list.setAdapter(adapter);
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
