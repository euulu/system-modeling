package org.eulu.probabilisticmodeling.model;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataIOManagerModel implements DataIOManager {

    @Override
    public void exportToFile(
            Window window,
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

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Зберегти згенеровані дані");
            fileChooser.setInitialFileName("Імовірнісне моделювання.xlsx");
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                    workbook.write(outputStream);
                }
            }
        }
    }

    private void setCellValues(ObservableList<String> data, XSSFRow row, int index) {
        XSSFCell cell = row.createCell(index);
        cell.setCellValue(data.get(index));
    }
}
