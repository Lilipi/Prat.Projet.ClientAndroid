package classes.manager;

import classes.model.Skill;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilie on 12/04/14.
 */
@Root(name="skills")
public class SkillManager {

    @ElementList(required=false, inline =true)
    private List<Skill> skill;

    public SkillManager(List<Skill> skill) {
        this.skill = skill;
    }

    public SkillManager() {
        this.skill = new ArrayList<Skill>();
    }

    public List<Skill> getSkill() {
        return skill;
    }

    public void setSkill(List<Skill> skill) {
        this.skill = skill;
    }



}
