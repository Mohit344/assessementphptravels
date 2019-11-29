package com.phptravels.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelOperation {
	
	
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	public boolean setCellDataUnique(String filePath,String sheetName, String colName, String uniqueId, String data) {
	try {
	int rowNum = 0;
	fis = new FileInputStream(filePath);
	workbook = new XSSFWorkbook(fis);
	

	int index = workbook.getSheetIndex(sheetName);
	int colNum = -1;
	if (index == -1)
	return false;

	sheet = workbook.getSheetAt(index);
	rowNum = getRowNumber(uniqueId, sheet);
	row = sheet.getRow(0);
	for (int i = 0; i < row.getLastCellNum(); i++) {

	if (row.getCell(i).getStringCellValue().trim().equals(colName))
	colNum = i;
	}
	if (colNum == -1)
	return false;

	sheet.autoSizeColumn(colNum);
	row = sheet.getRow(rowNum);
	if (row == null)
	row = sheet.createRow(rowNum);

	cell = row.getCell(colNum);
	if (cell == null)
	cell = row.createCell(colNum);

	cell.setCellValue(data);

	fileOut = new FileOutputStream(filePath);

	workbook.write(fileOut);

	fileOut.close();

	} catch (Exception e) {
	e.printStackTrace();
	return false;
	}
	return true;
	}

	public int getRowNumber(String uniqueId, XSSFSheet workSheet) throws IOException {

	Iterator<Row> rowIterator = getDataOfRows(workSheet);
	rowIterator.next();
	int rowNumber = 0;
	while (rowIterator.hasNext()) {
	rowNumber++;
	Row row = rowIterator.next();
	Iterator<Cell> cellIterator = row.cellIterator();

	while (cellIterator.hasNext()) {
	Cell cell = cellIterator.next();

	if (cell.toString().equalsIgnoreCase(uniqueId)) {
	return rowNumber;
	}

	}
	}
	return 0;
	}

	public Iterator<Row> getDataOfRows(XSSFSheet workSheet) {
	
	Iterator<Row> rows = workSheet.iterator();
	return rows;
	}
	public boolean setCellDataByRow(String filePath,String sheetName, String colName, int rowNum, String value)
	   {
	       try
	       {
	        fis = new FileInputStream(filePath);
	    workbook = new XSSFWorkbook(fis);
	    fis.close();
	           int col_Num = -1;
	           sheet = workbook.getSheet(sheetName);

	           row = sheet.getRow(0);
	           for (int i = 0; i < row.getLastCellNum(); i++) {
	               if (row.getCell(i).getStringCellValue().trim().equals(colName))
	               {
	                   col_Num = i;
	               }
	           }

	           sheet.autoSizeColumn(col_Num);
	           row = sheet.getRow(rowNum - 1);
	           if(row==null)
	               row = sheet.createRow(rowNum - 1);

	           cell = row.getCell(col_Num);
	           if(cell == null)
	               cell = row.createCell(col_Num);

	           cell.setCellValue(value);

	           fileOut = new FileOutputStream(filePath);
	           workbook.write(fileOut);
	           fileOut.close();
	       }
	       catch (Exception ex)
	       {
	           ex.printStackTrace();
	           return  false;
	       }
	       return true;
	   }


	
}
