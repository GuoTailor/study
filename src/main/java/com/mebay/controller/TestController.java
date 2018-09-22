package com.mebay.controller;

import com.mebay.bean.Curriculum;
import com.mebay.mapper.CurriculumMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="/test", tags="测试接口模块,该模块访问无需权限")
@RestController
@RequestMapping("/test")
public class TestController {
    private final CurriculumMapper curriculumMapper;

    public TestController(CurriculumMapper curriculumMapper) {
        this.curriculumMapper = curriculumMapper;
    }

    @GetMapping("/curriculum")
    @ApiOperation(value="返回全部课程", notes="test: 没有就是空“”")
    public List<Curriculum> getAllByCurriculum(){
        return curriculumMapper.getAll();
    }

    @GetMapping("/curriculum/{title}")
    @ApiOperation(value="根据课程名返回课程章节", notes="test: 返回内容包含课程信息")
    @ApiImplicitParam(paramType = "path", name = "title", value = "课程名", required = true, dataType = "String")
    public Curriculum getAllCurriculum(@PathVariable(value = "title") String title){
        return curriculumMapper.getCurriculumByTitle(title);
    }

}
