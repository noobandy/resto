package in.anandm.restaurant.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SimpleReportQuery extends ReportQuery {

    private String filters;

    private String projections;

    private String sorts;

    /**
     * 
     */
    public SimpleReportQuery() {
        super();

    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getProjections() {
        return projections;
    }

    public void setProjections(String projections) {
        this.projections = projections;
    }

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    @Override
    public List<Document> getData(MongoDatabase database,
                                  Map<String, Object> parameters) {
        MongoCollection<Document> collection = database
                .getCollection(collectionName);

        List<Document> documents = new ArrayList<Document>();

        FindIterable<Document> iterable = null;
        if (filters != null && !filters.trim().isEmpty()) {
            iterable = collection.find(Document.parse(filters));
        }
        else {
            iterable = collection.find();
        }

        if (projections != null && !projections.trim().isEmpty()) {
            iterable.projection(Document.parse(projections));
        }

        if (sorts != null && !sorts.trim().isEmpty()) {
            iterable.sort(Document.parse(sorts));
        }

        documents = iterable.into(documents);

        return documents;
    }
}
