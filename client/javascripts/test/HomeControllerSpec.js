"use strict";

describe("HomeController", function () {

    var $controller;
    var controller;
    var $scope;
    var RestaurantService;
    var $rootScope;
    var foundRestaurants = [1, 2, 3, 4, 5];


    beforeEach(module("App"));

    beforeEach(inject(function (_$controller_, _$rootScope_, _RestaurantService_) {
        $controller = _$controller_;

        $rootScope = _$rootScope_;
        RestaurantService = _RestaurantService_;
    }));

    beforeEach(function () {
        $scope = $rootScope.$new();
        spyOn(RestaurantService, "findRestaurantsNearZipCode").and.callFake(function () {
            return {
                then: function (success, error) {
                    success(foundRestaurants);
                }
            };
        });


        controller = $controller("HomeController", {$scope: $scope, RestaurantService: RestaurantService});
    });

    it("should initialize scope properly", function () {

        //empty alerts
        expect($scope.alerts.length).toEqual(0);

        //empty zip code
        expect($scope.zipCode).toEqual("");

        //empty restaurants
        expect($scope.restaurants.length).toEqual(0);

        //find restaurants function
        expect($scope.findRestaurants).toBeDefined();
        expect(typeof $scope.findRestaurants).toEqual("function");

        //close alert function
        expect($scope.closeAlert).toBeDefined();
        expect(typeof $scope.closeAlert).toEqual("function");

    });


    it("should remove alert message by given index", function () {


        $scope.alerts.push("1");

        $scope.alerts.push("2");

        $scope.closeAlert(0);

        expect($scope.alerts.length).toEqual(1);

        expect($scope.alerts[0]).toEqual("2");

    });

    it("should contain returned restaurants after find restaurants ", function () {

        /*RestaurantService.findRestaurantsNearZipCode().then(function(restaurants) {

         expect(restaurants.length).toEqual(5);
         done();
         });*/

        $scope.findRestaurants();


        expect($scope.restaurants.length).toEqual(foundRestaurants.length);

        expect($scope.alerts.length).toEqual(1);

    });


});