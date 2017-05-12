(function() {
    'use strict';
    angular
        .module('imageboardApp')
        .factory('Post', Post)
        .factory('PostComments', PostComments);

    Post.$inject = ['$resource', 'DateUtils'];
    PostComments.$inject = ['$resource'];

    function Post ($resource, DateUtils) {
        var resourceUrl =  'api/posts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertLocalDateFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date = DateUtils.convertLocalDateToServer(copy.date);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date = DateUtils.convertLocalDateToServer(copy.date);
                    return angular.toJson(copy);
                }
            }
        });
    }

    function PostComments ($resource) {
        var resourceUrl = 'api/posts/:id/comments';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
