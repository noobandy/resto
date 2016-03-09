package in.anandm.restaurant;

import java.util.List;

import org.bson.Document;

/**
 * 
 * @className:in.anandm.restaurant.IRestaurantDAO.java
 * @description:TODO
 * @author anandm
 * @date Mar 9, 2016 3:17:20 PM
 */

public interface IRestaurantDAO {

    List<Document> findNearestRestaurants(double lat,
                                          double lng,
                                          double maxDistance,
                                          int page,
                                          int pageSize);

    Document findById(String id);
}
