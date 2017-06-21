(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('EventMySuffixDialogController', EventMySuffixDialogController);

    EventMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Event', 'Person', 'EventType', 'Location', 'EventNotification'];

    function EventMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Event, Person, EventType, Location, EventNotification) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.people = Person.query();
        vm.eventtypes = EventType.query();
        vm.locations = Location.query();
        vm.eventnotifications = EventNotification.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.event.id !== null) {
                Event.update(vm.event, onSaveSuccess, onSaveError);
            } else {
                Event.save(vm.event, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationPlatformApp:eventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;
        vm.datePickerOpenStatus.initDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
