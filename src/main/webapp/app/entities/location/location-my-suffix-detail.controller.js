(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('LocationMySuffixDetailController', LocationMySuffixDetailController);

    LocationMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Location', 'Event'];

    function LocationMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Location, Event) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationPlatformApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
