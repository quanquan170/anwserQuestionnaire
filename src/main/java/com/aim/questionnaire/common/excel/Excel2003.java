package com.aim.questionnaire.common.excel;

import com.aim.questionnaire.common.constant.Constans;
import com.aim.questionnaire.common.utils.BaseUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;


/**
 *
* @Description: 2003版本-hssf 注意poi版本为3.14
* @Author sunShun
* @Date 2021-1-13 10:08:00
* @Version V1.0
 */
public class Excel2003 extends BaseExcel {

	HSSFWorkbook workbook;
	HSSFSheet sheet;
	File file;

	/**
	 * 打开指定的文件
	 * @param file File文件
	 * @throws IOException
	 */
	@Override
	public void open(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		this.workbook = new HSSFWorkbook(is);
		this.file = file;
		is.close();

	}

	/**
	 * 打开指定的文件
	 * @param paramMultipartFile MultipartFile文件
	 * @throws IOException
	 */
	@Override
	public void open(MultipartFile paramMultipartFile) throws IOException {
		this.workbook = new HSSFWorkbook(paramMultipartFile.getInputStream());
	}

	/**
     * 根据sheet下标选中一个sheet
     * @param paramInt sheet下标
     */
	@Override
	public void selectSheet(int paramInt) {
		int sheetCount = this.workbook.getNumberOfSheets();
		if (paramInt >= sheetCount) {
			this.sheet = null;
		}
		this.sheet = this.workbook.getSheetAt(paramInt);
	}

	/**
     * 根据sheet名称选中一个sheet
     * @param sheetName sheet名称
     */
	@Override
	public void selectSheet(String sheetName) {
		this.sheet = this.workbook.getSheet(sheetName);
	}

	/**
     * 获取当前sheet名称
     * @return sheet名称
     */
	@Override
	public String getSheetName() {
		if (this.sheet == null) {
			return null;
		}
		return this.sheet.getSheetName();
	}

	/**
     * 设置sheet名称
     * @param sheetIndex sheet下标
     * @param sheetName sheet名称
     */
	@Override
	public void setSheetName(int sheetIndex, String sheetName) {
		int sheetCount = this.workbook.getNumberOfSheets();
		if (sheetIndex < sheetCount) {
			this.workbook.setSheetName(sheetIndex, sheetName);
		}
	}

	/**
     * 获取最后一行
     * @return 行数
     */
	@Override
	public int getLastRowNum() {
		if (this.sheet == null) {
			return -1;
		}
		return this.sheet.getLastRowNum();
	}

	/**
     * 获取最后一列
     * @return 列数
     */
	@Override
	public int getLastColNum() {
		if (this.sheet == null) {
			return -1;
		}
		return this.sheet.getRow(0).getPhysicalNumberOfCells();
	}

	/**
     * 插入行操作
     * @param startRowIndex 插入行起始下标
     * @param count 插入的行数
     */
	@Override
	public void insertRow(int startRowIndex, int count) {
		if (count == 0) {
			return;
		}
		this.sheet.shiftRows(startRowIndex, this.sheet.getLastRowNum(), count);
		for (int i = 0; i < count; ++i) {
			this.sheet.createRow(BaseUtil.add(Integer.valueOf(startRowIndex), Integer.valueOf(i)).intValue());
		}
	}

	/**
     * 根据行列获取单元格的string类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @return string类型数据
     */
	@Override
	public String getString(int rowNum, int columnNum) {
		if (this.sheet != null) {
			HSSFRow row = this.sheet.getRow(rowNum);
			if (row != null) {
				HSSFCell cell = row.getCell(columnNum);
				if (cell != null) {
					switch (cell.getCellType()) {
					case 2:
						try {
							return new BigDecimal(String.valueOf(cell.getNumericCellValue())).toString();
						} catch (IllegalStateException e) {
							return String.valueOf(cell.getRichStringCellValue());
						}
					case 4:
						return String.valueOf(cell.getBooleanCellValue());
					case 0:
						if ((HSSFDateUtil.isCellDateFormatted(cell)) || (cell.getCellStyle().getDataFormat() == Constans.EXCEL_DATE_FORMAT_31)
								|| (cell.getCellStyle().getDataFormat() == Constans.EXCEL_DATE_FORMAT_58)
								|| (cell.getCellStyle().getDataFormat() == Constans.EXCEL_DATE_FORMAT_20)
								|| (cell.getCellStyle().getDataFormat() == Constans.EXCEL_DATE_FORMAT_21)
								|| (cell.getCellStyle().getDataFormat() == Constans.EXCEL_DATE_FORMAT_22)
								|| (cell.getCellStyle().getDataFormat() == Constans.EXCEL_DATE_FORMAT_14)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
							return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
						}
						double value = cell.getNumericCellValue();
						int intValue = (int) value;
						return ((value - intValue == 0.0D) ? String.valueOf(intValue)
								: new BigDecimal(String.valueOf(cell.getNumericCellValue())).toPlainString());
					case 1:
					case 3:
					default:
					}
					return cell.getStringCellValue();
				}
			}
		}
		return null;

	}

