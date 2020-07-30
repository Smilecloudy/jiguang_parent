// 定义brandController
angular.module("adType").controller("adTypeController", function ($scope, adTypeService, $controller) {

    // 监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        $scope.pageQuery();
    });

    // 继承其他的controller， baseController
    $controller("baseController", {$scope: $scope});

    $scope.meidaTypeArray = ["", "图片", "文字", "代码"];

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
            name: $scope.name
        };
        // $http.get("../../brand/pageQuery?currentPage="+$scope.pageOption.currentPage+"&pageSize="+$scope.pageOption.pageSize+"&name="+$scope.name)
        // $http.get("../../brand",{params: queryParams})
        adTypeService.get(queryParams)
            .then(
                function (value) {
                    // 总记录数
                    $scope.pageOption.total = value.data.total;
                    // 当前页显示的数据
                    $scope.dataList = value.data.result;
                }
            );
    };


    // 执行保存的方法
    $scope.save = function () {
        var response = null;
        // 判断操作的类型
        if ($scope.entity.id === undefined) {
            // 新增保存
            response = adTypeService.post($scope.entity);
        } else {
            // 修改保存
            response = adTypeService.put($scope.entity)
        }
        // 抽取优化
        response.then(
            function (value) {
                // 关闭模态窗口
                $("#newModal").modal("hide");
                // 刷新品牌列表
                $scope.pageQuery();
            },
            function (reason) {
                console.log(reason)
            }
        )
    };

    // 修改初始化
    $scope.initData = function (entity) {
        $scope.entity = entity;
    }

    $scope.delete = function (name, id) {
        var confirm = confirm("你确定要删除" + name + "吗？");
        if (confirm) {
            // 发送请求删除
            adTypeService.delete(id)
                .then(
                    function (value) {
                        alert(name+"删除成功")
                        // 刷新数据
                        $scope.pageQuery();
                    }
                );
        }

    }
});