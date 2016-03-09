"use strict";
(function () {
    var App = angular.module("App", ["ngResource", "ui.router", "ui.bootstrap", "LocalStorageModule",
        "pascalprecht.translate", "angular-loading-bar"]);

    App.constant("AppConfig", {
        basePath: "http://localhost/api/"
    });

    App.config(["localStorageServiceProvider",
        function (localStorageServiceProvider) {
            localStorageServiceProvider.setStorageType('sessionStorage');
            localStorageServiceProvider.setPrefix("App");
        }]);

    App.config(["cfpLoadingBarProvider", function (cfpLoadingBarProvider) {
        cfpLoadingBarProvider.spinnerTemplate = '<div id="pluswrap">\
	<div class="plus">\
	<img src="images/loader.gif">\
	</div>\
	</div>';

    }]);


    App.config(['$translateProvider', function ($translateProvider) {
        $translateProvider.useStaticFilesLoader({
            files: [{
                prefix: 'languages/',
                suffix: '.json'
            }]
        });
        $translateProvider.preferredLanguage('en');
        $translateProvider.fallbackLanguage('en');
    }]);
})();

