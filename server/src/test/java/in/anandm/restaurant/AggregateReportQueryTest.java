package in.anandm.restaurant;

import in.anandm.restaurant.reports.AggregateReportQuery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class AggregateReportQueryTest {

    private static MongoClient mongoClient;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        mongoClient = new MongoClient();

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        mongoClient.close();
        mongoClient = null;

    }

    @Test
    public void testGetData() {
        AggregateReportQuery query = new AggregateReportQuery();
        query.setCollectionName("restaurants");
        Document match = new Document("$match", new Document("cuisine",
                "Hamburgers"));
        Document group = new Document("$group",
                new Document("_id", "$cuisine").append("count", new Document(
                        "$sum", 1)));

        List<String> stages = Arrays.asList(match.toJson(), group.toJson());

        query.setStages(stages);

        MongoDatabase database = mongoClient.getDatabase("restaurants-db");

        List<Document> documents = query.getData(
                database, new HashMap<String, Object>());

        Assert.assertEquals(1L, documents.size());

        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }
}
