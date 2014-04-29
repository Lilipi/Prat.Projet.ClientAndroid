package classes.model;

import classes.manager.DegreeManager;
import classes.manager.ExperienceManager;
import classes.manager.LanguageManager;
import classes.manager.SkillManager;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Emilie on 01/04/14.
 */
@Root(name="cv")
public class CV {

    @Element
    String nom;
    @Element
    String prenom;
    @Element(required=false)
    DegreeManager degrees;
    @Element(required=false)
    ExperienceManager experiences;
    @Element(required=false)
    SkillManager skills;
    @Element(required=false)
    LanguageManager languages;

    public CV(String nom, String prenom, ExperienceManager experiences, SkillManager skills, DegreeManager degrees, LanguageManager languages) {
        this.nom = nom;
        this.prenom = prenom;
        this.experiences = experiences;
        this.skills = skills;
        this.degrees = degrees;
        this.languages = languages;
    }

    public SkillManager getSkills() {
        return skills;
    }

    public void setSkills(SkillManager skills) {
        this.skills = skills;
    }

    public LanguageManager getLanguages() {
        return languages;
    }

    public void setLanguages(LanguageManager languages) {
        this.languages = languages;
    }

    public DegreeManager getDegrees() {
        return degrees;
    }

    public void setDegrees(DegreeManager degrees) {
        this.degrees = degrees;
    }


    public ExperienceManager getExperiences() {
        return experiences;
    }

    public void setExperiences(ExperienceManager experiences) {
        this.experiences = experiences;
    }

    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }


    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public CV() {
        nom ="ntest";
        prenom="ptest";
    }

}
