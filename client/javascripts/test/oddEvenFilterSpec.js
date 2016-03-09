"use strict";

describe("odd event filter", function () {


    var $filter;

    beforeEach(module('App'));

    beforeEach(inject(function (_$filter_) {

        $filter = _$filter_;
    }));


    it("should return Odd given odd number input", function () {

        var oddEven = $filter("OddEven");

        expect(oddEven(3)).toEqual("Odd");
    });

    it("should return Even given even number input", function () {

        var oddEven = $filter("OddEven");

        expect(oddEven(4)).toEqual("Even");
    });

    it("should throw exception for non numeric input", function () {

        var oddEven = $filter("OddEven");

        expect(function () {
            oddEven("boom")
        }).toThrow();
    });
});

