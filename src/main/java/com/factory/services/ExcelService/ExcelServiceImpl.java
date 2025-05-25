package com.factory.services.ExcelService;

import com.factory.projections.ReportSewingWorkers;
import com.factory.repository.SewingRepo;
import com.factory.repository.ToolRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final ToolRepo toolRepo;
    private final SewingRepo sewingRepo;


    @Override
    public HttpEntity<?> downloadWorkerExcel(HttpServletResponse response, LocalDate date) throws IOException {
        // Set response type and headers
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=Hisobot_Workers.xls");
        // Retrieve worker data
        List<ReportSewingWorkers> workers = sewingRepo.getReportSewingWorkersForExcel(date, 0, "");

        // Create workbook and sheet
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Workers' Report");

        // Define styles for headers, numbers, and totals
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFCellStyle numberStyle = workbook.createCellStyle();
        HSSFDataFormat format = workbook.createDataFormat();
        numberStyle.setDataFormat(format.getFormat("#,##0.00"));

        HSSFCellStyle totalStyle = workbook.createCellStyle();
        HSSFFont totalFont = workbook.createFont();
        totalFont.setBold(true);
        totalFont.setFontHeightInPoints((short) 12);
        totalStyle.setFont(totalFont);
        totalStyle.setDataFormat(format.getFormat("#,##0.00"));
        totalStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Create header row
        HSSFRow headerRow = sheet.createRow(0);
        String[] headers = {
                "#", "Ism", "Familiya", "Chip soni", "Chip narxi",
                "Chistka soni", "Chistka narxi", "Tugma ochish soni", "Tugma ochish narxi",
                "Ip ochish soni", "Ip ochish narxi", "Ko'k etiketka", "Ko'k etiketka narxi",
                "Sariq chip", "Sariq chip narxi", "Tikish narxi", "Tikish perimetri",
                "Tikish narxi", "Tayyor mahsulot soni", "Qirqish perimetri", "Qirqish narxi",
                "Karobka soni", "Karobka narxi", "Karobka perimetri", "Plank narxi", "Upakovka narxi",
                "Total" // Add "Total" column
        };

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 4000);
        }

        int dataRowIndex = 1;

        // Initialize total accumulators for vertical sums
        double[] totals = new double[headers.length];
        totals[0] = Double.NaN; // Ignored for index column
        totals[1] = Double.NaN; // Ignored for "First Name"
        totals[2] = Double.NaN; // Ignored for "Last Name"

        // Process each worker and fill in data
        for (ReportSewingWorkers worker : workers) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);

            // Set worker data and handle null values with default 0
            dataRow.createCell(0).setCellValue(dataRowIndex); // Index
            dataRow.createCell(1).setCellValue(worker.getFirstName() != null ? worker.getFirstName() : "");
            dataRow.createCell(2).setCellValue(worker.getLastName() != null ? worker.getLastName() : "");

            // Chip Count and Price
            double chipCount = worker.getChipCount() != null ? worker.getChipCount().doubleValue() : 0.0;
            double chipPrice = worker.getChipPrice() != null ? worker.getChipPrice().doubleValue() : 0.0;
            dataRow.createCell(3).setCellValue(chipCount);
            dataRow.createCell(4).setCellValue(chipPrice);
            totals[3] += chipCount;
            totals[4] += chipPrice;

            // Cleaning Count and Price
            double cleaningCount = worker.getCleaningCount() != null ? worker.getCleaningCount().doubleValue() : 0.0;
            double cleaningPrice = worker.getCleaningPrice() != null ? worker.getCleaningPrice().doubleValue() : 0.0;
            dataRow.createCell(5).setCellValue(cleaningCount);
            dataRow.createCell(6).setCellValue(cleaningPrice);
            totals[5] += cleaningCount;
            totals[6] += cleaningPrice;

            // Button Open Count and Price
            double buttonOpenCount = worker.getButtonOpenCount() != null ? worker.getButtonOpenCount().doubleValue() : 0.0;
            double buttonOpenPrice = worker.getButtonOpenPrice() != null ? worker.getButtonOpenPrice().doubleValue() : 0.0;
            dataRow.createCell(7).setCellValue(buttonOpenCount);
            dataRow.createCell(8).setCellValue(buttonOpenPrice);
            totals[7] += buttonOpenCount;
            totals[8] += buttonOpenPrice;

            // Yarn Open Count and Price
            double yarnOpenCount = worker.getYarnOpenCount() != null ? worker.getYarnOpenCount().doubleValue() : 0.0;
            double yarnOpenPrice = worker.getYarnOpenPrice() != null ? worker.getYarnOpenPrice().doubleValue() : 0.0;
            dataRow.createCell(9).setCellValue(yarnOpenCount);
            dataRow.createCell(10).setCellValue(yarnOpenPrice);
            totals[9] += yarnOpenCount;
            totals[10] += yarnOpenPrice;

            // Blue Label and Price
            double blueLabel = worker.getBlueLabel() != null ? worker.getBlueLabel().doubleValue() : 0.0;
            double blueLabelPrice = worker.getBlueLabelPrice() != null ? worker.getBlueLabelPrice().doubleValue() : 0.0;
            dataRow.createCell(11).setCellValue(blueLabel);
            dataRow.createCell(12).setCellValue(blueLabelPrice);
            totals[11] += blueLabel;
            totals[12] += blueLabelPrice;

            // Yellow Chip and Price
            double yellowChip = worker.getYellowChip() != null ? worker.getYellowChip().doubleValue() : 0.0;
            double yellowChipPrice = worker.getYellowChipPrice() != null ? worker.getYellowChipPrice().doubleValue() : 0.0;
            dataRow.createCell(13).setCellValue(yellowChip);
            dataRow.createCell(14).setCellValue(yellowChipPrice);
            totals[13] += yellowChip;
            totals[14] += yellowChipPrice;

            // Sewing Count, Perimeter, and Price
            double sewingCount = worker.getCount() != null ? worker.getCount().doubleValue() : 0.0;
            double sewingPerimeter = worker.getTotalPerimeter() != null ? worker.getTotalPerimeter().doubleValue() : 0.0;
            double sewingPrice = worker.getSewingPrice() != null ? worker.getSewingPrice().doubleValue() : 0.0;
            dataRow.createCell(15).setCellValue(sewingCount);
            dataRow.createCell(16).setCellValue(sewingPerimeter);
            dataRow.createCell(17).setCellValue(sewingPrice);
            totals[15] += sewingCount;
            totals[16] += sewingPerimeter;
            totals[17] += sewingPrice;

            // Ready Prod Count, Cutting Perimeter, and Cutting Price
            double readyProdCount = worker.getReadyProdCount() != null ? worker.getReadyProdCount().doubleValue() : 0.0;
            double cuttingPerimeter = worker.getTotalPerimeterCutting() != null ? worker.getTotalPerimeterCutting().doubleValue() : 0.0;
            double cuttingPrice = worker.getCuttingPrice() != null ? worker.getCuttingPrice().doubleValue() : 0.0;
            dataRow.createCell(18).setCellValue(readyProdCount);
            dataRow.createCell(19).setCellValue(cuttingPerimeter);
            dataRow.createCell(20).setCellValue(cuttingPrice);
            totals[18] += readyProdCount;
            totals[19] += cuttingPerimeter;
            totals[20] += cuttingPrice;

            // Box Count, Price, and Perimeter
            double boxCount = worker.getBoxCount() != null ? worker.getBoxCount().doubleValue() : 0.0;
            double boxPrice = worker.getBoxPrice() != null ? worker.getBoxPrice().doubleValue() : 0.0;
            double boxPerimeter = worker.getBoxCountTotal() != null ? worker.getBoxCountTotal().doubleValue() : 0.0;
            dataRow.createCell(21).setCellValue(boxCount);
            dataRow.createCell(22).setCellValue(boxPrice);
            dataRow.createCell(23).setCellValue(boxPerimeter);
            totals[21] += boxCount;
            totals[22] += boxPrice;
            totals[23] += boxPerimeter;

            // Plank and Make Pack Prices
            double plankPrice = worker.getPlankPrice() != null ? worker.getPlankPrice().doubleValue() : 0.0;
            double makePackPrice = worker.getMakePackPrice() != null ? worker.getMakePackPrice().doubleValue() : 0.0;
            dataRow.createCell(24).setCellValue(plankPrice);
            dataRow.createCell(25).setCellValue(makePackPrice);
            totals[24] += plankPrice;
            totals[25] += makePackPrice;

            // Calculate total for the worker and set in the "Total" column
            double totalForWorker = chipCount + chipPrice + cleaningCount + cleaningPrice + buttonOpenCount
                    + buttonOpenPrice + yarnOpenCount + yarnOpenPrice + blueLabel + blueLabelPrice
                    + yellowChip + yellowChipPrice + sewingCount + sewingPerimeter + sewingPrice
                    + readyProdCount + cuttingPerimeter + cuttingPrice + boxCount + boxPrice
                    + boxPerimeter + plankPrice + makePackPrice;

            dataRow.createCell(26).setCellValue(totalForWorker); // Set the "Total" value for the worker

            totals[26] += totalForWorker; // Update total column sum
            dataRowIndex++;
        }

        // Create totals row (Vertical totals)
        HSSFRow totalRow = sheet.createRow(dataRowIndex);
        totalRow.createCell(0).setCellValue("Total");

        // Set totals for each numeric column
        for (int i = 3; i < totals.length; i++) {
            HSSFCell totalCell = totalRow.createCell(i);
            totalCell.setCellValue(totals[i]);
            totalCell.setCellStyle(totalStyle);
        }

        // Write to response
        workbook.write(response.getOutputStream());
        workbook.close();

        return ResponseEntity.ok().build();
    }


    @Override
    public HttpEntity<?> downloadWorkerExcelForList(HttpServletResponse response, LocalDate date, Integer workerId) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=WorkerReport.xls");

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Worker Report");

        List<ReportSewingWorkers> workers = sewingRepo.getReportSewingWorkersForExcel(date, workerId, "");

