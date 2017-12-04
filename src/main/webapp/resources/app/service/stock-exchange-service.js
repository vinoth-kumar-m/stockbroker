'use strict';
 
app.factory('StockExchangeService', function($http, $q, constants){

    var getStocks = function() {
    	var deferred = $q.defer();
        $http.get(constants.url.GET_STOCKS).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };
    
    var refresh = function() {
    	var deferred = $q.defer();
        $http.get(constants.url.REFRESH_STOCK_RATES).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };
    
    var getStockPerformance = function(stock) {
    	var deferred = $q.defer();
        $http.get(constants.url.GET_STOCK_PERFORMANCE + "/" + stock.symbol).then(
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
    	getStocks: getStocks,
    	refresh: refresh,
    	getStockPerformance: getStockPerformance
    };
 
});