(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventNotificationMySuffixDialogController', EventNotificationMySuffixDialogController);

    EventNotificationMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EventNotification', 'Event'];

    function EventNotificationMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EventNotification, Event) {
        var vm = this;

        vm.eventNotification = entity;
        vm.clear = clear;
        vm.save = save;
        vm.events = Event.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.eventNotification.id !== null) {
                EventNotification.update(vm.eventNotification, onSaveSuccess, onSaveError);
            } else {
                EventNotification.save(vm.eventNotification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationPlatformApp:eventNotificationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
