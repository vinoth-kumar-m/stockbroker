'use strict';
 
app.controller('PortfolioController', ['$scope', '$uibModal', '$log', '$timeout', 'constants', 'PortfolioService', 'UserService', 
		function($scope, $uibModal, $log, $timeout, constants, portfolioService, userService) {
	$scope.user = userService.getUser();
	$scope.showInvestorItems = angular.equals(constants.role.INVESTOR, $scope.user['role']);
	$scope.autoRefresh = false;
	
	$scope.toggleAutoRefresh = function() {
		$scope.autoRefresh = !$scope.autoRefresh;
		if($scope.autoRefresh) {
			$scope.getUserPortfolio();
		} else {
			$timeout.cancel(timer);
		}
	}
	
	$scope.getUserPortfolio = function() {
		if($scope.user) {
			portfolioService.getUserPortfolio($scope.user['username']).then(function(response) {
				$scope.dataLoading = false;
				$scope.userStocks = response.userStocks;
				$scope.summary = {
					totalStocks: 0,
					overAllBuy: 0.0,
					overAllCurrent: 0.0
				}
				$scope.totalStocks = 0;
				$scope.overAllBuy = 0;
				$scope.overAllCurrent = 0;
				angular.forEach($scope.userStocks, function(stock, key) {
					stock.buyTotal = stock.quantity * stock.price;
					stock.currentTotal = stock.quantity * stock.marketPrice;
					stock.profitLoss = (stock.currentTotal >= stock.buyTotal) ? 'Profit' : 'Loss';
					$scope.summary.totalStocks = $scope.summary.totalStocks + stock.quantity;
					$scope.summary.overAllBuy = $scope.summary.overAllBuy + stock.buyTotal;
					$scope.summary.overAllCurrent = $scope.summary.overAllCurrent + stock.currentTotal;
				});
			}, function(response) {
				if(response.status == 401) {
					$location.path("/login");
				}
			});
			
			if($scope.autoRefresh) {
				timer = $timeout(function() {
					$scope.getUserPortfolio();
				}, 10000);
			}
		}
	};
	
	$scope.sellStock = function(stock) {
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: 'sellStockModal.html',
			controller: 'SellStockModalController',
			size: 'md',
			resolve: {
				stock: stock
			}
		});

		modalInstance.result.then(function () {
			$scope.getUserPortfolio();
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};
	
	$scope.buyStock = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: 'buyStockModal.html',
			controller: 'BuyStockModalController',
			size: 'md'
		});

		modalInstance.result.then(function () {
			$scope.getUserPortfolio();
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};
	
	$scope.getUserPortfolio();
	
}]);


app.controller('SellStockModalController', ['$scope', '$uibModalInstance', 'PortfolioService', 'stock', 
		function($scope, $uibModalInstance, portfolioService, stock) {
	
	$scope.stock = angular.copy(stock);
	$scope.dataLoading = false;
	
	$scope.sell = function () {
		if(parseInt($scope.stock.quantity) > stock.quantity) {
			$scope.alert('danger', 'Quantity should be less than or equal to ' + stock.quantity);
			return false;
		}
		$scope.dataLoading = true;
		portfolioService.sellStock($scope.stock).then(function(data) {
			$scope.alert('success', 'Sell: ' + $scope.stock.symbol + ' successful');
			$uibModalInstance.close(data);
		}, function(response) {
			$scope.dataLoading = false;
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
}]);

app.controller('BuyStockModalController', ['$scope', '$uibModalInstance', 'PortfolioService', 'StockExchangeService', 
		function($scope, $uibModalInstance, portfolioService, stockExchangeService) {
	$scope.stocks = [];
	$scope.stock = {};
	$scope.dataLoading = false;
	
	$scope.getStocks = function() {
		stockExchangeService.getStocks().then(function(data) {
			$scope.stocks = data.stocks;
		});
	};
	
	$scope.populate = function() {
		angular.forEach($scope.stocks, function(stock, idx) {
			if(stock.symbol == $scope.stock.symbol) {
				$scope.stock.marketPrice = stock.marketPrice;
				$scope.stock.availableShares = stock.availableShares;
				return;
			}
		});
	};
	
	$scope.calculate = function() {
		if($scope.stock.marketPrice && $scope.stock.quantity) {
			$scope.stock.total = $scope.stock.marketPrice * $scope.stock.quantity;
		}
	};
	
	$scope.buy = function () {
		if(parseInt($scope.stock.quantity) > $scope.stock.availableShares) {
			$scope.alert('danger', 'Quantity should be less than or equal to ' + $scope.stock.availableShares);
			return false;
		}
		$scope.dataLoading = true;
		portfolioService.buyStock($scope.stock).then(function(data) {
			$scope.alert('success', 'Buy: ' + $scope.stock.symbol + ' successful');
			$uibModalInstance.close(data);
		}, function(response) {
			$scope.dataLoading = false;
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.getStocks();
}]);

