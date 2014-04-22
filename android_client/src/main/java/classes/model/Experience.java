package classes.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Emilie on 08/04/14.
 */
@Root
public class Experience {

    //ATTRIBUTS
    @Element
    private String title;
    @Element(required=false)
    private String beginMonth;
    @Element
    private int beginYear;
    @Element(required=false)
    private String endMonth;
    @Element
    private int endYear;
    @Element
    private String location;
    @Element
    private String company;
    @Element(required=false)
    private String description;

    //CONSTRUCTOR
    public Experience() {
    }

    public Experience(String title, String beginMonth, int beginYear, String endMonth, int endYear, String location, String company, String description) {
        this.title = title;
        this.beginMonth = beginMonth;
        this.beginYear = beginYear;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.location = location;
        this.company = company;
        this.description = description;
    }

    //GETTERS
    public String getTitle() {

        return title;
    }

    public String getBeginMonth() {
        return beginMonth;
    }

    public int getBeginYear() {
        return beginYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public String getLocation() {
        return location;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    //SETTERS
    public void setTitle(String title) {
        this.title = title;
    }

    public void setBeginMonth(String beginMonth) {
        this.beginMonth = beginMonth;
    }

    public void setBeginYear(int beginYear) {
        this.beginYear = beginYear;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
