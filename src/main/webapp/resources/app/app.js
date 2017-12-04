'use strict';

var app = angular.module('stockbroker', [ 'ngRoute', 'ngAnimate', 'ui.bootstrap', 'ui.toggle', 'chart.js' ]);

var timer;
app.controller('RouteController', ['$rootScope', '$scope', '$uibModal', '$location', '$timeout', 'UserService', 
		function($rootScope, $scope, $uibModal, $location, $timeout, userService) {
	$scope.menuItems = [];
	
	$scope.logout = function() {
		$scope.menuItems = [];
		userService.logout().then(function() {
			$location.path('login');
		});
	};
	
	$location.path('login');
	
	$scope.$on('$routeChangeStart', function($event, next, current) { 
		if(timer) {
			$timeout.cancel(timer);
		}
	});
	
	$rootScope.alert = function(type, message) {
		$uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: 'template/alert.html',
			controller: function($scope, $uibModalInstance, params) {
				$scope.type = params.type;
				$scope.message = params.message;
				$scope.close = function() {
					$uibModalInstance.close();
				}
			},
			size: 'md',
			resolve: {
				params: {
					type: type,
					message: message
				}
			}
		});
	};
	
}]);