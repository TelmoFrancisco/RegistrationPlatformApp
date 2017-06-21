(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventNotificationMySuffixDeleteController',EventNotificationMySuffixDeleteController);

    EventNotificationMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'EventNotification'];

    function EventNotificationMySuffixDeleteController($uibModalInstance, entity, EventNotification) {
        var vm = this;

        vm.eventNotification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EventNotification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
