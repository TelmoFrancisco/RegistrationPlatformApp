(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventNotificationMySuffixController', EventNotificationMySuffixController);

    EventNotificationMySuffixController.$inject = ['EventNotification'];

    function EventNotificationMySuffixController(EventNotification) {

        var vm = this;

        vm.eventNotifications = [];

        loadAll();

        function loadAll() {
            EventNotification.query(function(result) {
                vm.eventNotifications = result;
                vm.searchQuery = null;
            });
        }
    }
})();
