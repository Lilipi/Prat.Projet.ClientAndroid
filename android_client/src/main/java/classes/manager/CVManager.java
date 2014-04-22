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

    @ElementList(inline =true)
    private List<CV> resume;

    public List<CV> getResume() {
        return resume;
    }

    public void setResume(List<CV> resume) {
        this.resume = resume;
    }

    public CVManager() {
        resume = new ArrayList<CV>();
    }

    public CVManager(List<CV> cv) {
        this.resume = cv;
    }

}
