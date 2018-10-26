package com.mebay.bean;

import com.github.pagehelper.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by gyh on 2018/10/19.
 */
@ApiModel("分页查询对象")
public class PageQuery implements IPage {
    private static final Logger loggger = Logger.getLogger(PageQuery.class.getSimpleName());
    /**
     * 按id递增排序
     */
    public static final String ORDER_BY_CREATE_TIME_DESC = "id ASC";

    private static final Map<String, String> SearchType = new HashMap<>();
    private static final String[][] nmka = {
            {"cn", "like", "%%%s%%"}, {"eq", "=", "%s"},
            {"nc", "not like", "%%%s%%"}, {"ne", "<>", "%s"},
            {"gt", ">", "%s"}, {"lt", "<", "%s"},
            {"bw", "like", "%s%%"}, {"bn", "not like", "%s%%"},
            {"ew", "like", "%%%s"}, {"en", "not like", "%%%s"}
    };
    @ApiModelProperty(value = "查找字段", example = "name")
    private String searchField;
    @ApiModelProperty(value = "查找方式", allowableValues = "[['cn', '包含'], ['eq', '等于'], ['nc', '不包含'], ['ne', '不等于'], ['gt', '大于'], ['lt', '小于'], ['bw', '开始于'], ['bn', '不开始于'], ['ew', '结束于'], ['en', '不结束于']]")
    private String searchOper;
    @ApiModelProperty(value = "查找的字符", example = "张")
    private String searchString;

    @ApiModelProperty(value = "当前页号")
    @Range(min = 1, max = Integer.MAX_VALUE)
    private int pageNum = 1;

    @ApiModelProperty(value = "一页数量")
    @Range(min = 1, max = Integer.MAX_VALUE)
    private int pageSize = 30;

    @ApiModelProperty(value = "排序", notes = "例：create_time desc,update_time ASC")
    private String orderBy;

    public PageQuery(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageQuery() {
    }

    @ApiModelProperty(hidden = true)
    public int getOffset() {
        return (this.pageNum - 1) * this.pageSize;
    }

    public String buildSubSql() {
        if (StringUtils.isEmpty(searchField) && StringUtils.isEmpty(searchOper) && StringUtils.isEmpty(searchString)) {
            return null;
        }
        int index = -1;
        for (int i = 0; i < nmka.length; i++) { //判断指令是否在允许范围内
            if (nmka[i][0].equals(searchOper)) {
                index = i;
                break;
            }
        }
        if (index == -1) {  //不存在就返回null
            return null;
        }
        String subsql = " " + searchField + " " + nmka[index][1] + " '" + String.format(nmka[index][2], searchString) + "'";
        loggger.info(subsql);
        return subsql;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
