package com.zync.ui.dialog;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 表格视图解析器
 * @date 2019/5/25 22:44
 */
public class TableViewRenderer extends JTextArea implements TableCellRenderer {

    private static final long serialVersionUID = 8942034958164265575L;

    public TableViewRenderer() {
        // 将表格设为自动换行
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // 计算当前行的最佳高度
        int maxPreferredHeight = 0;
        for (int i = 0; i < table.getColumnCount(); i++) {
            this.setText(StringUtils.EMPTY + table.getValueAt(row, i));
            this.setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
            maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
        }
        if (table.getRowHeight(row) != maxPreferredHeight) {
            table.setRowHeight(row, maxPreferredHeight);
        }
        this.setText(value == null ? StringUtils.EMPTY : value.toString());
        return this;
    }
}
