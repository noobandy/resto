package in.anandm.restaurant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.GeoNear;
import org.mongodb.morphia.aggregation.Projection;
import org.mongodb.morphia.aggregation.Sort;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RestaurantsController {

    @Autowired
    private IRestaurantDAO restaurantDAO;

    @Autowired
    private Datastore restaurantDataStore;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Restaurant>> findNearestRestaurnats(@RequestParam(
                                                                    value = "lat",
                                                                    required = true) double lat,
                                                            @RequestParam(
                                                                    value = "lng",
                                                                    required = true) double lng,
                                                            @RequestParam(
                                                                    value = "maxDistance",
                                                                    required = false) Double maxDistance,
                                                            @RequestParam(
                                                                    value = "page",
                                                                    required = false) Integer page,
                                                            @RequestParam(
                                                                    value = "pageSize",
                                                                    required = false) Integer pageSize) {

        maxDistance = maxDistance != null ? maxDistance : 500;
        page = page != null ? page : 1;
        pageSize = pageSize != null ? pageSize : 20;
        AggregationPipeline aggregationPipeline = restaurantDataStore
                .createAggregation(Restaurant.class);
        Iterator<Restaurant> iterator = aggregationPipeline
                .geoNear(
                        GeoNear.builder("distance").setMaxDistance(maxDistance)
                                .setNear(lat, lng).setSpherical(true).build())
                .project(
                        Projection.projection("restaurant_id"),
                        Projection.projection("name"),
                        Projection.projection("borough"),
                        Projection.projection("cuisine"),
                        Projection.projection("address"),
                        Projection.projection("distance"),
                        Projection.projection(
                                "averageScore",
                                Projection.expression("$avg", "$grades.score")))
                .sort(new Sort("averageScore", -1)).skip((page - 1) * pageSize)
                .limit(pageSize).aggregate(Restaurant.class);

        List<Restaurant> foundRestaurants = new ArrayList<Restaurant>();

        while (iterator.hasNext()) {
            foundRestaurants.add(iterator.next());
        }

        return new ResponseEntity<List<Restaurant>>(foundRestaurants,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/restaurants/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Restaurant> findOneRestaurant(@PathVariable(value = "id") String id) {

        Query<Restaurant> query = restaurantDataStore
                .createQuery(Restaurant.class);
        query.field("id").equal(new ObjectId(id));

        Restaurant foundRestaurant = query.get();

        if (foundRestaurant == null) {
            return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Restaurant>(foundRestaurant,
                    HttpStatus.OK);
        }

    }
}
