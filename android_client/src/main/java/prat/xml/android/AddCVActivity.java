package prat.xml.android;

import android.app.Activity;
import android.content.Intent;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by Emilie on 27/04/14.
 */
public class AddCVActivity extends Activity {


    private CV cv;

    private int annee;
    private int mois;
    private String msg ="";

    private final static int MIN_ANNEE = 1900;

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

        final Calendar c = Calendar.getInstance();
        annee = c.get(Calendar.YEAR);
        mois = c.get(Calendar.MONTH);
        mois++;

        setContentView(R.layout.add_cv);
        restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
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

                msg ="";
                boolean error = false;

                String nom = ((EditText)findViewById(R.id.nom)).getText().toString();

            /*    if (nom == null || nom.length() == 0) {
                    error = true;
                    msg += "Le champs Nom est obligatoire.\n";
                }*/

                String prenom = ((EditText)findViewById(R.id.prenom)).getText().toString();

               /* if (prenom == null || prenom.length() == 0) {
                    error = true;
                    msg += "Le champs Prénom est obligatoire.\n";
                }*/

                LinearLayout diplomes = (LinearLayout) findViewById(R.id.addDiplome);
                int childCount = diplomes.getChildCount();

                List<Degree> degList = new ArrayList<Degree>();
                for (int i=0; i < childCount; i++){
                    View view = diplomes.getChildAt(i);

                    String titre = ((EditText)view.findViewById(R.id.titre)).getText().toString();

                   /* if (titre == null || titre.length() == 0) {
                        error = true;
                        msg += "Le champs Titre du diplôme " + (i+1) + " est obligatoire.\n";
                    }*/

                    int fin;

                    if (((EditText)view.findViewById(R.id.fin)).getText() != null && !(((EditText)view.findViewById(R.id.fin)).getText().toString().length() == 0) ) {
                        fin = Integer.parseInt(((EditText)view.findViewById(R.id.fin)).getText().toString());
                      /*  if (fin > annee) {
                            error = true;
                            msg += "La fin du diplôme " + (i + 1) + " doit être antérieur à la date du jour ou vide si en cours. \n";
                        }
                        if (fin < MIN_ANNEE) {
                            error = true;
                            msg += "La fin du diplôme " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + " \n";

                        }*/

                    } else {
                        //Diplôme en cours
                        fin = 0;
                    }

                    int debut = 0;
                    if (((EditText) view.findViewById(R.id.debut)).getText().toString() != null && !(((EditText) view.findViewById(R.id.debut)).getText().toString().length() == 0)) {
                        debut = Integer.parseInt(((EditText) view.findViewById(R.id.debut)).getText().toString());

                      /* if (debut > annee) {
                            error = true;
                            msg += "Le Début du diplôme " + (i + 1) + " doit être antérieur à la date du jour. \n";
                       }
                       if (debut > fin && fin != 0) {
                            error = true;
                            msg += "Le Début du diplôme " + (i + 1) + " doit être antérieur à sa fin. \n";
                       }

                       if (debut < MIN_ANNEE) {
                            error = true;
                            msg += "Le début du diplôme " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + " \n";

                       }*/

                    } else {
                        error = true;
                        msg += "Le champs Début du diplôme " + (i + 1) + " est obligatoire. \n";
                    }

                    Mention mention = Mention.values()[(((Spinner)view.findViewById(R.id.spinner2)).getSelectedItemPosition())];

                    String lieu = ((EditText)view.findViewById(R.id.lieu)).getText().toString();

                  /*  if (lieu == null || lieu.length() == 0) {
                        error = true;
                        msg += "Le champs Lieu du diplôme " + (i+1) + " est obligatoire.\n";
                    }*/

                    String ecole = ((EditText)view.findViewById(R.id.ecole)).getText().toString();

                    /*if (ecole == null || ecole.length() == 0) {
                        error = true;
                        msg += "Le champs Ecole du diplôme " + (i+1) + " est obligatoire.\n";
                    }*/

                    String description = ((EditText)view.findViewById(R.id.description)).getText().toString();

                    Degree d = new Degree(titre, debut, fin, mention, lieu, ecole, description);
                    degList.add(d);
                }
                DegreeManager deg = new DegreeManager(degList);

