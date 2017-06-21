(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('PersonMySuffixDetailController', PersonMySuffixDetailController);

    PersonMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Location', 'Event'];

    function PersonMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Location, Event) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationPlatformApp:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
