package classes.manager;

import classes.model.Experience;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilie on 12/04/14.
 */
@Root(name="experiences")
public class ExperienceManager {

    @ElementList(required=false, inline =true)
    private List<Experience> experience;

    public ExperienceManager(List<Experience> experience) {
        this.experience = experience;
    }

    public ExperienceManager() {
        this.experience = new ArrayList<Experience>();
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }
}
