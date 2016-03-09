"use strict";
(function () {

    var App = angular.module("App");


    App.controller("HomeController", ["$scope", "RestaurantService",
        function ($scope, RestaurantService) {
            $scope.alerts = [];

            $scope.zipCode = "";

            $scope.restaurants = [];

            $scope.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            $scope.findRestaurants = function () {
                RestaurantService.findRestaurantsNearZipCode($scope.zipCode).
                    then(function (restaurants) {
                        $scope.alerts.push({
                            type: "success",
                            message: restaurants.length + " restaurants found."
                        });
                        $scope.restaurants = restaurants;
                    });
            };
        }]);

    App.controller("RestaurantController", ["$stateParams", "$scope", "RestaurantService",
        function ($stateParams, $scope, RestaurantService) {
            $scope.restaurantId = $stateParams.id;

            RestaurantService.getRestaurant($scope.restaurantId).then(function (restaurant) {

                $scope.restaurant = restaurant;
            });

        }]);


    App.controller("AboutController", ["$scope",
        function ($scope) {

        }]);
})();