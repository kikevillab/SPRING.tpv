tpv.controller('LoginController', [ '$timeout', 'f00Service',
		function($timeout, f00Service) {
			"use strict";
			var vm = this;

			vm.completed = false;
			vm.error = false;
			vm.login = login;
			vm.mobile;
			vm.password;
			vm.respuesta = "";

			function login() {
				const
				delay = 2000;
				f00Service.login(vm.mobile, vm.password).then(function(result) {
					// promise was fullfilled
					vm.completed = true;
					vm.response = result.token + ":" + result.rol;
					sessionStorage.token = result.token;
					sessionStorage.rol = result.rol;
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