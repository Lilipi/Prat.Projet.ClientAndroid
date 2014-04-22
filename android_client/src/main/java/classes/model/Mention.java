package classes.model;

import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * Created by Emilie on 08/04/14.
 */
@Root
public enum Mention {

    PASSABLE("Passable"),
    AB("Assez bien"),
    BIEN("Bien"),
    TB("Très bien");


    private String name = "";

    //Constructeur
    Mention(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
