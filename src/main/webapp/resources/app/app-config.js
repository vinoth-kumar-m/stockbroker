'use strict';

app.config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl : 'resources/views/login.html',
		controller: 'UserController'
	}).when('/stocks', {
		templateUrl : 'resources/views/stocks.html',
		controller: 'StockExchangeController'
	}).when('/investors', {
		templateUrl : 'resources/views/investors.html',
		controller: 'InvestorController'
	}).when('/portfolio', {
		templateUrl : 'resources/views/portfolio.html',
		controller: 'PortfolioController'
	}).otherwise({
		redirectTo : '/'
	});
}]);

app.run(["$templateCache", function($templateCache) {
	$templateCache.put("template/alert.html",
			"<div class=\"modal-header\">" +
				"<div class=\"alert alert-{{type}}\" style=\"margin-bottom:0px; padding: 10px;\">" +
					"<button type=\"button\" class=\"close\" ng-click=\"close()\" >" +
						"<span class=\"glyphicon glyphicon-remove-circle\"></span>" +
					"</button>" +
					"{{type == 'danger' ? 'Error' : 'Information'}}" +
				"</div>" +
				"<div style=\"padding: 10px;\"><span>{{message}}</span></div>" +
			"</div>");
}]);

app.factory('httpInterceptor', ['$rootScope', '$q', '$location', function ($rootScope, $q, $location) {
	return {
		responseError: function (response) {
			if(response.status == 401) {
				$location.path('login');
			} 
			/* Open Error modal and display error */
			if(response.data) {
				$rootScope.alert('danger', response.data.message);
			} else {
				$rootScope.alert('danger', response.statusText);
			}
			/* reject deferred object so that it'll reach error block , else it'll execute success function */
			return $q.reject(response);
		}
	};
}]);

app.config(['$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('httpInterceptor');
}]);