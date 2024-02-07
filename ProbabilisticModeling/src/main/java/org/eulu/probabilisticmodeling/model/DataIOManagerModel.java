package org.eulu.probabilisticmodeling.model;

import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataIOManagerModel implements DataIOManager {

    @Override
    public void exportToFile(
            File file,
            ObservableList<String> groupCountLegendStandard,
            ObservableList<String> numbersStandard,
            ObservableList<String> groupCountLegendMidSquare,
            ObservableList<String> numbersMidSquare,
            ObservableList<String> groupCountLegendLinear,
            ObservableList<String> numbersLinear
    ) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Імовірнісне моделювання");
            XSSFRow numbersStandardRow = sheet.createRow(0);
            XSSFRow groupCountLegendStandardRow = sheet.createRow(1);
            XSSFRow numbersMidSquareRow = sheet.createRow(2);
            XSSFRow groupCountLegendMidSquareRow = sheet.createRow(3);
            XSSFRow numbersLinearRow = sheet.createRow(4);
            XSSFRow groupCountLegendLinearRow = sheet.createRow(5);
            for (int i = 0; i < numbersStandard.size(); i++) {
                setCellValues(numbersStandard, numbersStandardRow, i);
                setCellValues(numbersMidSquare, numbersMidSquareRow, i);
                setCellValues(numbersLinear, numbersLinearRow, i);
            }
            for (int i = 0; i < groupCountLegendStandard.size(); i++) {
                setCellValues(groupCountLegendStandard, groupCountLegendStandardRow, i);
                setCellValues(groupCountLegendMidSquare, groupCountLegendMidSquareRow, i);
                setCellValues(groupCountLegendLinear, groupCountLegendLinearRow, i);
            }

            try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                workbook.write(outputStream);
            }
        }
    }

    @Override
    public List<int[]> importFromExcel(File file) throws IOException {
        List<int[]> excelData = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                int[] rowData = new int[row.getLastCellNum()];
                int cellIndex = 0;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        rowData[cellIndex] = (int) cell.getNumericCellValue();
                    } else if (cell.getCellType() == CellType.STRING) {
                        try {
                            rowData[cellIndex] = Integer.parseInt(cell.getStringCellValue());
                        } catch (NumberFormatException e) {
                            throw new NumberFormatException();
                        }
                    }
                    cellIndex++;
                }
                excelData.add(rowData);
            }
        }

        return excelData;
    }

    private void setCellValues(ObservableList<String> data, XSSFRow row, int index) {
        XSSFCell cell = row.createCell(index);
        cell.setCellValue(data.get(index));
    }
}
