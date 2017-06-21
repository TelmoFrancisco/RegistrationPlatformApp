(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('event-type-my-suffix', {
            parent: 'entity',
            url: '/event-type-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.eventType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-type/event-typesmySuffix.html',
                    controller: 'EventTypeMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eventType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('event-type-my-suffix-detail', {
            parent: 'event-type-my-suffix',
            url: '/event-type-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.eventType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-type/event-type-my-suffix-detail.html',
                    controller: 'EventTypeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eventType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EventType', function($stateParams, EventType) {
                    return EventType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'event-type-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('event-type-my-suffix-detail.edit', {
            parent: 'event-type-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-type/event-type-my-suffix-dialog.html',
                    controller: 'EventTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EventType', function(EventType) {
                            return EventType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-type-my-suffix.new', {
            parent: 'event-type-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-type/event-type-my-suffix-dialog.html',
                    controller: 'EventTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('event-type-my-suffix', null, { reload: 'event-type-my-suffix' });
                }, function() {
                    $state.go('event-type-my-suffix');
                });
            }]
        })
        .state('event-type-my-suffix.edit', {
            parent: 'event-type-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-type/event-type-my-suffix-dialog.html',
                    controller: 'EventTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EventType', function(EventType) {
                            return EventType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-type-my-suffix', null, { reload: 'event-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-type-my-suffix.delete', {
            parent: 'event-type-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-type/event-type-my-suffix-delete-dialog.html',
                    controller: 'EventTypeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EventType', function(EventType) {
                            return EventType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-type-my-suffix', null, { reload: 'event-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
