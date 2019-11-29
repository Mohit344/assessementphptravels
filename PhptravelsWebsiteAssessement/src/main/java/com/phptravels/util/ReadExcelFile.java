package com.phptravels.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * In this class i'm reading the data from excel by using the keyword.
 * 
 * @author Mohit.Jaiswal
 *
 */
public class ReadExcelFile {

	public String path;
	public FileInputStream fileInputStream = null;
	public FileOutputStream fileOut = null;             
	public XSSFWorkbook workBook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;           
	public XSSFCell cell = null;

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

	/**
	 * Method returns the number of columns on the basis Column name and worksheet
	 *
	 * @param workSheet
	 * @return ColumnNumber-specific column number on the basis of Column name
	 * @throws IOException
	 */
	public int getColumnNumber(String columnName, XSSFSheet workSheet) throws IOException {
		Iterator<Row> rowIterator = getDataOfRows(workSheet);
		int columnNumber = 0;
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				columnNumber++;
				if (cell.toString().equalsIgnoreCase(columnName)) {
					return columnNumber;
				}

			}
		}
		return 0;
	}

	public Iterator<Row> getDataOfRows(XSSFSheet workSheet) {
		// iterating through rows and getting row number
		Iterator<Row> rows = workSheet.iterator();
		return rows;
	}

	public String[][] getRowData(String filepath, int sheetIndex, String uniqueId) throws NullCellValueException {

		int rowNumber = 0;

		try {
			if (sheetIndex < 0)
				throw new NullCellValueException("Sheet index can't be less than zero");
			;
			try {
				fileInputStream = new FileInputStream(new File(filepath));
			} catch (FileNotFoundException e) {
				System.out.println("Sorry! File not Found.");
				e.printStackTrace();
			}
			// Class used to read excel file and read the data
			try {
				workBook = new XSSFWorkbook(fileInputStream);

			} catch (IOException e) {
				System.out.println("File path not found");
				e.printStackTrace();
			}
			sheet = workBook.getSheetAt(sheetIndex);
			rowNumber = getRowNumber(uniqueId, sheet);
			XSSFRow row = sheet.getRow(rowNumber);
			if (row == null)
				throw new NullCellValueException("Cell value is null");
			String rowData[][] = new String[1][getColumnSize(sheet)];
			int j = 0;
			rowNumber = getRowNumber(uniqueId, sheet);
			XSSFRow rowValues = sheet.getRow(rowNumber);
			Iterator<Cell> cellIterator = rowValues.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				rowData[0][j] = cell.toString();
				j++;
			}

			return rowData;

		} catch (Exception e) {

			System.out.println("Cell data not found");
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Method returns the cell data on the basis of sheet index, row and column
	 * numbers
	 *
	 * @param filepath
	 * @param sheetIndex
	 * @param columnName
	 * @param UniqueId-  It is the id which differentiate the rows among them
	 * @return cell data
	 * @throws NullCellValueException
	 */
	public String getCellData(String filePath, String sheetName, String columnName, String uniqueId)
			throws NullCellValueException {
		int rowNumber = 0;
		int colNumber = 0;
		int sheetIndex = 0;
		try {

			if (sheetIndex < 0)
				throw new NullCellValueException("Sheet index can't be less than zero");
			;
			try {
				fileInputStream = new FileInputStream(new File(filePath));
			} catch (FileNotFoundException e) {
				System.out.println("Sorry! File not Found.");
				e.printStackTrace();
			}
			// Class used to read excel file and read the data
			try {
				workBook = new XSSFWorkbook(fileInputStream);
				sheetIndex = workBook.getSheetIndex(sheetName);
				// System.out.println("sheet_index is "+sheetIndex);

			} catch (IOException e) {
				System.out.println("File path not found");
				e.printStackTrace();
			}
			sheet = workBook.getSheetAt(sheetIndex);

			rowNumber = getRowNumber(uniqueId, sheet);
			colNumber = getColumnNumber(columnName, sheet);
			if (rowNumber <= 0)
				throw new NullCellValueException("row no. is less than zero ");
			;
			XSSFRow row = sheet.getRow(rowNumber);
			if (row == null)
				throw new NullCellValueException("Cell value is null");
			if (colNumber <= 0)
				throw new NullCellValueException("column no. is less than zero ");
			XSSFCell cell = row.getCell(colNumber - 1);

			return cell.toString();
		} catch (Exception e) {

			System.out.println("Cell data not found");
			e.printStackTrace();
			return "row " + rowNumber + " or column " + colNumber + " does not exist  in xls";
		}
	}

	public int getColumnSize(XSSFSheet worksheet) {

		Iterator<Row> rowIterator = worksheet.rowIterator();
		int columns = 0;

		if (rowIterator.hasNext()) {
			Row headerRow = rowIterator.next();
			// get the number of cells in the header row
			columns = headerRow.getPhysicalNumberOfCells();
		}

		return columns;

	}


	public int getRowCount(String filePath, String sheetName) {
		try {
			fileInputStream = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workBook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workBook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	public String[] excelDataProviderArray(String path, String sheetName, String columnname) throws IOException {
		int sheetIndex = 0;
		fileInputStream = new FileInputStream(new File(path));
		workBook = new XSSFWorkbook(fileInputStream);
		sheetIndex = workBook.getSheetIndex(sheetName);
		sheet = workBook.getSheetAt(sheetIndex);
		int rowCount = getRowCount(path, sheetName);
		String array[] = new String[rowCount - 1];
		int count = 0, matchcolumn = 0;
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				for (Cell cell : row) {
					if (cell.toString().contentEquals(columnname)) {
						matchcolumn = cell.getColumnIndex();
					}
				}
			} else {
				for (Cell cell : row) {
					if (matchcolumn == cell.getColumnIndex()) {
						String Data = cell.toString();
						array[count] = Data;
					}
				}
				count++;
			}

		}
		return array;
	}

}
