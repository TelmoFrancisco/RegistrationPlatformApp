(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('event-notification-my-suffix', {
            parent: 'entity',
            url: '/event-notification-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.eventNotification.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-notification/event-notificationsmySuffix.html',
                    controller: 'EventNotificationMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eventNotification');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('event-notification-my-suffix-detail', {
            parent: 'event-notification-my-suffix',
            url: '/event-notification-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.eventNotification.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-notification/event-notification-my-suffix-detail.html',
                    controller: 'EventNotificationMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eventNotification');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EventNotification', function($stateParams, EventNotification) {
                    return EventNotification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'event-notification-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('event-notification-my-suffix-detail.edit', {
            parent: 'event-notification-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-notification/event-notification-my-suffix-dialog.html',
                    controller: 'EventNotificationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EventNotification', function(EventNotification) {
                            return EventNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-notification-my-suffix.new', {
            parent: 'event-notification-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-notification/event-notification-my-suffix-dialog.html',
                    controller: 'EventNotificationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('event-notification-my-suffix', null, { reload: 'event-notification-my-suffix' });
                }, function() {
                    $state.go('event-notification-my-suffix');
                });
            }]
        })
        .state('event-notification-my-suffix.edit', {
            parent: 'event-notification-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-notification/event-notification-my-suffix-dialog.html',
                    controller: 'EventNotificationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EventNotification', function(EventNotification) {
                            return EventNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-notification-my-suffix', null, { reload: 'event-notification-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-notification-my-suffix.delete', {
            parent: 'event-notification-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-notification/event-notification-my-suffix-delete-dialog.html',
                    controller: 'EventNotificationMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EventNotification', function(EventNotification) {
                            return EventNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-notification-my-suffix', null, { reload: 'event-notification-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
