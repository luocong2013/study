package com.zync.intellij.platform.plugin.frame.execute.dialog;

import javax.swing.table.DefaultTableModel;

/**
 * 数据模型
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/24 13:05
 */
public class StanderTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 8506162402294207211L;

    public StanderTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // 所有表格不可编辑
        return false;
    }
}
