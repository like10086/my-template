package com.personal.utils;

import com.alibaba.excel.EasyExcel;
import com.personal.config.CustomCellStyleStrategyConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName: EasyExcelUtil
 * @Description: Excel导入导出工具类
 * @author: like
 * @date 2024/6/6 14:58
 */
@Component
public class EasyExcelUtil {
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String CHARACTER_ENCODING = "utf-8";
    private static final String HEADER = "Content-disposition";
    private static final String FILENAME_CHARACTER_ENCODING = "attachment;filename*=utf-8''";
    private static final String FILE_SUFFIX = ".xlsx";
    private static final String REGEX = "\\+";
    private static final String REPLACEMENT = "%20";

    @Resource
    private CustomCellStyleStrategyConfig customCellStyleStrategyConfig;

    /**
     * 导出Excel（到本地）
     *
     * @param dataList 数据列表
     * @param clazz    Excel对应的实体类
     * @param fileName 文件名
     */
    public void exportExcel(List<?> dataList, Class<?> clazz, String fileName) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            EasyExcel.write(outputStream, clazz)
                    .sheet()
                    .doWrite(dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel
     *
     * @param dataList 数据列表
     * @param clazz    Excel对应的实体类
     * @param fileName 文件名
     */
    public void exportExcel(HttpServletResponse response, List<?> dataList, Class<?> clazz, String fileName) {
        try {
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(CHARACTER_ENCODING);
            // URLEncoder.encode可以防止中文乱码
            fileName = URLEncoder.encode(fileName, CHARACTER_ENCODING).replaceAll(REGEX, REPLACEMENT);
            response.setHeader(HEADER, FILENAME_CHARACTER_ENCODING + fileName + FILE_SUFFIX);
            EasyExcel.write(response.getOutputStream(), clazz)
                    .registerWriteHandler(customCellStyleStrategyConfig)
                    .sheet()
                    .doWrite(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用模板导出
     *
     * @param templateInputStream 模板输入流
     * @param data                数据
     * @param fileName            输出文件名
     * @param <T>                 数据类型
     */
    public <T> void exportWithTemplate(HttpServletResponse response, InputStream templateInputStream, List<T> data, String fileName) {
        try {
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(CHARACTER_ENCODING);
            // URLEncoder.encode可以防止中文乱码
            fileName = URLEncoder.encode(fileName, CHARACTER_ENCODING).replaceAll(REGEX, REPLACEMENT);
            response.setHeader(HEADER, FILENAME_CHARACTER_ENCODING + fileName + FILE_SUFFIX);
            EasyExcel.write(response.getOutputStream(), data.get(0).getClass())
                    .withTemplate(templateInputStream)
                    .registerWriteHandler(customCellStyleStrategyConfig)
                    .sheet()
                    .doFill(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入Excel数据
     *
     * @param filePath 文件路径
     * @param clazz    Excel对应的数据实体类
     * @param <T>      数据类型
     * @return 数据列表
     */
    public <T> List<T> importExcel(String filePath, Class<T> clazz) {
        return EasyExcel.read(filePath).head(clazz).sheet().doReadSync();
    }
}
