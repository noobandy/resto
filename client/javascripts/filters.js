"use strict";
(function () {
    var App = angular.module("App");

    App.filter("OddEven", [function () {

        return function (input) {
            if(isNaN(parseInt(input))) {
                throw new Error("input is not a number")
            }

            return input % 2 == 0 ? "Even" : "Odd";
        };
    }]);
})();