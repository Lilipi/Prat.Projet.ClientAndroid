package prat.xml.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import classes.manager.CVManager;
import classes.model.CV;
import classes.model.Degree;
import classes.model.Experience;
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

            //Récupération de la listview créée dans le fichier main.xml
            LinearLayout exp = (LinearLayout) findViewById(R.id.listExperience);

            /*Faire pareil pour experience, compétence et langue + créer les layouts */

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
}
