// 定义brandController
angular.module("seller").controller("sellerController", function ($scope, $controller, sellerService) {

    // 监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        $scope.pageQuery();
    });

    //继承总的controller
$controller("baseController",{$scope: $scope});


    // 发送分页的请求
    $scope.pageQuery = function () {
        // 页面初始化时$scope.name == undefined
        if ($scope.name === undefined) {
            $scope.name = "";
        }

        // 定义查询参数
        var queryParams = {
            currentPage: $scope.pageOption.currentPage,
            pageSize: $scope.pageOption.pageSize,
            name: $scope.name,
            status: "0"
        };

        sellerService.get(queryParams).then(
            function (value) {
                // 总记录数
                $scope.pageOption.total = value.data.total;
                // 当前页显示的数据
                $scope.sellerList = value.data.result;
            }
        );


    };

    $scope.initEntity = function (seller) {
        $scope.entity = seller;
    };

    $scope.updateStatus = function (id, status) {
        var condition = {id:id,status:status};
        sellerService.put(condition).then(function (value) {
            alert("修改成功");
            $("#newModal").modal("hide");
            $scope.pageQuery();
        })
    };



});
