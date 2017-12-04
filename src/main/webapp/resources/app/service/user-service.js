'use strict';
 
app.factory('UserService', function($http, $q, constants) {
	
	var user;
	
	var getUser = function() {
		return user;
	};
	
	var setUser = function(loggedInUser) {
		user = loggedInUser;
	};
	
	var login = function(user) {
    	var deferred = $q.defer();
        $http.post(constants.url.LOGIN, user).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };
    
    var register = function(user) {
    	var deferred = $q.defer();
        $http.post(constants.url.REGISTER_USER, user).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };

    var getUsers = function() {
    	var deferred = $q.defer();
        $http.get(constants.url.GET_USERS).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    };
    
    var logout = function() {
    	var deferred = $q.defer();
        $http.get(constants.url.LOGOUT).then(
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
    	login: login,
    	register: register,
    	getUser: getUser,
    	setUser: setUser,
    	getUsers: getUsers,
    	logout: logout
    };
 
});