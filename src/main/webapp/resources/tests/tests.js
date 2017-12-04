
describe('stockbroker', function() {
	beforeEach(module('stockbroker'));

	var $controller, $rootScope, $uibModalInstance;

	beforeEach(inject(function(_$controller_, _$rootScope_){
		// The injector unwraps the underscores (_) from around the parameter names when matching
		$controller = _$controller_;
		$rootScope = _$rootScope_;
		$uibModalInstance = jasmine.createSpyObj('$uibModalInstance', ['close', 'dismiss']);
	}));

	describe('sell a stock', function() {

		var $scope, controller;

		beforeEach(function() {
			$scope = $rootScope.$new();
			$scope.alert = function(type, message) {
				console.log(type + " : " + message);
			};
			portfolioService = {
				sellStock: function(stock) {

				}
			};
			controller = $controller('SellStockModalController', { 
				$scope: $scope, 
				$uibModalInstance: $uibModalInstance,
				PortfolioService: {
					sellStock: function(stock) {

					}
				},
				stock: {
					symbol: 'SBIN',
					quantity: 5
				}
			});
			
		});

		it('checks selling quantity equals to or less than available quantity', function() {
			$scope.stock = {
					symbol: 'SBIN',
					quantity: 6
				};
			$scope.sell();
		});
	});

});