(function() {
    'use strict';

    angular
        .module('registrationPlatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('event-my-suffix', {
            parent: 'entity',
            url: '/event-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.event.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event/eventsmySuffix.html',
                    controller: 'EventMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('event');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('event-my-suffix-detail', {
            parent: 'event-my-suffix',
            url: '/event-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationPlatformApp.event.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event/event-my-suffix-detail.html',
                    controller: 'EventMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('event');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Event', function($stateParams, Event) {
                    return Event.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'event-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('event-my-suffix-detail.edit', {
            parent: 'event-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event/event-my-suffix-dialog.html',
                    controller: 'EventMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Event', function(Event) {
                            return Event.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-my-suffix.new', {
            parent: 'event-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event/event-my-suffix-dialog.html',
                    controller: 'EventMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                date: null,
                                initDate: null,
                                endDate: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('event-my-suffix', null, { reload: 'event-my-suffix' });
                }, function() {
                    $state.go('event-my-suffix');
                });
            }]
        })
        .state('event-my-suffix.edit', {
            parent: 'event-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event/event-my-suffix-dialog.html',
                    controller: 'EventMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Event', function(Event) {
                            return Event.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-my-suffix', null, { reload: 'event-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-my-suffix.delete', {
            parent: 'event-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event/event-my-suffix-delete-dialog.html',
                    controller: 'EventMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Event', function(Event) {
                            return Event.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-my-suffix', null, { reload: 'event-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
