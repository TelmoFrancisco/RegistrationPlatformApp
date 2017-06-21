(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('PersonMySuffixDialogController', PersonMySuffixDialogController);

    PersonMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Person', 'Location', 'Event'];

    function PersonMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Person, Location, Event) {
        var vm = this;

        vm.person = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.locations = Location.query({filter: 'person-is-null'});
        $q.all([vm.person.$promise, vm.locations.$promise]).then(function() {
            if (!vm.person.locationId) {
                return $q.reject();
            }
            return Location.get({id : vm.person.locationId}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });
        vm.events = Event.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.person.id !== null) {
                Person.update(vm.person, onSaveSuccess, onSaveError);
            } else {
                Person.save(vm.person, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationPlatformApp:personUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
