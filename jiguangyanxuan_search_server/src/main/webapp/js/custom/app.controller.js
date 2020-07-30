// 定义brandController
angular.module("yanxuan").controller("searchController", function ($scope, searchService) {

    // 发送分页的请求
    $scope.search = function () {

        searchService.get($scope.queryParams)
            .then(
                function (value) {
                    // 当前页显示的商品数据
                    $scope.dataList = value.data.result;
                    $scope.dataList.forEach(
                        function(element){
                            element.picUrl = JSON.parse(element.picUrl);
                        }
                    );
                    //类目信息
                    $scope.categoryList = value.data.category;
                    //品牌信息
                    $scope.brandList = value.data.brandList;
                    //规格信息
                    $scope.specList = value.data.specList;
                }
            );
    };


});