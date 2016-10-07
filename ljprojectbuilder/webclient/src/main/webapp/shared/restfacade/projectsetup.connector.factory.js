projectSetupConnectorFactory = ['$http', '$location', 'restConnectorFactory',
	function projectSetupConnectorFactory($http, $location, restConnectorFactory) {
	    var factory = {
	    		projectSetupAll: projectSetupAll,
	    		projectSetup: projectSetup,
	    		rename: rename,
	    		renamePackage: renamePackage
	     };
	    return factory;
		
		function projectSetupAll(project) {
			return $http.post('api/projectsetup/setupall', project)
				.then(
						restConnectorFactory.handleResponseSuccess,
						restConnectorFactory.handleResponseError
				);
		};
		
		function projectSetup(project) {
			return $http.post('api/projectsetup/setup', project)
				.then(function(response) { return response.data.metadata; }	);
		};
		
		function rename(project) {
			return $http.post('api/projectsetup/rename', project)
				.then(function(response) { return response.data.metadata; }	);
		};
		
		function renamePackage(project) {
			return $http.post('api/projectsetup/renamepackage', project)
				.then(function(response) { return response.data.metadata; }	);
		};
	}];