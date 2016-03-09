"use strict";
(function () {
    var App = angular.module("App");

    App.service("RestaurantService", ["$http", "$q", "AppConfig", function ($http, $q, AppConfig) {
        this.findRestaurantsNearZipCode = function (zipCode, page, pageSize, maxDistance) {
            var defered = $q.defer();
            $http.get("http://maps.googleapis.com/maps/api/geocode/json", {params: {"address": zipCode}}).
                then(function (result) {
                    var location = result.data.results[0].geometry.location;
                    console.log(location);

                    $http.get(AppConfig.basePath + "restaurants",
                        {
                            params: {
                                lat: location.lat,
                                lng: location.lng,
                                page: page,
                                pageSize: pageSize,
                                maxDistance: maxDistance
                            }
                        }).
                        then(function (result) {

                            defered.resolve(result.data);

                        }, function (error) {

                            defered.reject(error);
                        });
                }, function (error) {

                    defered.reject(error);
                });
            return defered.promise;
        };

        this.getRestaurant = function (id) {
            return $http.get(AppConfig.basePath + "restaurants/" + id);
        }

    }]);

})();