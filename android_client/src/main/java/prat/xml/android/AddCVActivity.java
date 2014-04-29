package prat.xml.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import classes.manager.DegreeManager;
import classes.manager.ExperienceManager;
import classes.manager.LanguageManager;
import classes.manager.SkillManager;
import classes.model.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
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
    private String url = "http://cv.lilipi.cloudbees.net/cv/put";

    private LinearLayout mLayout;
    private Button mButton;
    private int cptDiplome = 0;

    // Create a new RestTemplate instance
    private RestTemplate restTemplate;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_cv);
        restTemplate = new RestTemplate();
        // Add the Simple XML message converter
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());


        mLayout = (LinearLayout) findViewById(R.id.addDiplome);
        mButton = (Button) findViewById(R.id.add2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLayout.addView(createNewTextView("Diplôme", cptDiplome));
                mLayout.addView(createNewEditText("Titre", cptDiplome));
                mLayout.addView(createNewEditText("Début", cptDiplome));
                mLayout.addView(createNewEditText("Fin", cptDiplome));
                mLayout.addView(createNewEditText("Mention(Optionnelle)", cptDiplome));
                mLayout.addView(createNewEditText("Lieu", cptDiplome));
                mLayout.addView(createNewEditText("Ecole", cptDiplome));
                mLayout.addView(createNewEditText("Description", cptDiplome));
                cptDiplome++;

            }
        });

        Button creer = (Button) findViewById(R.id.creer);
        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ((EditText)findViewById(R.id.nom)).getText().toString();
                String prenom = ((EditText)findViewById(R.id.prenom)).getText().toString();
                ExperienceManager exp = new ExperienceManager();
                DegreeManager deg = new DegreeManager();
                SkillManager skill = new SkillManager();
                LanguageManager lg = new LanguageManager();

                try {
                    Log.i("TEST", url);
                    cv = new CV();
                    restTemplate.put(url, cv);//, CV.class);

                } catch (Exception e) {
                    Log.i("TEST", e.getMessage());
                }

            }
        });

     /*   Bundle bundle2 = getIntent().getExtras();
        int id = bundle2.getInt("id");

        url += "/" + id;*/

//        new Connection().execute();

    }

    private EditText createNewEditText(String text, int i) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText textView = new EditText(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        textView.setId(i);
        if (text == "Description") {
            textView.setSingleLine(false);
        } else {
            textView.setSingleLine(true);
        }
        return textView;
    }

    private TextView createNewTextView(String text, int i) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        textView.setId(i);

        return textView;
    }

  /*  private class Connection extends AsyncTask {

        private Connection() {
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            //Toast.makeText(getApplicationContext(), "Chargement terminé", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Toast.makeText(getApplicationContext(), "Début du téléchargement", Toast.LENGTH_SHORT).show();
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

    }*/

}