               /* Map<String, Integer> months = new HashMap<String, Integer>();
                months.put("En cours",0);
                months.put("Janvier", 1);
                months.put("Février", 2);
                months.put("Mars", 3);
                months.put("Avril", 4);
                months.put("Mai", 5);
                months.put("Juin", 6);
                months.put("Juillet", 7);
                months.put("Août", 8);
                months.put("Septembre", 9);
                months.put("Octobre", 10);
                months.put("Novembre", 11);
                months.put("Décembre", 12);*/

                LinearLayout experiences = (LinearLayout) findViewById(R.id.addExp);
                int count = experiences.getChildCount();

                List<Experience> expList = new ArrayList<Experience>();
                for (int i=0; i < count; i++){
                    View view = experiences.getChildAt(i);

                    String titre = ((EditText)view.findViewById(R.id.titre)).getText().toString();

                   /* if (titre == null || titre.length() == 0) {
                        error = true;
                        msg += "Le champs Titre de l'expérience " + (i+1) + " est obligatoire.\n";
                    }*/

                    String debutM = ((Spinner)view.findViewById(R.id.spinnerDebut)).getSelectedItem().toString();

                    String finM = ((Spinner)view.findViewById(R.id.spinnerFin)).getSelectedItem().toString();

                    int fin;
                    if (((EditText)view.findViewById(R.id.finAnnee)).getText().toString() != null && !(((EditText)view.findViewById(R.id.finAnnee)).getText().toString().length() == 0) ) {
                        fin = Integer.parseInt(((EditText)view.findViewById(R.id.finAnnee)).getText().toString());
                       /* if (fin > annee) {
                            error = true;
                            msg += "La fin de l'expérience " + (i + 1) + " doit être antérieur à la date du jour ou vide si en cours. \n";
                        }
                        if (fin < MIN_ANNEE) {
                            error = true;
                            msg += "La fin de l'expérience " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + " \n";

                        }*/
                    } else {
                        fin = 0;
                    }

                    int debut = 0;
                    if (((EditText) view.findViewById(R.id.debutAnnee)).getText().toString() != null && !(((EditText) view.findViewById(R.id.debutAnnee)).getText().toString().length() == 0)) {
                        debut = Integer.parseInt(((EditText) view.findViewById(R.id.debutAnnee)).getText().toString());

                      /*  if (debut > fin && fin != 0) {
                            error = true;
                            msg += "Le Début de l'expérience " + (i + 1) + " doit être antérieur à sa date de fin. \n";
                        }
                        if (debut == fin && fin != 0) {
                            if (months.get(debutM) > months.get(finM) && months.get(finM) != 0) {
                                error = true;
                                msg += "Le Début de l'expérience " + (i + 1) + " doit être antérieur à sa date de fin. \n";
                            }
                        }
                        if (debut  > annee) {
                            error = true;
                            msg += "Le début du diplôme " + (i + 1) + " doit être antérieur à la date du jour. \n";
                        }
                        if (debut == annee && months.get(debutM) > mois) {
                            error = true;
                            msg += "Le Début de l'expérience " + (i + 1) + " doit être antérieur à la date du jour. \n";
                        }
                        if (fin == annee && months.get(finM) > mois) {
                            error = true;
                            msg += "La fin de l'expérience " + (i + 1) + " doit être antérieur à la date du jour ou vide si en cours. \n";
                        }

                        if (debut < MIN_ANNEE) {
                            error = true;
                            msg += "Le début de l'expérience " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + " \n";

                        }*/

                    } else {
                        error = true;
                        msg += "Le champs Début de l'expérience " + (i + 1) + " est obligatoire. \n";
                    }


                    String lieu = ((EditText)view.findViewById(R.id.lieu)).getText().toString();

                  /*  if (lieu == null || lieu.length() == 0) {
                        error = true;
                        msg += "Le champs Lieu de l'expérience " + (i+1) + " est obligatoire.\n";
                    }*/

                    String entreprise = ((EditText)view.findViewById(R.id.entreprise)).getText().toString();

                   /* if (entreprise == null || entreprise.length() == 0) {
                        error = true;
                        msg += "Le champs Entreprise de l'expérience " + (i+1) + " est obligatoire.\n";
                    }*/

                    String description = ((EditText)view.findViewById(R.id.description)).getText().toString();

                    Experience exp = new Experience(titre, debutM, debut, finM, fin, lieu, entreprise, description);

                    expList.add(exp);

                }

