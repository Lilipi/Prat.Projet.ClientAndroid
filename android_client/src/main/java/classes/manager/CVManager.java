package classes.manager;

import classes.model.CV;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilie on 01/04/14.
 */
@Root(name="CVs")
public class CVManager {

    @ElementList(inline =true, required =false)
    private List<CV> cv;

    public List<CV> getCv() {
        return cv;
    }

    public void addCv(CV cv) {this.cv.add(cv);}

    public void setCv(List<CV> cv) {
        this.cv = cv;
    }

    public CVManager() {
        cv = new ArrayList<CV>();
    }

    public CVManager(List<CV> cv) {
        this.cv = cv;
    }

}
