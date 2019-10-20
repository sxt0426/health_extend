package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 报表
 */
@RestController
@RequestMapping("report")
public class ReportController {

    private static final Logger log = Logger.getLogger(ReportController.class);

    @Reference
    MemberService memberService;

    @Reference
    SetmealService setmealService;


    @Reference
    ReportService reportService;

    /**
     * 获取会员统计数据
     *
     * @return
     */
    @RequestMapping("getMemberReport")
    public Result getMemberReport(String[] value) {
        try {
            Map map = memberService.getMemberReport(value);
            if (map != null) {
                //查询成功
                return Result.success(MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
            }
        } catch (Exception e) {
            log.error("Get member report error.", e);
        }
        return Result.error(MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
    }

    /**
     * 统计套餐预约数量 每个套餐卖了多少
     * <p>
     * "setmealNames":["套餐1","套餐2","套餐3"],
     * "setmealCount":[
     * {"name":"套餐1","value":10},
     * {"name":"套餐2","value":30},
     * {"name":"套餐3","value":25}
     * ]
     * },
     *
     * @return
     */
    @RequestMapping("getSetmealReport")
    public Result getSetmealReport() {
        Map<String, Object> result = new HashMap<>();

        //1.查询套餐统计数据
        List<Map<String, Object>> setmealCounts = setmealService.getSetmealReport();

        //2.封装页面所需要的数据结构
        List<String> setmealNames = new ArrayList<>();

        for (Map<String, Object> setmealCount : setmealCounts) {
            setmealNames.add((String) setmealCount.get("name"));
        }

        result.put("setmealNames", setmealNames);
        result.put("setmealCounts", setmealCounts);

        return Result.success(MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, result);
    }

    /**
     * 运营数据统计
     * <p>
     * reportDate:null,
     * todayNewMember :0,
     * totalMember :0,
     * thisWeekNewMember :0,
     * thisMonthNewMember :0,
     * todayOrderNumber :0,
     * todayVisitsNumber :0,
     * thisWeekOrderNumber :0,
     * thisWeekVisitsNumber :0,
     * thisMonthOrderNumber :0,
     * thisMonthVisitsNumber :0,
     * hotSetmeal :[]
     *
     * @return
     */
    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> data = reportService.getBusinessReportData();

            return Result.success(MessageConstant.GET_BUSINESS_REPORT_SUCCESS, data);
        } catch (Exception e) {
            log.error("Get Business Data error.", e);
        }

        return Result.error(MessageConstant.GET_BUSINESS_REPORT_FAIL);
    }

    @RequestMapping("exportBusinessReport")
    public void exportBusinessReport(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //1.拿到需要导出的数据
        Map<String, Object> data = reportService.getBusinessReportData();

        //2.通过excel导出

        //取出返回结果数据，准备将报表数据写入到Excel文件中
        String reportDate = (String) data.get("reportDate");
        Integer todayNewMember = (Integer) data.get("todayNewMember");
        Integer totalMember = (Integer) data.get("totalMember");
        Integer thisWeekNewMember = (Integer) data.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) data.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) data.get("todayOrderNumber");
        Integer thisWeekOrderNumber = (Integer) data.get("thisWeekOrderNumber");
        Integer thisMonthOrderNumber = (Integer) data.get("thisMonthOrderNumber");
        Integer todayVisitsNumber = (Integer) data.get("todayVisitsNumber");
        Integer thisWeekVisitsNumber = (Integer) data.get("thisWeekVisitsNumber");
        Integer thisMonthVisitsNumber = (Integer) data.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) data.get("hotSetmeal");


        //获得Excel模板文件绝对路径
        //request.getSession().getServletContext() 获取根目录
        String temlateRealPath = request.getSession().getServletContext().getRealPath("template") +
                File.separator + "report_template.xlsx";

        //读取模板文件创建Excel表格对象
        XSSFWorkbook workbook;
        workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));

        //拿到工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        //拿到row index都是从0开始的
        XSSFRow row = sheet.getRow(2);
        row.getCell(5).setCellValue(reportDate);//日期

        //往单元格写数据 index都是从0开始的
        row = sheet.getRow(4);
        row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
        row.getCell(7).setCellValue(totalMember);//总会员数

        row = sheet.getRow(5);
        row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
        row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

        row = sheet.getRow(7);
        row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
        row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

        row = sheet.getRow(8);
        row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
        row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

        row = sheet.getRow(9);
        row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
        row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

        int rowNum = 12;
        for(Map map : hotSetmeal){//热门套餐
            String name = (String) map.get("name");
            Long setmeal_count = (Long) map.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map.get("proportion");
            row = sheet.getRow(rowNum ++);
            row.getCell(4).setCellValue(name);//套餐名称
            row.getCell(5).setCellValue(setmeal_count);//预约数量
            row.getCell(6).setCellValue(proportion.doubleValue());//占比
        }

        //通过response 输出流进行文件下载
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");//浏览器下载 设置数据类型为excel
        response.setHeader("content-Disposition", "attachment;filename=report.xlsx");//设置excel输出内容
        workbook.write(out);

        out.flush();
        out.close();
        workbook.close();
    }
}