tpv.controller('RegistrationController', [
		'$timeout',
		'f00Service',
		function($timeout, f00Service) {
			"use strict";
			var vm = this;

			vm.completed = false;
			vm.error = false;
			vm.mobile;
			vm.password;
			vm.registration = registration;
			vm.respuesta = "";
			vm.user = user;
			vm.username;

			function user() {
				if (sessionStorage.rol === "ADMIN")
					return "manager";
				else if (sessionStorage.rol === "MANAGER") {
					vm.password = "pass";
					return "customer";
				} else
					return "nobody";
			}

			function registration() {
				const
				delay = 2000;

				f00Service.registration(vm.mobile, vm.username, vm.password,
						vm.user()).then(function(result) {
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