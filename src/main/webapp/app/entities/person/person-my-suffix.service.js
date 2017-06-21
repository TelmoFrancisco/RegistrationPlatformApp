(function() {
    'use strict';
    angular
        .module('registrationPlatformApp')
        .factory('Person', Person);

    Person.$inject = ['$resource', 'DateUtils'];

    function Person ($resource, DateUtils) {
        var resourceUrl =  'api/people/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthDate = DateUtils.convertDateTimeFromServer(data.birthDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
