'use strict';
 
app.factory('PortfolioService', function($http, $q, constants){

    var getUserPortfolio = function(username) {
    	var deferred = $q.defer();
        $http.get(constants.url.USER_PORTFOLIO + "/" + username).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };
    
    var sellStock = function(stock) {
    	var deferred = $q.defer();
        $http.post(constants.url.SELL_STOCK, stock).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };
    
    var buyStock = function(stock) {
    	var deferred = $q.defer();
        $http.post(constants.url.BUY_STOCK, stock).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };
 
    return {
    	getUserPortfolio: getUserPortfolio,
    	sellStock: sellStock,
    	buyStock: buyStock
    };
 
});