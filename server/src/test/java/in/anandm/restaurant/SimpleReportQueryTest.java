package in.anandm.restaurant;

import in.anandm.restaurant.reports.SimpleReportQuery;

import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class SimpleReportQueryTest {

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
        SimpleReportQuery query = new SimpleReportQuery();
        query.setCollectionName("restaurants");
        Document filters = new Document("cuisine", "Hamburgers");
        Document projections = new Document("name", 1);
        Document sorts = new Document("name", 1);

        query.setFilters(filters.toJson());

        query.setProjections(projections.toJson());

        query.setSorts(sorts.toJson());

        MongoDatabase database = mongoClient.getDatabase("restaurants-db");

        List<Document> documents = query.getData(
                database, new HashMap<String, Object>());

        Assert.assertEquals(433L, documents.size());

        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }
}
