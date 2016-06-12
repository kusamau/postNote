'use strict';

angular.module('postnote')
		.factory('dbServiceFactory', [ '$http', 'baseURL', function($http, baseURL) {
			var dbService = {};
			
			// Retrieves all the postnotes
			dbService.getPosts = function() {
				return $http.get(baseURL + 'notes');
			}

			// Deletes one postnote	
			dbService.deletePost = function(id) {
				return $http.delete(baseURL + 'notes/' + id);
			}
			
			// Inserts one postnote			
			dbService.insertPost = function(post) {
				return $http.post(baseURL + 'notes', post);
			}
			
			// Updates one postnote
			dbService.updatePost = function(post) {
				return $http.put(baseURL + 'notes', post);
			}
			
			return dbService;
		} ]);