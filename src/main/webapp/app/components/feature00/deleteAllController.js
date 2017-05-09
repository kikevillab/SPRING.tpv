tpv.controller('DeleteAllController', [ '$timeout', 'f00Service',
		function($timeout, f00Service) {
			"use strict";
			var vm = this;
			
            vm.completed = false;
			vm.deleteAll = deleteAll;
			vm.error = false;
			vm.response = "";

			function deleteAll() {
				const
				delay = 2000;
				f00Service.deleteAll().then(function(result) {
					// promise was fullfilled
					vm.completed = true;
					vm.response = "";
					$timeout(function() {
						vm.completed = false;
					}, delay)
				}, function(errors) {
					// handle errors
					vm.error = true;
					vm.response = errors;
					$timeout(function() {
						vm.error = false;
					}, delay)
				});
			}

		} ]);