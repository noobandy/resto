package in.anandm.restaurant;

import in.anandm.restaurant.reports.ReportDefinition;

import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mongodb.MongoClient;

@Controller
@RequestMapping(value = "/reports")
public class ReportController {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private Datastore restaurantDataStore;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ReportDefinition>> getAllReports() {
        Query<ReportDefinition> query = restaurantDataStore
                .createQuery(ReportDefinition.class);
        List<ReportDefinition> reports = query.asList();

        return new ResponseEntity<List<ReportDefinition>>(reports,
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReportDefinition> addReport(@RequestBody ReportDefinition reportDefinition) {
        restaurantDataStore.save(reportDefinition);

        return new ResponseEntity<ReportDefinition>(reportDefinition,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReportDefinition> getReport(@PathVariable String id) {
        ReportDefinition reportDefinition = restaurantDataStore.find(
                ReportDefinition.class, "id", new ObjectId(id)).get();

        if (reportDefinition == null) {
            return new ResponseEntity<ReportDefinition>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ReportDefinition>(reportDefinition,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReportDefinition> updateReport(@PathVariable String id,
                                                         @RequestBody ReportDefinition reportDefinition) {

        restaurantDataStore.save(reportDefinition);

        return new ResponseEntity<ReportDefinition>(reportDefinition,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ReportDefinition> deleteReport(@PathVariable String id) {
        restaurantDataStore.delete(ReportDefinition.class, new ObjectId(id));

        return new ResponseEntity<ReportDefinition>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/data", method = RequestMethod.GET)
    public ResponseEntity<List<Document>> getReportData(@PathVariable String id) {
        ReportDefinition reportDefinition = restaurantDataStore.find(
                ReportDefinition.class, "id", new ObjectId(id)).get();

        if (reportDefinition == null) {
            return new ResponseEntity<List<Document>>(HttpStatus.NOT_FOUND);
        }

        List<Document> documents = reportDefinition.getReportQuery().getData(
                mongoClient.getDatabase("restaurants-db"),
                new HashMap<String, Object>());

        return new ResponseEntity<List<Document>>(documents, HttpStatus.OK);
    }
}
