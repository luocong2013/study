package com.zync.ui.frame.execute.dialog;

import com.zync.ui.vo.ComponentVO;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 查看详情弹出界面
 * @date 2019/5/25 22:42
 */
public class DetailsDialog extends JDialog implements ActionListener {
    private JTable jTable;
    private JScrollPane jScrollPane;
    private JButton confirmButton;
    /**系统剪切板*/
    private Clipboard clipboard;
    /**弹出菜单*/
    private JPopupMenu menu;
    private JMenuItem copy;
    /**拷贝内容*/
    private String copyData;

    public DetailsDialog(Frame parent, String title, boolean modal, ComponentVO component) {
        super(parent, title, modal);
        this.initComponents(component);
    }

    /**
     * 初始化组件
     * @param component
     */
    private void initComponents(ComponentVO component) {
        jTable = new JTable();
        jScrollPane = new JScrollPane(jTable);
        confirmButton = new JButton("确认");
        menu = new JPopupMenu();

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(450, 100, 980, 640);
        this.setLayout(null);
        this.setResizable(false);

        this.add(jScrollPane);
        jScrollPane.setBounds(10, 10, 950, 560);

        this.add(confirmButton);
        confirmButton.setBounds(880, 575, 80, 25);
        confirmButton.addActionListener(this);

        copy = new JMenuItem("复制");
        copy.addActionListener(this);
        menu.add(copy);

        // 表头
        Object[] columnNames = new Object[] {"错误类型", "相关文件", "错误标签", "出错行号", "错误描述", "正确示例"};

        // 表格信息
        Object[][] data = new Object[1][6];
        data[0] = new Object[] {"标签错误", "d:/file/a.zip", "<name></name>", "20", "姓名出错", "<name>aaa</name>"};

        jTable.setModel(new StanderTableModel(data, columnNames));
        jTable.setDefaultRenderer(Object.class, new TableViewRenderer());
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int code = e.getButton();
                if (code == MouseEvent.BUTTON1) {
                    // 单击鼠标左键

                } else if (code == MouseEvent.BUTTON3) {
                    // 单击鼠标右键
                    // 弹出菜单
                    menu.show(jTable, e.getX(), e.getY());
                    int row = jTable.rowAtPoint(e.getPoint());
                    int column = jTable.columnAtPoint(e.getPoint());
                    copyData = (String) jTable.getValueAt(row, column);
                }
            }
        });
        JTableHeader tableHeader = jTable.getTableHeader();
        // 设置表头不可拉动
        tableHeader.setResizingAllowed(false);
        // 设置表头不可移动
        tableHeader.setReorderingAllowed(false);
        TableColumnModel columnModel = jTable.getColumnModel();
        // 设置列宽首选大小
        columnModel.getColumn(0).setPreferredWidth(120);
        columnModel.getColumn(1).setPreferredWidth(190);
        columnModel.getColumn(2).setPreferredWidth(190);
        columnModel.getColumn(3).setPreferredWidth(90);
        columnModel.getColumn(4).setPreferredWidth(190);
        columnModel.getColumn(5).setPreferredWidth(190);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(confirmButton)) {
            this.dispose();
        } else if (obj.equals(copy)) {
            // 获取系统剪切板
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            // 封装文本内容
            Transferable contents = new StringSelection(copyData);
            // 把文本内容设置到系统剪贴板
            clipboard.setContents(contents, null);
        }
    }
}
