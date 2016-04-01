package in.anandm.restaurant.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AggregateReportQuery extends ReportQuery {

    private List<String> stages = new ArrayList<String>();

    /**
     * 
     */
    public AggregateReportQuery() {
        super();

    }

    public List<String> getStages() {
        return stages;
    }

    public void setStages(List<String> stages) {
        this.stages = stages;
    }

    @Override
    public List<Document> getData(MongoDatabase database,
                                  Map<String, Object> parameters) {

        MongoCollection<Document> collection = database
                .getCollection(collectionName);

        List<Document> documents = new ArrayList<Document>();

        List<Document> pipeline = new ArrayList<Document>(stages.size());

        for (String stage : stages) {
            pipeline.add(Document.parse(stage));
        }

        documents = collection.aggregate(pipeline).into(documents);

        return documents;
    }

}
