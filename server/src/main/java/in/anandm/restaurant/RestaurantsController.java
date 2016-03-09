package in.anandm.restaurant;

import java.util.List;

import org.bson.Document;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Document>> findNearestRestaurnats(@RequestParam(
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

        List<Document> foundRestaurants = restaurantDAO.findNearestRestaurants(
                lat, lng, maxDistance, page, pageSize);

        return new ResponseEntity<List<Document>>(foundRestaurants,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/restaurants/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Document> findOneRestaurant(@PathVariable(value = "id") String id) {

        Document foundRestaurant = restaurantDAO.findById(id);
        if (foundRestaurant == null) {
            return new ResponseEntity<Document>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Document>(foundRestaurant, HttpStatus.OK);
        }

    }

}
