package com.itheima.service;

import java.util.Map;

/**
 * 报表统计接口
 */
public interface ReportService {
    Map<String,Object> getBusinessReportData() throws Exception;
}