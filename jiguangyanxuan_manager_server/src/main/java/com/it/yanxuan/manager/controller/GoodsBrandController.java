package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsBrandService;
import com.it.yanxuan.model.GoodsBrand;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/23 13:57
 */

//@Controller
@RestController//整合@Controller和@ResponseBody
@RequestMapping("/brand")
public class GoodsBrandController {
    @Reference
    private IGoodsBrandService goodsBrandService;

    //------------------restful风格---------------------------------------------------------------------------------

    //通过id查询品牌信息
    @GetMapping("/{id}")
    public ResponseEntity<GoodsBrand> queryById(@PathVariable("id") Long id) {
        GoodsBrand goodsBrand = goodsBrandService.queryById(id);
        //向客户端相应查询得到的数据和状态码
        return new ResponseEntity<>(goodsBrand, HttpStatus.OK);
    }

    //分页查询品牌信息
    @GetMapping
    public ResponseEntity<PageResult<GoodsBrand>> query(Integer currentPage, Integer pageSize, GoodsBrand goodsBrand) {
        if (currentPage == null || pageSize == null) {
            currentPage = 1;
            pageSize = Integer.MAX_VALUE;
        }

        PageResult<GoodsBrand> pageResult = goodsBrandService.pageQuery(currentPage, pageSize, goodsBrand);
        //System.out.println("pageResult = " + pageResult.toString());
        return new ResponseEntity<PageResult<GoodsBrand>>(pageResult, HttpStatus.OK);
    }

    //保存品牌信息
    @PostMapping
    public ResponseEntity save(@RequestBody GoodsBrand goodsBrand) {
        int result = goodsBrandService.save(goodsBrand);
        if (result > 0) {
            //如果保存成功，返回
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //修改品牌信息
    @PutMapping
    public ResponseEntity update(@RequestBody GoodsBrand goodsBrand) {
        int result = goodsBrandService.update(goodsBrand);
        if (result > 0) {
            //如果保存成功，返回
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //通过id停用品牌信息
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        int result = goodsBrandService.deleteByBrandId(id);
        if (result > 0) {
            //如果保存成功，返回
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------------------restful风格---------------------------------------------------------------------------------


    //查询所有商品品牌
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
//    @ResponseBody
    public List<GoodsBrand> queryAll() {
        //调用远程服务(com.it.yanxuan.goods.service.GoodsBrandServiceImpl)完成品牌信息的查询
        List<GoodsBrand> goodsBrandList = goodsBrandService.queryAll();

        return goodsBrandList;
    }

    //根据分页参数进行分页查询
    @RequestMapping("/pageQuery")
    public PageResult<GoodsBrand> pageQuery(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                            GoodsBrand goodsBrand) {
        //调用远程服务完成分页查询
        PageResult<GoodsBrand> pageResult = goodsBrandService.pageQuery(currentPage, pageSize, goodsBrand);
        return pageResult;
    }

    //将新增的品牌信息保存到数据库
    @RequestMapping("/save")
    public Result saveBrand(@RequestBody GoodsBrand goodsBrand) {
        Result result = new Result();
        try {
            goodsBrandService.save(goodsBrand);
            //保存成功
            result.setCode(true);
            result.setMessage("保存成功");
        } catch (Exception e) {
            //保存失败
            result.setCode(false);
            result.setMessage("保存失败");
        }
        return result;
    }

    //将修改的品牌信息保存到数据库
    @RequestMapping("/update")
    public Result updateBrand(@RequestBody GoodsBrand goodsBrand) {
        Result result = new Result();
        try {
            goodsBrandService.update(goodsBrand);
            //保存成功
            result.setCode(true);
            result.setMessage("保存成功");
        } catch (Exception e) {
            //保存失败
            result.setCode(false);
            result.setMessage("保存失败");
        }
        return result;
    }


    //根据品牌的id从数据库删除品牌信息
    @RequestMapping("/delete")
    public Result deleteByBrandId(Long id) {
        Result result = new Result();
        try {
            goodsBrandService.deleteByBrandId(id);
            result.setCode(true);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setCode(false);
            result.setMessage("删除失败");
        }
        return result;
    }
}
