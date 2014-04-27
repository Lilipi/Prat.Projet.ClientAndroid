package prat.xml.android;

import android.app.Activity;
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
         /*   ArrayAdapter<String> adapter = new ArrayAdapter<String>(CVsActivity.this, android.R.layout.simple_list_item_1, val);
            list.setAdapter(adapter);*/
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(cv.getNom());

            TextView prenom = (TextView) findViewById(R.id.prenom);
            prenom.setText(cv.getPrenom());

            //Récupération de la listview créée dans le fichier main.xml
            LinearLayout degrees = (LinearLayout) findViewById(R.id.scroll);

            //Création de la ArrayList qui nous permettra de remplire la listView
            ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

            //On déclare la HashMap qui contiendra les informations pour un item
            HashMap<String, String> map;

            for (Degree d : cv.getDegrees().getDegree()) {
                //Création d'une HashMap pour insérer les informations du premier item de notre listView
                map = new HashMap<String, String>();
                //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
                map.put("titre", d.getTitle());
                //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
                map.put("description", d.getDescription());
                map.put("beginYear", ""+ d.getBeginYear());
                map.put("endYear", ""+d.getEndYear());
                map.put("location", d.getLocation());
                map.put("school", d.getSchool());
                map.put("mention",d.getMention().toString());

                //enfin on ajoute cette hashMap dans la arrayList
                listItem.add(map);

            }


            //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
            SimpleAdapter mSchedule = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_diplome,
                    new String[] {"titre", "beginYear", "endYear","location", "school", "mention", "description"}, new int[] {R.id.titre, R.id.debut, R.id.fin, R.id.location, R.id.school, R.id.mention, R.id.description});

            //On attribut à notre listView l'adapter que l'on vient de créer
            //degrees.setAdapter(mSchedule);
            for (int i = 0; i < mSchedule.getCount(); i++) {
                View item = mSchedule.getView(i, null, null);
                degrees.addView(item);
             }

            listItem = new ArrayList<HashMap<String, String>>();

            //Récupération de la listview créée dans le fichier main.xml
            LinearLayout exp = (LinearLayout) findViewById(R.id.listExperience);

            for (Experience e : cv.getExperiences().getExperience()) {
                //Création d'une HashMap pour insérer les informations du premier item de notre listView
                map = new HashMap<String, String>();
                //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
                map.put("titre", e.getTitle());
                //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
                map.put("description", e.getDescription());
                String res = "";
                if (e.getBeginMonth() != null) {
                    res += e.getBeginMonth() + " ";
                }
                res += e.getBeginYear() + " à ";
                if (e.getEndMonth() != null) {
                    res += e.getEndMonth() + " ";
                }
                res += e.getEndYear();

                map.put("duree", res);

                map.put("entreprise", e.getCompany() + ", " + e.getLocation());

                //enfin on ajoute cette hashMap dans la arrayList
                listItem.add(map);

            }


            //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
            SimpleAdapter adapter = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_experience,
                    new String[] {"titre", "duree", "entreprise", "description"}, new int[] {R.id.titre, R.id.duree, R.id.entreprise, R.id.description});

            //On attribut à notre listView l'adapter que l'on vient de créer
            for (int i = 0; i < adapter.getCount(); i++) {
                View item = adapter.getView(i, null, null);
                exp.addView(item);
            }


            listItem = new ArrayList<HashMap<String, String>>();

            //Récupération de la listview créée dans le fichier main.xml
            LinearLayout skills = (LinearLayout) findViewById(R.id.listCompetence);

            for (Skill s : cv.getSkills().getSkill()) {
                //Création d'une HashMap pour insérer les informations du premier item de notre listView
                map = new HashMap<String, String>();
                //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
                map.put("nom_competence", s.getName());
                map.put("level", " " + s.getLevel());

                //enfin on ajoute cette hashMap dans la arrayList
                listItem.add(map);

            }



            //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
            SimpleAdapter skilladapter = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_competence,
                    new String[] {"nom_competence", "level"}, new int[] {R.id.nom_competence, R.id.ratingbar});
            //On attribut à notre listView l'adapter que l'on vient de créer
            //degrees.setAdapter(mSchedule);

            skilladapter.setViewBinder(new MyBinderSkill());

            //On attribut à notre listView l'adapter que l'on vient de créer
            for (int i = 0; i < skilladapter.getCount(); i++) {
                View item = skilladapter.getView(i, null, null);
                skills.addView(item);
            }

            listItem = new ArrayList<HashMap<String, String>>();

            //Récupération de la listview créée dans le fichier main.xml
            LinearLayout languages = (LinearLayout) findViewById(R.id.listLangue);

            for (Language l : cv.getLanguages().getLanguage()) {
                //Création d'une HashMap pour insérer les informations du premier item de notre listView
                map = new HashMap<String, String>();
                //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
                map.put("nom_competence", l.getName());
                map.put("level", " " + l.getLevel());

                //enfin on ajoute cette hashMap dans la arrayList
                listItem.add(map);

            }



            //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
            SimpleAdapter languageadapter = new SimpleAdapter (getBaseContext(), listItem, R.layout.affichage_competence,
                    new String[] {"nom_competence", "level"}, new int[] {R.id.nom_competence, R.id.ratingbar});
            //On attribut à notre listView l'adapter que l'on vient de créer
            //degrees.setAdapter(mSchedule);

            languageadapter.setViewBinder(new MyBinderLanguage());

            //On attribut à notre listView l'adapter que l'on vient de créer
            for (int i = 0; i < languageadapter.getCount(); i++) {
                View item = languageadapter.getView(i, null, null);
                languages.addView(item);
            }

           /* for (int i = 0; i < skilladapter.getCount(); i++) {
                View item = skilladapter.getView(i, null, null);
                skills.addView(item);
            }*/
            /*Faire pareil pour compétence et langue + créer les layouts */

            /*String val = new String[cv.getDegrees().];
            for (int i = 0; i < val.length; i++) {
                CV cv = CVs.get(i);
                val[i] = cv.getNom() + " " + cv.getPrenom();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CVActivity.this, R.id.listformation, val);
            list.setAdapter(adapter);*/



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
