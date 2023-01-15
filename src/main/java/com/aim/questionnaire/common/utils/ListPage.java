package com.aim.questionnaire.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ListPage<E> {
    /**
     * 当前页面
     */
    private int pageNum = 1;

    /**
     * 显示多少行
     */
    private int pageSize = 10;

    /**
     * 总记录条数
     */
    private int total;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 数据
     */
    private List<E> data;
    /**
     * 数据
     */
    private List<Long> elements;

    private Object object;

    public ListPage(List<E> list, int total, int pageNum, int pageSize){
        this.pageNum=pageNum;
        this.pageSize=pageSize;
        this.total=total;
        this.data=list;
        if (this.total % this.pageSize == 0) {
            this.pages = this.total / this.pageSize;
        } else {
            this.pages = this.total / this.pageSize + 1;
        }
    }
    /**
     * 对list集合进行分页处理
     *
     */
    public ListPage(List<E> list, int pageNum, int pageSize) {
        this.pageNum=pageNum;
        this.pageSize=pageSize;
        this.total=list.size();// 记录总数

        this.pages = 0; // 页数

        if(list.size()==0){
            this.data= list;
            return ;
        }
        if (this.total % this.pageSize == 0) {
            this.pages = this.total / this.pageSize;
        } else {
            this.pages = this.total / this.pageSize + 1;
        }
        if(this.pages<this.pageNum){
            this.data=list.subList(this.pageSize*(this.pages-1), (Math.min((this.pageSize * this.pages), this.total)));
        }else{
            this.data=list.subList(this.pageSize*(this.pageNum-1), (Math.min((this.pageSize * this.pageNum), this.total)));
        }
    }
}