// Define common styles
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);

// First row with the worker's full name
        int rowIndex = 0;
        if (!workers.isEmpty()) {
            ReportSewingWorkers firstWorker = workers.get(0);  // Assuming all workers have the same name in this report
            Row nameRow = sheet.createRow(rowIndex++);
            Cell nameCell = nameRow.createCell(0);
            nameCell.setCellValue("Ism Familiya: " + firstWorker.getFirstName() + " " + firstWorker.getLastName());
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));  // Merge cells across the width of the report
        }

// Headers for the left and right sections
        String[] headers = {"Килинган иш", "миқдори", "Сумма"};

// Create the headers
        Row headerRow = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);

            Cell cellRight = headerRow.createCell(i + 4);
            cellRight.setCellValue(headers[i]);
            cellRight.setCellStyle(headerStyle);
        }

// Fill data for each worker
        double totalLeftSumma = 0.0;
        double totalRightSumma = 0.0;

        for (ReportSewingWorkers worker : workers) {
            // Left section rows
            Row leftRow = sheet.createRow(rowIndex++);
            leftRow.createCell(0).setCellValue("Тикиш (метр)");
            leftRow.createCell(1).setCellValue(worker.getTotalPerimeter() != null ? worker.getTotalPerimeter().doubleValue() : 0.0);
            leftRow.createCell(2).setCellValue(worker.getSewingPrice() != null ? worker.getSewingPrice().doubleValue() : 0.0);
            totalLeftSumma += worker.getSewingPrice() != null ? worker.getSewingPrice().doubleValue() : 0.0;

            Row cuttingRow = sheet.createRow(rowIndex++);
            cuttingRow.createCell(0).setCellValue("Киркиш, бичиш (метр)");
            cuttingRow.createCell(1).setCellValue(worker.getReadyProdCount() != null ? worker.getReadyProdCount().doubleValue() : 0.0);
            cuttingRow.createCell(2).setCellValue(worker.getCuttingPrice() != null ? worker.getCuttingPrice().doubleValue() : 0.0);
            totalLeftSumma += worker.getCuttingPrice() != null ? worker.getCuttingPrice().doubleValue() : 0.0;

            Row chipRow = sheet.createRow(rowIndex++);
            chipRow.createCell(0).setCellValue("Чип куйиш (дона)");
            chipRow.createCell(1).setCellValue(worker.getChipCount() != null ? worker.getChipCount().doubleValue() : 0.0);
            chipRow.createCell(2).setCellValue(worker.getChipPrice() != null ? worker.getChipPrice().doubleValue() : 0.0);
            totalLeftSumma += worker.getChipPrice() != null ? worker.getChipPrice().doubleValue() : 0.0;

            Row cleaningRow = sheet.createRow(rowIndex++);
            cleaningRow.createCell(0).setCellValue("Тозалаш (метр)");
            cleaningRow.createCell(1).setCellValue(worker.getCleaningCount() != null ? worker.getCleaningCount().doubleValue() : 0.0);
            cleaningRow.createCell(2).setCellValue(worker.getCleaningPrice() != null ? worker.getCleaningPrice().doubleValue() : 0.0);
            totalLeftSumma += worker.getCleaningPrice() != null ? worker.getCleaningPrice().doubleValue() : 0.0;

            Row buttonOpenRow = sheet.createRow(rowIndex++);
            buttonOpenRow.createCell(0).setCellValue("Тугма очиш (дона)");
            buttonOpenRow.createCell(1).setCellValue(worker.getButtonOpenCount() != null ? worker.getButtonOpenCount().doubleValue() : 0.0);
            buttonOpenRow.createCell(2).setCellValue(worker.getButtonOpenPrice() != null ? worker.getButtonOpenPrice().doubleValue() : 0.0);
            totalLeftSumma += worker.getButtonOpenPrice() != null ? worker.getButtonOpenPrice().doubleValue() : 0.0;

            // Right section rows (align data side-by-side)
            Row yarnRow = sheet.getRow(rowIndex - 5);
            if (yarnRow == null) yarnRow = sheet.createRow(rowIndex - 5);
            yarnRow.createCell(4).setCellValue("Ип очиш (метр)");
            yarnRow.createCell(5).setCellValue(worker.getYarnOpenCount() != null ? worker.getYarnOpenCount().doubleValue() : 0.0);
            yarnRow.createCell(6).setCellValue(worker.getYarnOpenPrice() != null ? worker.getYarnOpenPrice().doubleValue() : 0.0);
            totalRightSumma += worker.getYarnOpenPrice() != null ? worker.getYarnOpenPrice().doubleValue() : 0.0;

            Row blueLabelRow = sheet.getRow(rowIndex - 4);
            if (blueLabelRow == null) blueLabelRow = sheet.createRow(rowIndex - 4);
            blueLabelRow.createCell(4).setCellValue("Кук этикетка (метр)");
            blueLabelRow.createCell(5).setCellValue(worker.getBlueLabel() != null ? worker.getBlueLabel().doubleValue() : 0.0);
            blueLabelRow.createCell(6).setCellValue(worker.getBlueLabelPrice() != null ? worker.getBlueLabelPrice().doubleValue() : 0.0);
            totalRightSumma += worker.getBlueLabelPrice() != null ? worker.getBlueLabelPrice().doubleValue() : 0.0;

            Row yellowChipRow = sheet.getRow(rowIndex - 3);
            if (yellowChipRow == null) yellowChipRow = sheet.createRow(rowIndex - 3);
            yellowChipRow.createCell(4).setCellValue("Сарик чип (закрепка) (метр)");
            yellowChipRow.createCell(5).setCellValue(worker.getYellowChip() != null ? worker.getYellowChip().doubleValue() : 0.0);
            yellowChipRow.createCell(6).setCellValue(worker.getYellowChipPrice() != null ? worker.getYellowChipPrice().doubleValue() : 0.0);
            totalRightSumma += worker.getYellowChipPrice() != null ? worker.getYellowChipPrice().doubleValue() : 0.0;

            Row plankRow = sheet.getRow(rowIndex - 2);
            if (plankRow == null) plankRow = sheet.createRow(rowIndex - 2);
            plankRow.createCell(4).setCellValue("Планка чизиш (метр)");
            plankRow.createCell(5).setCellValue(worker.getPlankCount() != null ? worker.getPlankCount().doubleValue() : 0.0);
            plankRow.createCell(6).setCellValue(worker.getPlankPrice() != null ? worker.getPlankPrice().doubleValue() : 0.0);
            totalRightSumma += worker.getPlankPrice() != null ? worker.getPlankPrice().doubleValue() : 0.0;

            Row chipsSewRow = sheet.getRow(rowIndex - 1);
            if (chipsSewRow == null) chipsSewRow = sheet.createRow(rowIndex - 1);
            chipsSewRow.createCell(4).setCellValue("Закрепка қилиш (дона)");
            chipsSewRow.createCell(5).setCellValue(worker.getMakePackCount() != null ? worker.getMakePackCount().doubleValue() : 0.0);
            chipsSewRow.createCell(6).setCellValue(worker.getPlankPrice() != null ? worker.getPlankPrice().doubleValue() : 0.0);
            totalRightSumma += worker.getPlankPrice() != null ? worker.getPlankPrice().doubleValue() : 0.0;

            // Include the additional row for "Упаковка (коробка) (дона)"
            Row boxRow = sheet.createRow(rowIndex++);
            boxRow.createCell(4).setCellValue("Упаковка (коробка) (дона)");
            boxRow.createCell(5).setCellValue(worker.getBoxCountTotal() != null ? worker.getBoxCountTotal().doubleValue() : 0.0);
            boxRow.createCell(6).setCellValue(worker.getBoxPrice() != null ? worker.getBoxPrice().doubleValue() : 0.0);
            totalRightSumma += worker.getBoxPrice() != null ? worker.getBoxPrice().doubleValue() : 0.0;
        }

