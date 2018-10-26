package com.mebay.bean;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyh on 2018/10/19.
 */
@ApiModel("分页对象")
public class PageView<T> {
    private static final long serialVersionUID = -4426958360243585882L;

    @ApiModelProperty(value = "当前页号")
    private int pageNum;

    @ApiModelProperty(value = "每页的数量")
    private int pageSize;

    @ApiModelProperty(value = "总记录数")
    private long total;

    @ApiModelProperty(value = "总页数")
    private int pages;

    @ApiModelProperty(value = "结果集")
    private List<T> list;

    public PageView(PageQuery pageQuery) {
        this.pageNum = pageQuery.getPageNum();
        this.pageSize = pageQuery.getPageSize();
    }

    public PageView(){}

    public PageView(List<T> poList) {
        BeanUtils.copyProperties(new PageInfo<>(poList), this);
    }

    public static <T> PageView<T> build(List<T> poList) {
        return new PageView<>(poList);
    }

    /**
     * @desc 构建一个分页VO对象
     *
     * @param page 数据库查出来的分页数据列表
     */
    public static <T> PageView<T> build(Page<T> page) {
        PageView<T> pageVO = new PageView<>();
        BeanUtils.copyProperties(page.toPageInfo(), pageVO);
        return pageVO;
    }

    /**
     * @desc 构建一个分页VO对象
     * 试用场景为：从数据库取出的PO列表不做任何处理，转化为VO列表返回
     *
     * @param page 数据库查出来的分页数据列表
     * @param voClazz 要转为的VO类
     */
    public static <T, E> PageView<T> build(Page<E> page, Class<T> voClazz) {

        PageView<T> pageVO = new PageView<>();
        BeanUtils.copyProperties(page, pageVO, "list");

        try {
            List<T> VOs = new ArrayList<>();
            List<E> POs = page.getResult();
            if (!CollectionUtils.isEmpty(POs)) {
                for (E e : POs) {
                    T t = voClazz.newInstance();
                    BeanUtils.copyProperties(e, t);
                    VOs.add(t);
                }
            }
            pageVO.setList(VOs);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return pageVO;
    }

    /**
     * @desc 构建一个分页VO对象
     * 试用场景为：将处理好的VO列表封装返回
     *
     * @param poPage 数据库查出来的分页数据
     * @param voList vo数据列表
     */
    public static <T, E> PageView<T> build(Page<E> poPage, List<T> voList) {
        PageView<T> page = new PageView<>();
        BeanUtils.copyProperties(poPage, page, "list");
        page.setList(voList == null ? new ArrayList<>() : voList);
        return page;
    }

    public static int getPages(long total, int pageSize) {
        if (total == 0 || pageSize == 0) {
            return 0;
        }
        return (int) (total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1));
    }

    public int getPages(){
        return getPages(this.total, this.pageSize);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