                ExperienceManager exp = new ExperienceManager(expList);

                LinearLayout skills = (LinearLayout) findViewById(R.id.addSkill);
                int c = skills.getChildCount();

                List<Skill> skillList = new ArrayList<Skill>();
                for (int i=0; i < c; i++){
                    View view = skills.getChildAt(i);

                    String titre = ((EditText)view.findViewById(R.id.nom)).getText().toString();

                  /*  if (titre == null || titre.length() == 0) {
                        error = true;
                        msg += "Le champs Titre de la compétence " + (i+1) + " est obligatoire.\n";
                    }*/

                    float level = ((RatingBar)view.findViewById(R.id.ratingBar)).getRating();

                    Skill s = new Skill(titre, level);
                    skillList.add(s);

                }

                SkillManager skill = new SkillManager(skillList);

                LinearLayout languages = (LinearLayout) findViewById(R.id.addLangue);
                int cpt = languages.getChildCount();

                List<Language> langList = new ArrayList<Language>();
                for (int i=0; i < cpt; i++){
                    View view = languages.getChildAt(i);

                    String titre = ((EditText)view.findViewById(R.id.nom)).getText().toString();

                   /* if (titre == null || titre.length() == 0) {
                        error = true;
                        msg += "Le champs Titre de la langue " + (i+1) + " est obligatoire.\n";
                    }*/

                    float level = ((RatingBar)view.findViewById(R.id.ratingBar)).getRating();

                    Language l = new Language(titre, level);
                    langList.add(l);

                }

                LanguageManager lg = new LanguageManager(langList);

                cv = new CV(nom, prenom, exp, skill, deg, lg);

                boolean error2 = doValidation(cv);
                error = (error || error2);

