package com.personal.config;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: CustomCellStyleStrategyConfig
 * @Description: easyExcel导出样式配置
 * @author: like
 * @date 2024/6/6 15:49
 */
@Configuration
public class CustomCellStyleStrategyConfig extends HorizontalCellStyleStrategy {
    public CustomCellStyleStrategyConfig() {
        super(getDefaultHeadStyle(), getDefaultContentStyle());
    }

    private static WriteCellStyle getDefaultHeadStyle() {
        // 设置表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 边框
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);
        return headWriteCellStyle;
    }

    private static WriteCellStyle getDefaultContentStyle() {
        // 设置内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置自动换行
        contentWriteCellStyle.setWrapped(true);
        // 边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        return contentWriteCellStyle;
    }

    public WriteCellStyle getContentCellStyle(CellData cellData, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return getDefaultHeadStyle();
        } else {
            // 可以根据需要为特定的单元格或数据添加特殊样式
            // 这里仅作为示例，直接返回默认内容样式
            return getDefaultContentStyle();
        }
    }
}
