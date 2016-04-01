package in.anandm.restaurant.reports;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "reports")
public class ReportDefinition {

    private ObjectId id;

    private String title;

    @Embedded
    private ReportQuery reportQuery;

    private String reportTemplate;

    /**
     * 
     */
    public ReportDefinition() {
        super();

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ReportQuery getReportQuery() {
        return reportQuery;
    }

    public void setReportQuery(ReportQuery reportQuery) {
        this.reportQuery = reportQuery;
    }

    public String getReportTemplate() {
        return reportTemplate;
    }

    public void setReportTemplate(String reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
