package in.anandm.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

@Service
public class RestaurantDAOImpl implements IRestaurantDAO {

    private MongoClient mongoClient;

    private MongoCollection<Document> restaurants;

    /**
     * @param mongoClient
     */
    @Autowired
    private RestaurantDAOImpl(MongoClient mongoClient) {
        super();
        this.mongoClient = mongoClient;
        this.restaurants = mongoClient.getDatabase("restaurants-db")
                .getCollection("restaurants");
    }

    /**
     * @methodName:RestaurantDAOImpl.java.findNearestRestaurants
     * @description:TODO
     * @author anandm
     * @param lat
     * @param lng
     * @param maxDistance
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Document> findNearestRestaurants(double lat,
                                                 double lng,
                                                 double maxDistance,
                                                 int page,
                                                 int pageSize) {

        List<Bson> aggregatePipeline = new ArrayList<Bson>();

        Geometry point = new Point(new Position(lng, lat));

        BasicDBObject geoNear = new BasicDBObject("$near", point);
        geoNear.put("distanceField", "distance");
        geoNear.put("maxDistance", maxDistance);
        geoNear.put("spherical", true);

        aggregatePipeline.add(geoNear);

        Bson project = Aggregates.project(Projections.fields(Projections
                .computed("averageScore", new BasicDBObject("$avg",
                        "$grades.score")), Projections.include(
                "name", "address", "restaurant_id", "borough", "cuisine",
                "distance")));

        aggregatePipeline.add(project);

        Bson sort = Aggregates.sort(Sorts.descending("averageScore"));

        aggregatePipeline.add(sort);

        Bson skip = Aggregates.skip((page - 1) * pageSize);

        aggregatePipeline.add(skip);

        Bson limit = Aggregates.limit(pageSize);

        aggregatePipeline.add(limit);

        MongoCursor<Document> cursor = restaurants.aggregate(aggregatePipeline)
                .iterator();

        List<Document> foundRestaurants = new ArrayList<Document>();

        while (cursor.hasNext()) {
            foundRestaurants.add(cursor.next());
        }
        return foundRestaurants;
    }

    /**
     * @methodName:RestaurantDAOImpl.java.findById
     * @description:TODO
     * @author anandm
     * @param id
     * @return
     */
    @Override
    public Document findById(String id) {
        Document foundRestaurant = restaurants.find(
                Filters.eq("_id", new ObjectId(id))).first();
        return foundRestaurant;
    }

}
