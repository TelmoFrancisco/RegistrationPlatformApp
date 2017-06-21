(function() {
    'use strict';
    angular
        .module('registrationPlatformApp')
        .factory('EventNotification', EventNotification);

    EventNotification.$inject = ['$resource'];

    function EventNotification ($resource) {
        var resourceUrl =  'api/event-notifications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
