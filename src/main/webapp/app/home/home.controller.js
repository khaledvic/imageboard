(function() {
    'use strict';

    angular
        .module('imageboardApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'Post', 'PostComments','Comment', '$state'];

    function HomeController ($scope, Principal, LoginService, Post, PostComments,Comment, $state) {
        var vm = this;
        vm.comment={}
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        vm.posts = [];

        loadAll();

        function loadAll() {
            Post.query(function(result) {
                vm.posts = result;
                vm.searchQuery = null;
            });
        }

        function removeUser(array) {
            if (array !== undefined) {
                return array.filter(function (user) {
                    return user.id !== vm.account.id;
                });
            }
        }

        function containsUser(array) {
            return array !== undefined && array.filter(function (user) {
                return user.id === vm.account.id;
            }).length > 0
        }

        $scope.setupPost = function (post) {
            if (post.score === undefined || post.score === null) {
                post.score = 0;
            }
            if (post.comments === null || post.comments === undefined) {
                post.comments = PostComments.query({'id': post.id});
            }
            if (post.showComments === undefined) {
                post.showComments = false;
            }
        }

        $scope.isUpvoter = function (post) {
            return containsUser(post.upvoters);
        }

        $scope.isDownvoter = function (post) {
            return containsUser(post.downvoters);
        }

        $scope.upvote = function (post) {
            if (containsUser(post.upvoters)) {
                post.upvoters = removeUser(post.upvoters);
                post.score = post.score - 1;
            } else {
                post.upvoters.push(vm.account);
                post.downvoters = removeUser(post.downvoters);
                post.score = post.score + 1;
            }
            Post.update(post);
        }

        $scope.downvote = function (post) {
            if (containsUser(post.downvoters)) {
                post.downvoters = removeUser(post.downvoters);
                post.score = post.score + 1;
            } else {
                post.downvoters.push(vm.account);
                post.upvoters = removeUser(post.upvoters);
                post.score = post.score - 1;
            }
            Post.update(post);
        }

        $scope.toggleComments = function (post) {
            post.showComments = !post.showComments;
        }

        $scope.addComment=    function(post) {
            var pid=post.id;
            console.log("pid:"+pid);
            vm.isSaving = true;
            vm.comment[pid].date= new Date().toISOString().split("T")[0];
            vm.comment[pid].score=0;
            vm.comment[pid].post=post;
            vm.comment[pid].author=vm.account;
            Comment.save(vm.comment[pid], onSaveSuccess, onSaveError);
            
             
        }

        function onSaveSuccess (result) {
            vm.comment[result.post.id]=null;
            //console.log(result)
            $scope.$emit('imageboardApp:commentUpdate', result);
            console.log(vm.posts);
            loadAll();
            vm.isSaving = false;
        }

        function onSaveError (err) {
            console.log(err);
            vm.isSaving = false;
        }
    }
})();
