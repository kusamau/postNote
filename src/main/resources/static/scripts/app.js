'use strict';

function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("text/html", ev.target.id);
}

function drop(ev) {
	if (ev.target.className != 'slot') {
		return;
	}
	ev.preventDefault();
	var data = ev.dataTransfer.getData("text/html");
	
	var node = document.getElementById(data);
	ev.target.appendChild(node);
}

angular.module('postnote',
		[ 'pascalprecht.translate', 'ngSanitize', 'ui.router' ]).constant(
		'baseURL', 'http://localhost:9999/').config(
		function($sceDelegateProvider, $stateProvider, $urlRouterProvider) {

			// -- Handles the Cross-Origin Resource Sharing (CORS)
			$sceDelegateProvider.resourceUrlWhitelist([
			// Allow same origin resource loads.
			'self' ]);
			// The blacklist overrides the whitelist so the open
			// redirect here is blocked.
			$sceDelegateProvider.resourceUrlBlacklist([]);

			// -- Routing
			$stateProvider.state('postnote', {
				url : "/",
				views : {
					'header' : {
						templateUrl : 'views/header.html'
					},
					'messagesRow' : {
						templateUrl : 'views/messages.html'
					},
					'content' : {
						templateUrl : 'views/mainContent.html'
					},
					'footer' : {
						templateUrl : 'views/footer.html'
					}
				},
				controller : 'PostNotesController'
			});
			$urlRouterProvider.otherwise('/');
		});