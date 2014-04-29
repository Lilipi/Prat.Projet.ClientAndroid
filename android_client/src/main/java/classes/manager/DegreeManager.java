package classes.manager;

import classes.model.Degree;
import classes.model.Mention;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilie on 12/04/14.
 */
@Root(name="degrees")
public class DegreeManager {

    @ElementList(required=false, inline =true)
    private List<Degree> degree;

    public List<Degree> getDegree() {
        return degree;
    }

    public void setDegree(List<Degree> degree) {
        this.degree = degree;
    }

    public DegreeManager(List<Degree> degree) {
        this.degree = degree;
    }

    public DegreeManager() {
        this.degree = new ArrayList<Degree>();
    }



}
