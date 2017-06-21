(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-my-suffix', {
            parent: 'entity',
            url: '/person-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.person.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person/peoplemySuffix.html',
                    controller: 'PersonMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('person');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-my-suffix-detail', {
            parent: 'person-my-suffix',
            url: '/person-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.person.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person/person-my-suffix-detail.html',
                    controller: 'PersonMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('person');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Person', function($stateParams, Person) {
                    return Person.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-my-suffix-detail.edit', {
            parent: 'person-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person/person-my-suffix-dialog.html',
                    controller: 'PersonMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Person', function(Person) {
                            return Person.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-my-suffix.new', {
            parent: 'person-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person/person-my-suffix-dialog.html',
                    controller: 'PersonMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                email: null,
                                phoneNumber: null,
                                birthDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-my-suffix', null, { reload: 'person-my-suffix' });
                }, function() {
                    $state.go('person-my-suffix');
                });
            }]
        })
        .state('person-my-suffix.edit', {
            parent: 'person-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person/person-my-suffix-dialog.html',
                    controller: 'PersonMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Person', function(Person) {
                            return Person.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-my-suffix', null, { reload: 'person-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-my-suffix.delete', {
            parent: 'person-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person/person-my-suffix-delete-dialog.html',
                    controller: 'PersonMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Person', function(Person) {
                            return Person.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-my-suffix', null, { reload: 'person-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
