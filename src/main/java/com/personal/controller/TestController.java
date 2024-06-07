package com.personal.controller;

import com.personal.common.CommonResult;
import com.personal.entry.MtUser;
import com.personal.service.MtUserService;
import com.personal.utils.EasyExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TestController
 * @Description:
 * @author: like
 * @date 2024/6/5 11:34
 */
@Api(tags = "测试类")
@RestController
@RequestMapping("/")
public class TestController {
    @Resource
    private MtUserService mtUserService;
    @Resource
    private EasyExcelUtil easyExcelUtil;

    @GetMapping("test")
    @ApiOperation(value = "test")
    public CommonResult<MtUser> test() {
        MtUser mtUser = new MtUser();
        mtUser.setName("123");
//        mtUserService.save(mtUser);
        mtUserService.test();
        return CommonResult.success(mtUser);
    }

    @ApiOperation(value = "export")
    @GetMapping(value = "export")
    public void export(HttpServletResponse response) {
        MtUser mtUser;
        List<MtUser> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mtUser = new MtUser();
            mtUser.setId(123L + i);
            mtUser.setName("awasda" + i);
            mtUser.setAccount("asdasdafd" + i);
            list.add(mtUser);
        }
        easyExcelUtil.exportExcel(response, list, MtUser.class, "测试123");
    }
}
