'use strict';
 
app.controller('UserController', ['$scope', '$location', '$uibModal', 'constants', 'UserService', 
		function($scope, $location, $uibModal, constants, userService) {
	
	$scope.dataLoading = false;
	$scope.user = {};
	
	$scope.login = function() {
		$scope.dataLoading = true;
		userService.login($scope.user).then(function(user) {
			userService.setUser(user);
			$scope.$parent.menuItems = constants.menu[user.role];
			if(user.role == constants.role.ADMINISTRATOR) {
				$location.path("/investors");
			} else if(user.role == constants.role.INVESTOR) {
				$location.path("/portfolio");
			} else {
				$location.path("/login");
			}
		}, function(response) {
			$scope.dataLoading = false;
		});
	};
	
	$scope.register = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: 'userRegistration.html',
			controller: 'RegisterUserController',
			size: 'md'
		});
		
		modalInstance.result.then(function () {
			$scope.alert('success', 'Registration successful');
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

}]);

app.controller('RegisterUserController', ['$scope', '$uibModalInstance', 'UserService', 
		function($scope, $uibModalInstance, userService) {
	$scope.dataLoading = false;
	$scope.register = function() {
		$scope.dataLoading = true;
		userService.register($scope.user).then(function(data) {
			$uibModalInstance.close(data);
		}, function(response) {
			$scope.dataLoading = false;
		});
	};
	
	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
}]);

app.controller('InvestorController', ['$scope', '$location', '$uibModal', 'UserService', 
		function($scope, $location, $uibModal, userService) {
	$scope.users = [];
	$scope.getUsers = function() {
		userService.getUsers().then(function(data) {
			$scope.users = data.users;
		}, function(response) {
			if(response.status == 401) {
				$location.path("/login");
			}
		});
	};
	
	$scope.portfolio = function(user) {
		userService.setUser(user);
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: 'resources/views/portfolio.html',
			controller: 'PortfolioController',
			windowClass: 'portfolio-modal-window'
		});
	};
	
	$scope.getUsers();
}]);