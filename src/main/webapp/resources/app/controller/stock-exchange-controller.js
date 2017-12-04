'use strict';
 
app.controller('StockExchangeController', ['$scope', '$uibModal', '$log', 'constants', 'UserService', 'StockExchangeService', 
		function($scope, $uibModal, $log, constants, userService, stockExchangeService) {
	$scope.stocks = [];
	$scope.showAdminItems = angular.equals(constants.role.ADMINISTRATOR, userService.getUser()['role']);
	$scope.getStocks = function() {
		stockExchangeService.getStocks().then(function(response) {
			$scope.stocks = response.stocks;
		}, function(response) {
			if(response.status == 401) {
				$location.path("/login");
			}
		});
	};
	
	$scope.refresh = function() {
		stockExchangeService.refresh().then(function(response) {
			$scope.stocks = response.stocks;
		}, function(response) {
			if(response.status == 401) {
				$location.path("/login");
			}
		});
	};
	
	$scope.performance = function(stock) {
		stockExchangeService.getStockPerformance(stock).then(function(response) {
			var modalInstance = $uibModal.open({
				animation: true,
				ariaLabelledBy: 'modal-title',
				ariaDescribedBy: 'modal-body',
				templateUrl: 'stockPerformance.html',
				controller: 'StockPerformanceModalController',
				size: 'md',
				resolve: {
					params: {
						stock: stock,
						historicalData: response.historicalData
					}
				}
			});
		}, function(response) {
			if(response.status == 401) {
				$location.path("/login");
			}
		});
	};
	
	$scope.getStocks();
}]);

app.controller('StockPerformanceModalController', ['$scope', '$uibModalInstance', 'params', 
		function($scope, $uibModalInstance, params) {
	$scope.stock = params.stock;
	
	var labels = [];
	var data = [];
	angular.forEach(params.historicalData, function(stock, key) {
		labels.push(stock.launchDate);
		data.push(stock.marketPrice);
	});
	
	$scope.labels = labels;
	$scope.series = ['Price'];
	$scope.data = [data];
	
	$scope.onClick = function (points, evt) {
		console.log(points, evt);
	};
	
	$scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
	
	$scope.options = {
			scales: {
				yAxes: [{
					id: 'y-axis-1',
					type: 'linear',
					display: true,
					position: 'left'
				}]
			}
	};
}]);
