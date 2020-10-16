package cn.fllday.learn.auth.web;

import cn.fllday.learn.auth.service.DictService;
import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.pojo.user.SysDict;
import cn.fllday.learn.pojo.user.SysDictItem;
import cn.fllday.learn.pojo.user.dto.SysDictDTO;
import cn.fllday.learn.pojo.user.dto.SysDictItemDTO;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: gssznb
 */
@RestController
@RequestMapping(value = "dict")
@Validated
public class SysDictController {


    @Autowired
    private DictService dictService;

    @PostMapping(value = "")
    public AjaxResult addDict(@Validated SysDictDTO dto) {
        dictService.addDict(dto);
        return AjaxResult.success();
    }

    @PostMapping(value = "item")
    public AjaxResult addDictItem(SysDictItemDTO dto) {
        dictService.addDictItem(dto);
        return AjaxResult.success();
    }

    @GetMapping(value = "")
    public AjaxResult getDictPage(SysDictDTO dto) {
        PageInfo<SysDict> dictPage = dictService.getDictList(dto);
        return AjaxResult.success(dictPage);
    }

    @GetMapping(value = "/item/{dictId}")
    public AjaxResult getDictItemList(@PathVariable(value = "dictId") @NotNull(message = "字典id不能为空") String dictId){
        List<JSONObject> dictItems = dictService.getDictItemById(dictId);
        return AjaxResult.success(dictItems);
    }
}