	/**
     * 根据行列获取单元格的double类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @return double类型数据
     */
	@Override
	public double getDouble(int rowNum, int columnNum) {
		if (this.sheet != null) {
			HSSFRow row = this.sheet.getRow(rowNum);
			if (row != null) {
				HSSFCell cell = row.getCell(columnNum);
				if (cell != null) {
					switch (cell.getCellType()) {
					case 4:
						return ((cell.getBooleanCellValue()) ? 1 : 0);
					case 0:
						return cell.getNumericCellValue();
					case 1:
					case 2:
					case 3:
					default:
					}
				}
			}
		}
		return 0.0D;
	}

	/**
     * 复制单元格样式
     * @param srcRowIndex 目标行
     * @param srcColIndex 目标列
     * @param destRowIndex 复制的行
     * @param destColIndex 复制的列
     */
	@Override
	public void copyCellStyle(int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
		HSSFCell srcCell = this.sheet.getRow(srcRowIndex).getCell(srcColIndex);
		if (srcCell != null) {
			HSSFRow destRow = null;
			HSSFCell destCell = null;
			destRow = this.sheet.getRow(destRowIndex);
			if (destRow == null) {
				destRow = this.sheet.createRow(destRowIndex);
			}
			destCell = destRow.getCell(destColIndex);
			if (destCell == null) {
				destCell = destRow.createCell(destColIndex);
			}
			destCell.setCellStyle(srcCell.getCellStyle());
		}
	}

	/**
     * 写入string类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @param value 值
     */
	@Override
	public void writeString(int rowNum, int columnNum, String value) {
		if (this.sheet != null) {
			HSSFRow row = this.sheet.getRow(rowNum);
			if (row == null) {
				row = this.sheet.createRow(rowNum);
			}
			HSSFCell cell = row.getCell(columnNum);
			if (cell == null) {
				cell = row.createCell(columnNum);
			}
			cell.setCellType(1);
			cell.setCellValue(value);
		}
	}

	/**
     * 写入number类型数据
     * @param rowNum 行
     * @param columnNum 列
     * @param value 值
     */
	@Override
	public void writeNumbic(int rowNum, int columnNum, double value) {
		if (this.sheet != null) {
			HSSFRow row = this.sheet.getRow(rowNum);
			if (row == null) {
				row = this.sheet.createRow(rowNum);
			}
			HSSFCell cell = row.getCell(columnNum);
			if (cell == null) {
				cell = row.createCell(columnNum);
			}
			cell.setCellType(0);
			cell.setCellValue(value);
		}
	}

	/**
     * 写入公式
     * @param rowNum 行
     * @param columnNum 列
     * @param value 公式值
     */
	@Override
	public void writeFormula(int rowNum, int columnNum, String value) {
		if (this.sheet != null) {
			HSSFRow row = this.sheet.getRow(rowNum);
			if (row == null) {
				row = this.sheet.createRow(rowNum);
			}
			HSSFCell cell = row.getCell(columnNum);
			if (cell == null) {
				cell = row.createCell(columnNum);
			}
			cell.setCellType(2);
			cell.setCellFormula(value);
		}
	}

	/**
     * 保存当前打开的excel
     * @throws Exception
     */
	@Override
	public void save() throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(this.file);
			this.workbook.write(fos);
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException localIOException1) {
				}
			}
		}
	}

	/**
     * 判断是够是空行
     * @param rowNum 行
     * @return 是/否
     */
	@Override
	public boolean isNullRow(int rowNum) {
		try {
			HSSFRow row = this.sheet.getRow(rowNum);
			return (row == null);
		} catch (Exception e) {
		}
		return true;
	}

	/**
     * 设置单元格样式
     * @param rowNum 行
     * @param columnNum 列
     */
	@Override
	public void setCellStringType(int rowNum, int columnNum) {
		if (this.sheet != null) {
			HSSFRow row = this.sheet.getRow(rowNum);
			if (row != null) {
				row = this.sheet.createRow(rowNum);
				HSSFCell cell = row.getCell(columnNum);
				if (cell != null) {
					cell.setCellType(1);
				}
			}
		}
	}

	/**
     * 合并单元格
     * @param startRowIndex 起始行
     * @param endRowIndex 结束行
     * @param startCellIndex 起始列
     * @param endCellIndex 结束列
     */
	@Override
	public void mergedRegion(int startRowIndex, int endRowIndex, int startCellIndex, int endCellIndex) {
		CellRangeAddress newCellRangeAddress = new CellRangeAddress(startRowIndex, endRowIndex, startCellIndex,
				endCellIndex);
		this.sheet.addMergedRegion(newCellRangeAddress);
	}

	/**
     * 判断是否是空行
     * @param rowNum 行
     * @return 是/否
     */
	@Override
	public boolean isEmptyRow(int rowNum) {
		try {
			HSSFRow row = this.sheet.getRow(rowNum);
			for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell(i);
				if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return true;
		}
	}
}
