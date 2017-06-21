(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventNotificationMySuffixDetailController', EventNotificationMySuffixDetailController);

    EventNotificationMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EventNotification', 'Event'];

    function EventNotificationMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, EventNotification, Event) {
        var vm = this;

        vm.eventNotification = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationPlatformApp:eventNotificationUpdate', function(event, result) {
            vm.eventNotification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
