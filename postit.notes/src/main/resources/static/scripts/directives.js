angular.module('postnote').directive('singlePost', function() {
	return {
		restrict : 'E',
		templateUrl : 'views/singlePost.html',
	    transclude: true
	};
}).directive('newPost', function() {
	return {
		restrict : 'E',
		templateUrl : 'views/newPost.html',
	    transclude: true
	};
});