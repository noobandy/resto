"use strict";
(function () {

    angular.module("App").config(["$stateProvider", "$urlRouterProvider",
        function ($stateProvider, $urlRouterProvider) {

            // For any unmatched url, redirect to /
            $urlRouterProvider.otherwise("/");

            $stateProvider.state({
                name: "home",
                url: "/",
                templateUrl: "templates/home.html",
                controller: "HomeController",
            }).state({
                name: "restaurant details",
                url: "/restaurants/:id",
                templateUrl: "templates/restaurant.html",
                controller: "RestaurantController"
            }).state({
                name: "about",
                url: "/about",
                templateUrl: "templates/about.html",
                controller: "AboutController"
            }).state({
                name: "students",
                url: "/students",
                templateUrl: "templates/students.html",
                controller: function($scope) {
                    $scope.students = [];

                    for(var i = 1; i < 11; i++) {
                        $scope.students.push({
                            name : "Name "+i,
                            age : i * 10 + i,
                            sex : i % 2 === 0 ? "M" : "F"
                        });
                    }

                    $scope.property = "$";
                    $scope.value = "";

                    $scope.getFilter = function() {
                        var filter = {};
                        filter[$scope.property] = $scope.value;
                        return filter;
                    };


                }
            });;
        }]);
})();