// 定义brandController
angular.module("yanxuan").controller("searchController", function ($scope, searchService) {

    // 发送分页的请求
    $scope.search = function () {

        searchService.get($scope.queryParams)
            .then(
                function (value) {
                    // 当前页显示的数据
                    $scope.dataList = value.data.result;
                    $scope.dataList.forEach(
                        function(element){
                            element.picUrl = JSON.parse(element.picUrl);
                        }
                    );
                }
            );
    };


});