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
import java.util.List;

/**
 * Created by Emilie on 27/04/14.
 */
public class AddCVActivity extends Activity {


    private CV cv;

    // The connection URL
    private String url = "http://cv.lilipi.cloudbees.net/cv/put";

    private LinearLayout mLayout;
    private ImageButton mButton;
    private Spinner mention;
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
        mButton = (ImageButton) findViewById(R.id.add2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout diplome = (LinearLayout)findViewById(R.id.addDiplome);
                final View view = View.inflate(AddCVActivity.this, R.layout.diplome, null);
                ImageButton supp = (ImageButton) view.findViewById(R.id.suppr);
                supp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diplome.removeView(view);
                    }

                });
                diplome.addView(view);
                addMentionItemsOnSpinner(view);

             /*   mLayout.addView(createNewTextView("Diplôme", cptDiplome));
                mLayout.addView(createNewEditText("Titre", cptDiplome));
                mLayout.addView(createNewEditText("Début", cptDiplome));
                mLayout.addView(createNewEditText("Fin", cptDiplome));
                mLayout.addView(createNewEditText("Mention(Optionnelle)", cptDiplome));
                mLayout.addView(createNewEditText("Lieu", cptDiplome));
                mLayout.addView(createNewEditText("Ecole", cptDiplome));
                mLayout.addView(createNewEditText("Description", cptDiplome));
                cptDiplome++;*/

            }
        });

        mLayout = (LinearLayout) findViewById(R.id.addSkill);
        mButton = (ImageButton) findViewById(R.id.addComp);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout skills = (LinearLayout)findViewById(R.id.addSkill);
                final View view = View.inflate(AddCVActivity.this, R.layout.skill, null);
                ImageButton supp = (ImageButton) view.findViewById(R.id.suppr);
                supp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skills.removeView(view);
                    }

                });
                skills.addView(view);

            }
        });

        mLayout = (LinearLayout) findViewById(R.id.addLangue);
        mButton = (ImageButton) findViewById(R.id.langueAdd);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout langue = (LinearLayout)findViewById(R.id.addLangue);
                final View view = View.inflate(AddCVActivity.this, R.layout.skill, null);
                ImageButton supp = (ImageButton) view.findViewById(R.id.suppr);
                supp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        langue.removeView(view);
                    }

                });
                langue.addView(view);
            }
        });

        mLayout = (LinearLayout) findViewById(R.id.addExp);
        mButton = (ImageButton) findViewById(R.id.imageButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout exp = (LinearLayout)findViewById(R.id.addExp);
                final View view = View.inflate(AddCVActivity.this, R.layout.experience, null);
                ImageButton supp = (ImageButton) view.findViewById(R.id.deleteButton);
                supp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exp.removeView(view);
                    }

                });
                exp.addView(view);
                addBeginMonthItemsOnSpinner(view);
                addEndMonthItemsOnSpinner(view);
            }
        });


        Button creer = (Button) findViewById(R.id.creer);
        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ((EditText)findViewById(R.id.nom)).getText().toString();
                String prenom = ((EditText)findViewById(R.id.prenom)).getText().toString();


                LinearLayout diplomes = (LinearLayout) findViewById(R.id.addDiplome);
                int childCount = diplomes.getChildCount();

                List<Degree> degList = new ArrayList<Degree>();
                for (int i=0; i < childCount; i++){
                    View view = diplomes.getChildAt(i);
                    String titre = ((EditText)view.findViewById(R.id.titre)).getText().toString();
                    int debut = Integer.parseInt(((EditText) view.findViewById(R.id.debut)).getText().toString());
                    int fin;
                    if (((EditText)view.findViewById(R.id.fin)).getText() != null && !(((EditText)view.findViewById(R.id.fin)).getText().toString().length() == 0) ) {
                        fin = Integer.parseInt(((EditText)view.findViewById(R.id.fin)).getText().toString());
                    } else {
                        fin = 0;
                    }
                    Mention mention = Mention.values()[(((Spinner)view.findViewById(R.id.spinner2)).getSelectedItemPosition())];
                    String lieu = ((EditText)view.findViewById(R.id.lieu)).getText().toString();
                    String ecole = ((EditText)view.findViewById(R.id.ecole)).getText().toString();
                    String description = ((EditText)view.findViewById(R.id.description)).getText().toString();
                    //Log.i("TEST", "t : "+ titre + " d " + debut + " f " + fin + " m " + mention + " l "+ lieu + " e" + ecole + " d "+ description);
                    Degree d = new Degree(titre, debut, fin, mention, lieu, ecole, description);
                    degList.add(d);
                }
                DegreeManager deg = new DegreeManager(degList);

                LinearLayout experiences = (LinearLayout) findViewById(R.id.addExp);
                int count = experiences.getChildCount();

                List<Experience> expList = new ArrayList<Experience>();
                for (int i=0; i < count; i++){
                    View view = experiences.getChildAt(i);
                    String titre = ((EditText)view.findViewById(R.id.titre)).getText().toString();
                    int debut = Integer.parseInt(((EditText) view.findViewById(R.id.debutAnnee)).getText().toString());
                    int fin;
                    if (((EditText)view.findViewById(R.id.finAnnee)).getText() != null && !(((EditText)view.findViewById(R.id.finAnnee)).getText().toString().length() == 0) ) {
                        fin = Integer.parseInt(((EditText)view.findViewById(R.id.fin)).getText().toString());
                    } else {
                        fin = 0;
                    }
                    String debutM = ((Spinner)view.findViewById(R.id.spinnerDebut)).getSelectedItem().toString();
                    String finM = ((Spinner)view.findViewById(R.id.spinnerFin)).getSelectedItem().toString();
                    String lieu = ((EditText)view.findViewById(R.id.lieu)).getText().toString();
                    String entreprise = ((EditText)view.findViewById(R.id.entreprise)).getText().toString();
                    String description = ((EditText)view.findViewById(R.id.description)).getText().toString();
                    //Log.i("TEST", "t : "+ titre + " d " + debut + " f " + fin + " m " + mention + " l "+ lieu + " e" + ecole + " d "+ description);
                    Experience exp = new Experience(titre, debutM, debut, finM, fin, lieu, entreprise, description);
                    expList.add(exp);

                    }

                ExperienceManager exp = new ExperienceManager(expList);

                SkillManager skill = new SkillManager();
                LanguageManager lg = new LanguageManager();

              /*  try {
                    Log.i("TEST", url);
                    cv = new CV();
                    restTemplate.put(url, cv);//, CV.class);

                } catch (Exception e) {
                    Log.i("TEST", e.getMessage());
                }*/

            }
        });

     /*   Bundle bundle2 = getIntent().getExtras();
        int id = bundle2.getInt("id");

        url += "/" + id;*/

//        new Connection().execute();

    }

    private void addBeginMonthItemsOnSpinner(View view) {
        Spinner debut = (Spinner) view.findViewById(R.id.spinnerDebut);
        List<String> list = new ArrayList<String>();
        String [] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
        "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        debut.setAdapter(dataAdapter);
    }

    private void addEndMonthItemsOnSpinner(View view) {
        Spinner fin = (Spinner) view.findViewById(R.id.spinnerFin);
        List<String> list = new ArrayList<String>();
        String [] months = {"En cours", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fin.setAdapter(dataAdapter);
    }
/*
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
    }*/

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

    public void addMentionItemsOnSpinner(View view) {

        mention = (Spinner) view.findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        for (Mention m : Mention.values()) {
            list.add(m.toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mention.setAdapter(dataAdapter);
    }

    // get the selected dropdown list value
  /*  public void addListenerOnButton() {

         = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MyAndroidAppActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }*/
}
