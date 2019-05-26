package com.zync.ui.frame.execute.dialog;

import javax.swing.table.DefaultTableModel;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 数据模型
 * @date 2019/5/25 22:43
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
