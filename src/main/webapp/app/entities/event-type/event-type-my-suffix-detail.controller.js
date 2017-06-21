(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventTypeMySuffixDetailController', EventTypeMySuffixDetailController);

    EventTypeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EventType', 'Event'];

    function EventTypeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, EventType, Event) {
        var vm = this;

        vm.eventType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationPlatformApp:eventTypeUpdate', function(event, result) {
            vm.eventType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
