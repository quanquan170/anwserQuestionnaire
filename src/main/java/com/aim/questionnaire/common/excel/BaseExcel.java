package com.aim.questionnaire.common.excel;

import com.aim.questionnaire.common.constant.Constans;
import com.aim.questionnaire.common.utils.BaseUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


/**
 * @Description: excel共通类
 * @Author sunShun
 * @Date 2021-1-13 10:07:43
 * @Version V1.0
 */
public abstract class BaseExcel {

	/**
	 * BaseExcel构造方法
	 * @param file 文件对象
	 * @return BaseExcel对象
	 */
    public static BaseExcel newInstrance(File file) throws IOException, IllegalArgumentException {
        BaseExcel baseExcel;
        if (file == null) {
            throw new IllegalAccessError("请指定需要打开的文件");
        }
        String extentionName = BaseUtil.getExtensionName(file.getAbsolutePath());
        if (Constans.EXCEL_2003_SUFFIX.equalsIgnoreCase(extentionName)) {
            baseExcel = new Excel2003();
            baseExcel.open(file);
            return baseExcel;
        }
        if (Constans.EXCEL_2007_SUFFIX.equalsIgnoreCase(extentionName)) {
            baseExcel = new Excel2007();
            baseExcel.open(file);
            return baseExcel;
        }
        throw new IllegalAccessError("文件扩展名不正确");

    }

    public static BaseExcel newInstrance(MultipartFile multipartFile) throws IOException,
            IllegalArgumentException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//        String filePath = file.getAbsolutePath();
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        return newInstrance(file);
    }

    /**
     * 打开指定的文件
     * @param paramFile 文件
     */
    public abstract void open(File paramFile) throws IOException;

    /**
     * 打开指定的文件
     * @param paramMultipartFile 文件
     */
    public abstract void open(MultipartFile paramMultipartFile) throws IOException;

    /**
     * 根据sheet下标选中一个sheet
     * @param paramInt sheet下标
     */
    public abstract void selectSheet(int paramInt);

    /**
     * 根据sheet名称选中一个sheet
     * @param sheetName sheet名称
     */
    public abstract void selectSheet(String sheetName);

    /**
     * 获取当前sheet名称
     * @return sheet名称
     */
    public abstract String getSheetName();

    /**
     * 设置sheet名称
     * @param sheetIndex sheet下标
     * @param sheetName sheet名称
     */
    public abstract void setSheetName(int sheetIndex, String sheetName);

    /**
     * 获取最后一行
     * @return 行数
     */
    public abstract int getLastRowNum();

    /**
     * 获取最后一列
     * @return 列数
     */
    public abstract int getLastColNum();

    /**
     * 插入行操作
     * @param startRowIndex 插入行起始下标
     * @param count 插入的行数
     */
    public abstract void insertRow(int startRowIndex, int count);

    /**
     * 根据行列获取单元格的string类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @return string类型数据
     */
    public abstract String getString(int rowNum, int columnNum);

    /**
     * 根据行列获取单元格的double类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @return double类型数据
     */
    public abstract double getDouble(int rowNum, int columnNum);

    /**
     * 复制单元格样式
     * @param srcRowIndex 目标行
     * @param srcColIndex 目标列
     * @param destRowIndex 复制的行
     * @param destColIndex 复制的列
     */
    public abstract void copyCellStyle(int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex);

    /**
     * 写入string类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @param value 值
     */
    public abstract void writeString(int rowNum, int columnNum, String value);

    /**
     * 写入number类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @param value 值
     */
    public abstract void writeNumbic(int rowNum, int columnNum, double value);

    /**
     * 写入公式
     * @param rowNum 行
     * @param columnNum 列
     * @param value 公式值
     */
    public abstract void writeFormula(int rowNum, int columnNum, String value);

    /**
     * 保存当前打开的excel
     */
    public abstract void save() throws Exception;

    /**
     * 判断是够是空行
     * @param rowNum 行
     * @return 是/否
     */
    public abstract boolean isNullRow(int rowNum);

    /**
     * 设置单元格样式
     * @param rowNum 行
     * @param columnNum 列
     */
    public abstract void setCellStringType(int rowNum, int columnNum);

    /**
     * 合并单元格
     * @param startRowIndex 起始行
     * @param endRowIndex 结束行
     * @param startCellIndex 起始列
     * @param endCellIndex 结束列
     */
    public abstract void mergedRegion(int startRowIndex, int endRowIndex, int startCellIndex, int endCellIndex);

    /**
     * 判断是否是空行
     * @param rowNum 行
     * @return 是/否
     */
    public abstract boolean isEmptyRow(int rowNum);
}
