package in.anandm.restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
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

        // List<Bson> aggregatePipeline = new ArrayList<Bson>();
        /**
         * [{ $geoNear: { near: { type: "Point", coordinates: [lng, lat] },
         * distanceField: "distance", maxDistance: maxDistance, spherical: true
         * } }, { $project: { name: 1, address: 1, restaurant_id: 1, borough: 1,
         * cuisine: 1, averageScore: {$avg: "$grades.score"}, distance: 1 } }, {
         * $sort: { averageScore: -1 } }, { $skip: page * pageSize }, { $limit:
         * pageSize }]
         */
        Geometry point = new Point(new Position(lng, lat));
        /*
         * BasicDBObject near = new BasicDBObject("near", point);
         * near.put("distanceField", "distance"); near.put("maxDistance",
         * maxDistance); near.put("spherical", true); BasicDBObject geoNear =
         * new BasicDBObject("$geoNear", near);
         * 
         * aggregatePipeline.add(geoNear);
         * 
         * Bson project = Aggregates.project(Projections.fields(Projections
         * .include( "name", "address", "restaurant_id", "borough", "cuisine",
         * "distance"), Projections.computed( "averageScore", new
         * BasicDBObject("$avg", "$grades.score"))));
         * 
         * aggregatePipeline.add(project);
         * 
         * Bson sort = Aggregates.sort(Sorts.descending("averageScore"));
         * 
         * aggregatePipeline.add(sort);
         * 
         * Bson skip = Aggregates.skip((page - 1) * pageSize);
         * 
         * aggregatePipeline.add(skip);
         * 
         * Bson limit = Aggregates.limit(pageSize);
         * 
         * aggregatePipeline.add(limit);
         */

        List<Document> foundRestaurants = new ArrayList<Document>();

        foundRestaurants = restaurants
                .aggregate(
                        Arrays.asList(
                                new Document("$geoNear", new Document("near",
                                        point)
                                        .append("maxDistance", maxDistance)
                                        .append("distanceField", "distance")
                                        .append("spherical", true)),
                                new Document(
                                        "$project",
                                        new Document("name", 1)
                                                .append("address", 1)
                                                .append("restaurant_id", 1)
                                                .append("borough", 1)
                                                .append("cuisine", 1)
                                                .append(
                                                        "averageScore",
                                                        new Document("$avg",
                                                                "$grades.score"))),
                                new Document("$sort", new Document(
                                        "averageScore", -1)), new Document(
                                        "$skip", (page - 1) * pageSize),
                                new Document("$limit", pageSize))).into(
                        foundRestaurants);

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
