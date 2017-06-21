(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .controller('LocationMySuffixDialogController', LocationMySuffixDialogController);

    LocationMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Location', 'Event'];

    function LocationMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Location, Event) {
        var vm = this;

        vm.location = entity;
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
            if (vm.location.id !== null) {
                Location.update(vm.location, onSaveSuccess, onSaveError);
            } else {
                Location.save(vm.location, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationPlatformApp:locationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
