package prat.xml.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import classes.manager.CVManager;
import classes.model.*;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Emilie on 25/04/14.
 */
public class CVActivity extends Activity {

    private CV cv;
    ProgressDialog progress;

    // The connection URL
    private String url = "http://cv.lilipi.cloudbees.net/cv";

    // Create a new RestTemplate instance
    private RestTemplate restTemplate;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.get_cv);

        Bundle bundle2 = getIntent().getExtras();
        int id = bundle2.getInt("id");

        url += "/" + id;

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

            TextView name = (TextView) findViewById(R.id.name);
            name.setText(cv.getNom());

            TextView prenom = (TextView) findViewById(R.id.prenom);
            prenom.setText(cv.getPrenom());

            LinearLayout degrees = (LinearLayout) findViewById(R.id.scroll);

            ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

            HashMap<String, String> map;

            for (Degree d : cv.getDegrees().getDegree()) {

                map = new HashMap<String, String>();

                map.put("titre", d.getTitle());

                if (d.getDescription() != null && d.getDescription().length() != 0) {
                    map.put("description", d.getDescription());
                }
                map.put("beginYear", ""+ d.getBeginYear());
                if (d.getEndYear() == 0) {
                    map.put("endYear", "En cours");
                } else {
                    map.put("endYear", ""+d.getEndYear());
                    map.put("mention",d.getMention().toString());
                }
                map.put("location", d.getLocation());
                map.put("school", d.getSchool());

                listItem.add(map);

            }



            SimpleAdapter mSchedule = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_diplome,
                    new String[] {"titre", "beginYear", "endYear","location", "school", "mention", "description"}, new int[] {R.id.titre, R.id.debut, R.id.fin, R.id.location, R.id.school, R.id.mention, R.id.description});

            for (int i = 0; i < mSchedule.getCount(); i++) {
                View item = mSchedule.getView(i, null, null);
                degrees.addView(item);
             }

            listItem = new ArrayList<HashMap<String, String>>();

            LinearLayout exp = (LinearLayout) findViewById(R.id.listExperience);

            for (Experience e : cv.getExperiences().getExperience()) {
                map = new HashMap<String, String>();
                map.put("titre", e.getTitle());

                if (e.getDescription() != null && e.getDescription().length() != 0) {
                    map.put("description", e.getDescription());
                }
                String res = "";
                res += e.getBeginMonth() + " ";
                res += e.getBeginYear() + " à ";

                res += e.getEndMonth() + " ";
                if (e.getEndYear() != 0) {
                    res += e.getEndYear();
                }

                map.put("duree", res);

                map.put("entreprise", e.getCompany() + ", " + e.getLocation());

                listItem.add(map);

            }


            SimpleAdapter adapter = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_experience,
                    new String[] {"titre", "duree", "entreprise", "description"}, new int[] {R.id.titre, R.id.duree, R.id.entreprise, R.id.description});

            for (int i = 0; i < adapter.getCount(); i++) {
                View item = adapter.getView(i, null, null);
                exp.addView(item);
            }


            listItem = new ArrayList<HashMap<String, String>>();


            LinearLayout skills = (LinearLayout) findViewById(R.id.listCompetence);

            for (Skill s : cv.getSkills().getSkill()) {

                map = new HashMap<String, String>();

                map.put("nom_competence", s.getName());
                map.put("level", " " + s.getLevel());

                listItem.add(map);

            }



            SimpleAdapter skilladapter = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_competence,
                    new String[] {"nom_competence", "level"}, new int[] {R.id.nom_competence, R.id.ratingbar});

            skilladapter.setViewBinder(new MyBinderSkill());

            for (int i = 0; i < skilladapter.getCount(); i++) {
                View item = skilladapter.getView(i, null, null);
                skills.addView(item);
            }

            listItem = new ArrayList<HashMap<String, String>>();

            LinearLayout languages = (LinearLayout) findViewById(R.id.listLangue);

            for (Language l : cv.getLanguages().getLanguage()) {
                map = new HashMap<String, String>();

                map.put("nom_competence", l.getName());
                map.put("level", " " + l.getLevel());


                listItem.add(map);

            }

            SimpleAdapter languageadapter = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_competence,
                    new String[] {"nom_competence", "level"}, new int[] {R.id.nom_competence, R.id.ratingbar});


            languageadapter.setViewBinder(new MyBinderLanguage());

            for (int i = 0; i < languageadapter.getCount(); i++) {
                View item = languageadapter.getView(i, null, null);
                languages.addView(item);
            }

            if (progress.isShowing()) {
                progress.dismiss();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(CVActivity.this);
            progress.setMessage("Chargement en cours...");
            progress.show();

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

        cv = restTemplate.getForObject(url, CV.class);

    }

    class MyBinderSkill implements SimpleAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if(view.getId() == R.id.ratingbar){
                String stringval = (String) data;
                float value =  Float.parseFloat(stringval);
                RatingBar ratingBar = (RatingBar) view;
                float ratingValue = value / (float)Skill.MAX_LEVEL * (float)ratingBar.getNumStars();
                ratingBar.setRating(ratingValue);
                return true;
            }
            return false;
        }
    }


    class MyBinderLanguage implements SimpleAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if(view.getId() == R.id.ratingbar){
                String stringval = (String) data;
                float value =  Float.parseFloat(stringval);
                RatingBar ratingBar = (RatingBar) view;
                float ratingValue = value / (float)Language.MAX_LEVEL * (float)ratingBar.getNumStars();
                ratingBar.setRating(ratingValue);
                return true;
            }
            return false;
        }
    }
}
