package in.anandm.restaurant.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

public abstract class ReportQuery {

    protected String collectionName;

    protected List<ReportQueryParameter> parameters = new ArrayList<ReportQueryParameter>();

    /**
     * 
     */
    public ReportQuery() {
        super();

    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<ReportQueryParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ReportQueryParameter> parameters) {
        this.parameters = parameters;
    }

    public abstract List<Document> getData(MongoDatabase database,
                                           Map<String, Object> parameters);

}
