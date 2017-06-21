(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventMySuffixDetailController', EventMySuffixDetailController);

    EventMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Event', 'Person', 'EventType', 'Location', 'EventNotification'];

    function EventMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Event, Person, EventType, Location, EventNotification) {
        var vm = this;

        vm.event = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationPlatformApp:eventUpdate', function(event, result) {
            vm.event = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