// Add totals row only for "Сумма"
        Row totalRow = sheet.createRow(rowIndex++);
        totalRow.createCell(0).setCellValue("Жами:");
        totalRow.createCell(2).setCellValue(totalLeftSumma); // Only set "Сумма" column total for the left section
        totalRow.createCell(4).setCellValue("Жами:");
        totalRow.createCell(6).setCellValue(totalRightSumma); // Only set "Сумма" column total for the right section

// Calculate the overall totals for "Сумма" columns
        double totalSumma = totalLeftSumma + totalRightSumma;
        double totalWithTax = totalSumma / 0.88;

// Add rows for total calculations
        Row totalSumRow = sheet.createRow(rowIndex++);
        Cell totalSumCell = totalSumRow.createCell(0);
        totalSumCell.setCellValue("Ишчи томонидан қилинган ҳамма ишлар суммаси (солиқсиз):");
        sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, 5));
        totalSumRow.createCell(6).setCellValue(totalWithTax);

        Row totalWithTaxRow = sheet.createRow(rowIndex++);
        Cell totalWithTaxCell = totalWithTaxRow.createCell(0);
        totalWithTaxCell.setCellValue("Ишчи томонидан қилинган ҳамма ишлар суммаси (солиғи билан):");
        sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, 5));
        totalWithTaxRow.createCell(6).setCellValue(totalSumma);

        workbook.write(response.getOutputStream());
        workbook.close();
