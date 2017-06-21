(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventTypeMySuffixDeleteController',EventTypeMySuffixDeleteController);

    EventTypeMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'EventType'];

    function EventTypeMySuffixDeleteController($uibModalInstance, entity, EventType) {
        var vm = this;

        vm.eventType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EventType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
