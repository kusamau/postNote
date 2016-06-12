'use strict';

/*
 * Loads a problem in scope scope: the $scope dbServiceFactory: the function
 * calling the service problemId: the problemId
 */
var loadNotes = function(scope, dbServiceFactory) {
	dbServiceFactory.getPosts().then(function(response) {
		scope.notes = response.data;
	}, function(response) {
		// $scope.$routeParams.type = 'FAILED'
	});
};

var createMessage = function(scope, text_msg, level) {
	scope.message = {
		text : text_msg,
		alert_level : level
	};
}

/**
 * Returns a random integer between min (inclusive) and max (inclusive) Using
 * Math.round() will give you a non-uniform distribution!
 */
function getRandomInt(min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

function getRandomColor() {
	var colors = [ 'yellow', 'green', 'orange', 'red', 'grey' ];
	return colors[getRandomInt(0, 4)];
}

function resetNewPost(scope) {
	scope.new_post = {
		note : 'Need a new PostNote?',
		color : getRandomColor(),
		id : 'new_post'
	};
}

angular.module('postnote').config(
		[
				'$translateProvider',
				'baseURL',
				function($translateProvider, baseURL) {
					$translateProvider.useSanitizeValueStrategy(null)
							.registerAvailableLanguageKeys(
									[ 'en', 'it', 'ru' ], {
										'en_*' : 'en',
										'it_*' : 'it'
									});
					$translateProvider.useUrlLoader(baseURL + "i18n")
							.determinePreferredLanguage();

				} ]);

angular
		.module('postnote')
		.controller(
				'PostNotesController',
				[
						'$scope',
						'dbServiceFactory',
						'$state',
						function($scope, dbServiceFactory, $state) {

							$scope.post_allocations = new Array(28);
							$scope.message = {
								text : '',
								alert_level : 'alert-success'
							};

							resetNewPost($scope);

							$scope.editingPostId = null;
							$scope.creatingPost = false;
							loadNotes($scope, dbServiceFactory);

							$scope.appIsEditing = function(post) {
								if ($scope.editingPostId
										&& $scope.editingPostId === post.id) {
									return true;
								}
							}

							$scope.handleUpdateEnter = function(event, post) {
								// Pressed return;
								if (event.which === 13
										&& $scope.appIsEditing(post)) {
									$scope.updatePost(post);
								}
								;
							}

							$scope.handleInsertEnter = function(event) {
								// Pressed return;
								if (event.which === 13 && $scope.creatingPost) {
									$scope.insertPost();
								}
								;
							}

							$scope.editPost = function(post) {
								$scope.editingPostId = post.id;
							};

							$scope.cancelEdit = function() {
								$scope.editingPostId = null;
							};

							$scope.createPost = function() {
								$scope.creatingPost = true;
							};

							$scope.dropPost = function() {
								$scope.creatingPost = false;
								$state.reload('postnote');
							};

							$scope.deletePost = function(post) {
								dbServiceFactory
										.deletePost(post.id)
										.then(
												function(response) {
													loadNotes($scope,
															dbServiceFactory);
													createMessage($scope,
															'Posts deleted',
															'alert-green');
												},
												function(response) {
													createMessage(
															$scope,
															'Posts not available',
															'alert-warning');
												});

							};

							$scope.updatePost = function(post) {
								dbServiceFactory.updatePost(post).then(
										function(response) {
											$scope.editingPostId = null;
											createMessage($scope,
													'Post updated.',
													'alert-success');
										},
										function(response) {
											createMessage($scope,
													response.data.message,
													'alert-warning');
										});
							};

							$scope.insertPost = function() {
								$scope.new_post.id = null;
								dbServiceFactory
										.insertPost($scope.new_post)
										.then(
												function(response) {
													$scope.creatingPost = false;
													resetNewPost($scope);
													loadNotes($scope,
															dbServiceFactory);
													document
															.getElementById(
																	'new_post_container')
															.appendChild(
																	document
																			.getElementById('new_post'));
													createMessage($scope,
															'Post created.',
															'alert-success');
												},
												function(response) {
													createMessage(
															$scope,
															response.data.message,
															'alert-warning');
												});
							};
						} ]);