//        response.getOutputStream().close();

        return ResponseEntity.ok().build();
    }

    @Override
    public HttpEntity<?> downloadReportExcel(HttpServletResponse response) throws IOException {

//        response.setContentType("application/octet-stream");
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment;filename=Hisobot.xls";
//
//        response.setHeader(headerKey, headerValue);
//
//        List<ToolReportProjection> tools = toolRepo.getReportsExcel("");
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Reports");
//
//        HSSFCellStyle headerStyle = workbook.createCellStyle();
//        HSSFFont headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerStyle.setFont(headerFont);
//        headerStyle.setAlignment(HorizontalAlignment.CENTER);
//
//        HSSFCellStyle numberStyle = workbook.createCellStyle();
//        HSSFDataFormat format = workbook.createDataFormat();
//        numberStyle.setDataFormat(format.getFormat("#,##0.00"));
//
//        HSSFCellStyle totalStyle = workbook.createCellStyle();
//        HSSFFont totalFont = workbook.createFont();
//        totalFont.setBold(true);
//        totalFont.setFontHeightInPoints((short) 12);
//        totalStyle.setFont(totalFont);
//        totalStyle.setDataFormat(format.getFormat("#,##0.00"));
//
//        HSSFRow row = sheet.createRow(0);
//        String[] headers = {"#", "Ismi", "Chiqim turi", "Umumiy chiqim narxi", "Bo'lingan umumiy chiqim", "Umumiy chiqim", "Umumiy kirim"};
//        for (int i = 0; i < headers.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            cell.setCellValue(headers[i]);
//            cell.setCellStyle(headerStyle);
//        }
//
//        int dataRowIndex = 1;
//        double totalKirim = 0.0;
//        double totalChiqim = 0.0;
//        double totalChiqimPrice = 0.0;
//
//        for (ToolReportProjection tool : tools) {
//            HSSFRow dataRow = sheet.createRow(dataRowIndex);
//            dataRow.createCell(0).setCellValue(dataRowIndex);
//            dataRow.createCell(1).setCellValue(tool.getToolName() != null ? tool.getToolName() : "");
//            dataRow.createCell(2).setCellValue(tool.getExpenseType() != null ? tool.getExpenseType() : "");
//
//            HSSFCell totalExpensePriceCell = dataRow.createCell(3);
//            totalExpensePriceCell.setCellValue(tool.getTotalExpensePrice() != null ? tool.getTotalExpensePrice() : 0.0);
//            totalExpensePriceCell.setCellStyle(numberStyle);
//
//            HSSFCell totalExpenseBasedOnTypeCell = dataRow.createCell(4);
//            totalExpenseBasedOnTypeCell.setCellValue(tool.getTotalExpenseBasedOnType() != null ? tool.getTotalExpenseBasedOnType() : 0.0);
//            totalExpenseBasedOnTypeCell.setCellStyle(numberStyle);
//
//            HSSFCell totalExpenseCell = dataRow.createCell(5);
//            totalExpenseCell.setCellValue(tool.getTotalExpense() != null ? tool.getTotalExpense() : 0.0);
//            totalExpenseCell.setCellStyle(numberStyle);
//
//            HSSFCell totalIncomeCell = dataRow.createCell(6);
//            totalIncomeCell.setCellValue(tool.getTotalIncome() != null ? tool.getTotalIncome() : 0.0);
//            totalIncomeCell.setCellStyle(numberStyle);
//
//            totalKirim += tool.getTotalIncome() != null ? tool.getTotalIncome() : 0.0;
//            totalChiqim += tool.getTotalExpense() != null ? tool.getTotalExpense() : 0.0;
//            totalChiqimPrice += tool.getTotalExpensePrice() != null ? tool.getTotalExpensePrice() : 0.0;
//
//            // Alternate row coloring
//            if (dataRowIndex % 2 == 0) {
//                HSSFCellStyle alternateRowStyle = workbook.createCellStyle();
//                alternateRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
//                alternateRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                for (int i = 0; i <= 6; i++) {
//                    HSSFCell cell = dataRow.getCell(i);
//                    if (cell == null) {
//                        cell = dataRow.createCell(i);
//                    }
//                    cell.setCellStyle(alternateRowStyle);
//                }
//            }
//
//            dataRowIndex++;
//        }
//
//        // Create summary row
//        HSSFRow summaryRow = sheet.createRow(dataRowIndex);
//        summaryRow.createCell(0).setCellValue("Total");
//
//        HSSFCell totalExpensePriceCell = summaryRow.createCell(3);
//        totalExpensePriceCell.setCellValue(toolRepo.calculateTotalExpensePrice());
//        totalExpensePriceCell.setCellStyle(totalStyle);
//
//        HSSFCell totalChiqimCell = summaryRow.createCell(5);
//        totalChiqimCell.setCellValue(totalChiqim);
//        totalChiqimCell.setCellStyle(totalStyle);
//
//        HSSFCell totalKirimCell = summaryRow.createCell(6);
//        totalKirimCell.setCellValue(totalKirim);
//        totalKirimCell.setCellStyle(totalStyle);
//
//        // Apply style to summary row
//        HSSFCellStyle summaryStyle = workbook.createCellStyle();
//        HSSFFont summaryFont = workbook.createFont();
//        summaryFont.setBold(true);
//        summaryStyle.setFont(summaryFont);
//
//        for (int i = 0; i <= 6; i++) {
//            HSSFCell cell = summaryRow.getCell(i);
//            if (cell == null) {
//                cell = summaryRow.createCell(i);
//            }
//            cell.setCellStyle(summaryStyle);
//        }
//
//        // Auto-size columns
//        for (int i = 0; i <= 6; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        ServletOutputStream ops = response.getOutputStream();
//        workbook.write(ops);
//        workbook.close();
//        ops.close();
//
        return ResponseEntity.ok("");
    }

}
