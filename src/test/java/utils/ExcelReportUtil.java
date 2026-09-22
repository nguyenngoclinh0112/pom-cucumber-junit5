package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelReportUtil {
    private static final String EXCEL_PATH = "src/test/resources/data/TestResults.xlsx";
    private static final String HEADER_FEATURE = "Feature";
    private static final String HEADER_TEST_CASE = "Test Case";
    private static final String HEADER_STATUS = "Status";
    private static final String HEADER_NOTE = "Note";

    private static XSSFWorkbook createNewWorkbook() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Test Results");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("No.");
        header.createCell(1).setCellValue(HEADER_FEATURE);
        header.createCell(2).setCellValue(HEADER_TEST_CASE);
        header.createCell(3).setCellValue(HEADER_STATUS);
        header.createCell(4).setCellValue(HEADER_NOTE);

        return workbook;
    }

    private static int findColumnIndex(Row headerRow, String headerName){
        if(headerRow == null){
            return -1;
        }

        for(Cell cell: headerRow){
            if(cell.getStringCellValue().trim().equalsIgnoreCase(headerName)){
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

//    getCellValue => doc gia tri cua 1 cell
    private static String getCellValue(Row row, int columnIndex){
        if (row == null || columnIndex == -1){
            return "";
        }
        Cell cell = row.getCell(columnIndex);
        return cell == null? "" : cell.getStringCellValue().trim();
    }

    private static void setCellValue(Row row, int columnIndex, String value){
        if(columnIndex == -1){
//            file excel ko có cột này => bỏ qua, không làm crash toàn bộ quá trình  update
        return;
        }
        Cell existtingCell = row.getCell(columnIndex);
        if (existtingCell != null) {
            row.removeCell(existtingCell);
        }
        row.createCell(columnIndex).setCellValue(value == null? "" : value);
    }

    private static Row findRowByTestCase(XSSFSheet sheet, int testCaseCol, String testCaseName){
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++){
            Row row = sheet.getRow(rowIndex);
            if(row != null && getCellValue(row, testCaseCol).equals(testCaseName.trim())){
                return row;
            }
        }
        return null;
    }

//    hàm update status testcase trong file excell
//    dùng cơ chế synchronized: đưa các kết quả test case vào hàng đợi
//    cái nào xong trước thì đọc file để cập nhật status trước

    public static synchronized void updateStatus(String featureName, String testCaseName, String status, String note){
//        tạo Object đại diện cho path lưu thông tin file
        File file = new File(EXCEL_PATH);

        try {
            XSSFWorkbook workbook; // biến lưu trữ toàn bộ nội dung file excel trong RAM để thao tác
            if(file.exists()){
                try (FileInputStream input = new FileInputStream(file)){
                    workbook = new XSSFWorkbook(input);
                }
            } else {
//                không tồn tại => tạo file excel mới
                workbook = createNewWorkbook();
            }

            try(workbook){
//                lấy sheet đầu tiên
                XSSFSheet sheet = workbook.getSheetAt(0);
//                dòng 0 luôn là header
                Row headerRow = sheet.getRow(0);
                System.out.println("===== EXCEL HEADER =====");

                for (Cell cell : headerRow) {
                    System.out.println(
                            "Column " + cell.getColumnIndex()
                                    + " = [" + cell.getStringCellValue() + "]"
                    );
                }

                System.out.println("========================");


//                dò xem cột testcase và trạng thái nằm ở vị trí nào trong file excel
//                không tìm thấy, => -1
                int testCaseCol = findColumnIndex(headerRow,HEADER_TEST_CASE);
                int statusCol = findColumnIndex(headerRow,HEADER_STATUS);
                int noteCol = findColumnIndex(headerRow, HEADER_NOTE);
                int featureCol = findColumnIndex(headerRow, HEADER_FEATURE);

                if(testCaseCol == -1 || statusCol == -1){
                    System.out.println("File excel thiếu cột " + HEADER_TEST_CASE + " hoặc " + HEADER_STATUS);
                    return;
                }

//                tìm trong toàn bộ dữ liệu xem có dòng nào cột Test Case trùng khớp CHÍNH XÁC với tên scenario đang chạy hay không
                Row targetRow = findRowByTestCase(sheet, testCaseCol, testCaseName);

//                TH1: nếu không khớp => tạo row mới => thêm thông tin, status vào file excel
                if (targetRow == null){
                    int newRowIndex = sheet.getLastRowNum() + 1;
                    targetRow = sheet.createRow(newRowIndex);
                    setCellValue(targetRow,0,String.valueOf(newRowIndex));
                    setCellValue(targetRow, testCaseCol, testCaseName);
                    setCellValue(targetRow, featureCol, featureName);
                }

//                TH2: nếu khớp => update value của cột trạng thái
                setCellValue(targetRow, statusCol, status);

//                testcase fail => lưu nội dung vào cột ghi chú
                boolean isPassed = status != null && status.equalsIgnoreCase("PASSED");
                setCellValue(targetRow, noteCol, isPassed? "": note);

//                ghi đè lại toàn bộ workbook xuống file excel
                try (FileOutputStream output = new FileOutputStream(file)){
                    workbook.write(output);
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
