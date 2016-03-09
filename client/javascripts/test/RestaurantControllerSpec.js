"use strict";

describe("HomeController", function () {

    var $controller;
    var controller;
    var $scope;
    var RestaurantService;
    var $rootScope;
    var foundRestaurant = {name: "some name"};
    var $stateParams;


    beforeEach(module("App"));

    beforeEach(inject(function (_$controller_, _$rootScope_, _RestaurantService_, _$stateParams_) {
        $controller = _$controller_;

        $rootScope = _$rootScope_;
        RestaurantService = _RestaurantService_;

        $stateParams = _$stateParams_;
    }));

    beforeEach(function () {
        $stateParams.id = "some id";
        $scope = $rootScope.$new();
        spyOn(RestaurantService, "getRestaurant").and.callFake(function (id) {
            return {
                then: function (success, error) {
                    success(foundRestaurant);
                }
            };
        });


        controller = $controller("RestaurantController", {
            $stateParams: $stateParams,
            $scope: $scope,
            RestaurantService: RestaurantService
        });
    });


    it("should contain returned restaurant after get restaurant ", function () {

        expect($scope.restaurantId).toEqual($stateParams.id);


        expect($scope.restaurant).toBeDefined();

        expect($scope.restaurant).toEqual(foundRestaurant);


    });


});