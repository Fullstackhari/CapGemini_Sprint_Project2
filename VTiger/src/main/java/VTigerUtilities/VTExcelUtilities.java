package VTigerUtilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class VTExcelUtilities {
	  String excelPath = "./src/test/resources/Vtiger_Test_Data.xlsx";
	    public int getRowCount(String sheetName) throws Exception {
	        FileInputStream file = new FileInputStream(excelPath);
	        Workbook workbook = WorkbookFactory.create(file);
	        int rowCount = workbook.getSheet(sheetName).getPhysicalNumberOfRows();
	        workbook.close();
	        file.close();
	        return rowCount;
	    }

	    public int getColumnCount(String sheetName) throws Exception {
	        FileInputStream file = new FileInputStream(excelPath);
	        Workbook workbook = WorkbookFactory.create(file);
	        int columnCount = workbook.getSheet(sheetName).getRow(0).getPhysicalNumberOfCells();
	        workbook.close();
	        file.close();
	        return columnCount;
	    }

	    public String readDataFromExcel(String sheetName, int row, int cell) throws Exception {
	        FileInputStream file = new FileInputStream(excelPath);
	        Workbook workbook = WorkbookFactory.create(file);
	        Row excelRow = workbook.getSheet(sheetName).getRow(row);

	        if (excelRow == null) {
	            workbook.close();
	            file.close();
	            return "";
	        }

	        Cell excelCell = excelRow.getCell(cell);
	        if (excelCell == null || excelCell.getCellType() == CellType.BLANK) {
	            workbook.close();
	            file.close();
	            return "";
	        }

	        DataFormatter formatter = new DataFormatter();
	        String data = formatter.formatCellValue(excelCell);
	        workbook.close();
	        file.close();
	        return data;
	    }

	    public void setCellDataInExcel(String sheetName, int row, int cell, String value) throws Exception {

	        FileInputStream file = new FileInputStream(excelPath);
	        Workbook workbook = WorkbookFactory.create(file);
	        workbook.getSheet(sheetName).getRow(row).createCell(cell).setCellValue(value);
	        file.close();
	        FileOutputStream output = new FileOutputStream(excelPath);
	        workbook.write(output);
	        workbook.close();
	        output.close();
	    }

}
