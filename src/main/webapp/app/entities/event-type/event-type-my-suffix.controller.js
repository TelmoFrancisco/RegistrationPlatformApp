(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventTypeMySuffixController', EventTypeMySuffixController);

    EventTypeMySuffixController.$inject = ['EventType'];

    function EventTypeMySuffixController(EventType) {

        var vm = this;

        vm.eventTypes = [];

        loadAll();

        function loadAll() {
            EventType.query(function(result) {
                vm.eventTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
