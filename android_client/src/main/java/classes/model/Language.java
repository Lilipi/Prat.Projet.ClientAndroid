package classes.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Emilie on 12/04/14.
 */
@Root
public class Language {

    @Element
    private String name;
    @Element
    private float level;

    public final static int MAX_LEVEL = 5;

    public Language(String name, float level) {
        this.name = name;
        setLevel(level);
    }

    public Language() {

    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        if (level < 0) {
            level = 0;
        } else if (level > MAX_LEVEL) {
            level = MAX_LEVEL;
        } else {
            this.level = level;
        }
    }
}