                if (error == false) {

                    // Make the HTTP PUT request, marshaling the request to XML
                    HttpEntity<CV> requestEntity = new HttpEntity<CV>(cv);
                    try {
                        Log.i("TEST", "try");
                        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
                        Toast.makeText(
                                getApplicationContext(),
                                response.getBody(),
                                Toast.LENGTH_LONG
                        ).show();
                        final LinearLayout boutonLayout = (LinearLayout)findViewById(R.id.boutonLayout);
                        final View view = View.inflate(AddCVActivity.this, R.layout.retour, null);
                        Button add = (Button) findViewById(R.id.creer);
                        add.setClickable(false);
                        add.setEnabled(false);
                        Button retour = (Button) view.findViewById(R.id.retour);
                        retour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(AddCVActivity.this, ActivityMain.class);
                                startActivity(i);
                            }
                        });
                        /*
                        ImageButton supp = (ImageButton) view.findViewById(R.id.suppr);
                        supp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                diplome.removeView(view);
                            }

                        });*/
                        boutonLayout.addView(view);
                    } catch (Exception e) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Erreur lors de l'ajout du CV",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            msg,
                            Toast.LENGTH_LONG
                    ).show();
                }

            }
        });

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

    private boolean doValidation(CV cv) {
        boolean res = false;

        final Calendar c = Calendar.getInstance();
        int annee = c.get(Calendar.YEAR);
        int mois = c.get(Calendar.MONTH);
        mois++;

        if (cv.getNom() == null || cv.getNom().length() == 0) {
            res = true;
            msg += "Le champ Nom est obligatoire.\n";
        }
        if (cv.getPrenom() == null || cv.getPrenom().length() == 0) {
            res = true;
            msg += "Le champ Prénom est obligatoire.\n";
        }

        DegreeManager degrees = cv.getDegrees();
        for (int i = 0; i < degrees.getDegree().size(); i++) {
            Degree d = degrees.getDegree().get(i);
            if (d.getTitle() == null || d.getTitle().length() == 0) {
                res = true;
                msg += "Le champ Titre du diplôme " + (i + 1) + " est obligatoire.\n";
            }
            if (d.getBeginYear() > d.getEndYear() && d.getEndYear() != 0) {
                res = true;
                msg += "L'année de début du diplôme " + (i + 1) + " doit être antérieure à l'année de fin.\n";
            }
            if (d.getBeginYear() < MIN_ANNEE) {
                res = true;
                msg += "L'année de début du diplôme " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + ".\n";

            }
            if (d.getBeginYear() > annee) {
                res = true;
                msg += "L'année de début du diplôme " + (i + 1) + " doit être antérieure à l'année en cours.\n";
            }
            if (d.getEndYear() < MIN_ANNEE  && d.getEndYear() != 0) {
                res = true;
                msg += "L'année de début du diplôme " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + ".\n";

            }
            if (d.getEndYear() > annee) {
                res = true;
                msg += "L'année de fin du diplôme " + (i + 1) + " doit être antérieure à l'année en cours ou vide.\n";
            }
            if (d.getLocation() == null || d.getLocation().length() == 0) {
                res = true;
                msg += "Le champ Lieu du diplôme " +(i + 1) + " est obligatoire.\n";
            }
            if (d.getSchool() == null || d.getSchool().length() == 0) {
                res = true;
                msg += "Le champ Ecole du diplôme " + (i + 1) + " est obligatoire.\n";
            }
        }

        Map<String, Integer> months = new HashMap<String, Integer>();
        months.put("En cours",0);
        months.put("Janvier", 1);
        months.put("Février", 2);
        months.put("Mars", 3);
        months.put("Avril", 4);
        months.put("Mai", 5);
        months.put("Juin", 6);
        months.put("Juillet", 7);
        months.put("Août", 8);
        months.put("Septembre", 9);
        months.put("Octobre", 10);
        months.put("Novembre", 11);
        months.put("Décembre", 12);

        ExperienceManager experiences = cv.getExperiences();
        for (int i = 0; i < experiences.getExperience().size(); i++) {
            Experience exp = experiences.getExperience().get(i);
            if (exp.getTitle() == null || exp.getTitle().length() == 0) {
                res = true;
                msg += "Le champ Titre de l'expérience " + (i + 1) + " est obligatoire.\n";
            }
            if (exp.getBeginYear() > exp.getEndYear()  && exp.getEndYear() != 0) {
                res = true;
                msg += "L'année de début de l'expérience " + (i + 1) + " doit être antérieure à l'année de fin.\n";
            }
            if (exp.getBeginYear() < MIN_ANNEE) {
                res = true;
                msg += "L'année de début de l'expérience " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + ".\n";

            }
            if (exp.getEndYear() < MIN_ANNEE  && exp.getEndYear() != 0) {
                res = true;
                msg += "L'année de fin de l'expérience " + (i + 1) + " doit être postérieure à " + MIN_ANNEE + ".\n";

            }
            if (exp.getBeginYear() == exp.getEndYear()) {
                if (months.get(exp.getBeginMonth()) > months.get(exp.getEndMonth()) && months.get(exp.getEndMonth()) != 0) {
                    res = true;
                    msg += "Le mois de début de l'expérience " +(i + 1) + " doit être antérieur au mois de fin.\n";
                }
            }
            if (exp.getBeginYear() == annee) {
                if (months.get(exp.getBeginMonth()) > mois) {
                    res = true;
                    msg += "Le mois de début de l'expérience " +(i + 1) + " doit être antérieur à la date du jour.\n";
                }

            }
            if (exp.getEndYear() == annee) {
                if (months.get(exp.getEndMonth()) > mois) {
                    res = true;
                    msg += "Le mois de fin de l'expérience " +(i + 1) + " doit être antérieur à la date du jour.\n";
                }
            }

            if (exp.getLocation() == null || exp.getLocation().length() == 0) {
                res = true;
                msg += "Le champ Lieu de l'expérience " + (i + 1) + " est obligatoire.\n";
            }
            if (exp.getCompany() == null || exp.getCompany().length() == 0) {
                res = true;
                msg += "Le champ Entreprise de l'expérience " + (i + 1) + " est obligatoire.\n";
            }
        }

        LanguageManager languages = cv.getLanguages();
        for (int i = 0; i < languages.getLanguage().size(); i++) {
            Language l = languages.getLanguage().get(i);
            if (l.getName() == null || l.getName().length() == 0) {
                res = true;
                msg += "Le champ Titre de la langue " + (i + 1) + " est obligatoire.\n";
            }
        }

        SkillManager skills = cv.getSkills();
        for (int i = 0; i < skills.getSkill().size(); i++) {
            Skill skill = skills.getSkill().get(i);
            if (skill.getName() == null || skill.getName().length() == 0) {
                res = true;
                msg += "Le champ Titre de la compétence " + (i + 1) + " est obligatoire.\n";
            }
        }

        return res;
    }

}
