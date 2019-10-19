package com.itheima.common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {

    /**
     * 读取单元格中的数据
     * @throws IOException
     */
    @Test
    public void ExcelReadTest() throws IOException {
        //1.创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\fzoo\\Desktop\\83\\CZJK\\第5章\\资源\\poi测试数据.xlsx");

        //2.获取工作表sheet
        XSSFSheet sheet = workbook.getSheetAt(0);

        //3.获取数据行
        for (Row row : sheet) {
            //4.遍历行中的单元格
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
    }

    @Test
    public void ExcelRead2Test() throws IOException {
        //1.创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\fzoo\\Desktop\\83\\CZJK\\第5章\\资源\\poi测试数据.xlsx");

        //2.获取工作表sheet
        //workbook.getNumberOfSheets();//获取工作表的数量
        XSSFSheet sheet = workbook.getSheetAt(0);

        //3.获取数据行 获取最后一行数据的索引号 索引号从0开始
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i <=lastRowNum; i++) {
            //通过索引号 获取数据行
            XSSFRow row = sheet.getRow(i);

            //获取单元格 获取最后一个单元格的索引号
            short lastCellNum = row.getLastCellNum();

            for (int j = 0; j < lastCellNum; j++) {
                System.out.println("cell index:"+j);

                //根据单元格索引号 获取 单元格
                XSSFCell cell = row.getCell(j);

                System.out.println(cell.getStringCellValue());

            }
        }
    }

    /**
     * 创建工作薄，并向文档中写数据
     * @throws Exception
     */
    @Test
    public void ExcelWrite() throws Exception {
        //1.创建工作薄对象
        XSSFWorkbook workbook = new XSSFWorkbook();

        //2.创建工作表
        XSSFSheet sheet = workbook.createSheet("itheima");

        //3.通过sheet创建数据行row
        XSSFRow row0 = sheet.createRow(0);
        //4.通过row创建单元格cell
        row0.createCell(0).setCellValue("小明");
        row0.createCell(1).setCellValue("男");
        row0.createCell(2).setCellValue("上海");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("小米");
        row1.createCell(1).setCellValue("女");
        row1.createCell(2).setCellValue("北京");

        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\fzoo\\Desktop\\83\\CZJK\\第5章\\资源\\itheima.xlsx");

        workbook.write(outputStream);

        outputStream.flush();
        outputStream.close();

        workbook.close();
    }
}