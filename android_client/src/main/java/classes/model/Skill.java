package classes.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Emilie on 12/04/14.
 */
@Root
public class Skill {

    @Element
    private String name;
    @Element
    private int level;
    public static final int MAX_LEVEL = 20;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Skill(String name, int level) {
        this.name = name;
        setLevel(level);
    }

    public Skill() {

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level < 0) {
            this.level = 0;
        } else if (level > MAX_LEVEL) {
            this.level = MAX_LEVEL;
        } else {
            this.level = level;
        }
    }
}
