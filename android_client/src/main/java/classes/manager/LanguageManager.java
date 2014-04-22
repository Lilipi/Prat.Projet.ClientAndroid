package classes.manager;

import classes.model.Language;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilie on 12/04/14.
 */
@Root(name="languages")
public class LanguageManager {

    @ElementList(inline =true)
    private List<Language> language;

    public LanguageManager(List<Language> language) {
        this.language = language;
    }

    public LanguageManager() {
        this.language = new ArrayList<Language>();
    }

    public List<Language> getLanguage() {

        return language;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }
}
