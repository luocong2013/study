package com.zync.intellij.platform.plugin.frame.execute;

import com.zync.intellij.platform.plugin.common.consts.Const;
import com.zync.intellij.platform.plugin.frame.AbstractFrame;
import com.zync.intellij.platform.plugin.frame.execute.dialog.DetailsDialog;
import com.zync.intellij.platform.plugin.vo.ComponentVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 执行任务界面
 * @author luocong
 * @version v1.0
 * @date 2021/12/24 12:53
 */
public class ExecuteUI extends AbstractFrame implements ActionListener {
    private JButton chooseFileBt;
    private JButton refreshBt;
    private JCheckBox md5Check;
    private JButton executeBt;
    private JFileChooser jFileChooser;
    private JScrollPane jScrollPane;
    private JPanel centerPanel;

    /**
     * 组件list
     */
    private static LinkedList<ComponentVO> components = new LinkedList<>();

    public ExecuteUI() {
        this.initComponents();
        this.addActionListener();
    }

    /**
     * 初始化主界面
     */
    private void initComponents() {
        chooseFileBt = new JButton();
        refreshBt = new JButton();
        md5Check = new JCheckBox();
        executeBt = new JButton();
        jFileChooser = new JFileChooser();
        jScrollPane = new JScrollPane();
        centerPanel = new JPanel();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("执行任务工具 - " + Const.VERSION);
        this.setResizable(false);
        this.setBounds(450, 100, 980, 640);
        this.setLayout(null);

        chooseFileBt.setText("选择任务…");
        this.add(chooseFileBt);
        chooseFileBt.setBounds(40, 30, 130, 28);

        refreshBt.setText("清空面板");
        this.add(refreshBt);
        refreshBt.setBounds(200, 30, 80, 28);

        md5Check.setText("MD5校验");
        this.add(md5Check);
        md5Check.setBounds(310, 30, 80, 28);

        executeBt.setText("执行");
        this.add(executeBt);
        executeBt.setBounds(420, 30, 60, 28);

        this.add(jScrollPane);
        jScrollPane.setBounds(40, 70, 900, 500);
        jScrollPane.setViewportView(centerPanel);

        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.WHITE);

        jFileChooser.setDialogTitle("选择文件");
    }

    /**
     * 为button添加监听事件
     */
    private void addActionListener() {
        chooseFileBt.addActionListener(this);
        refreshBt.addActionListener(this);
        executeBt.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(chooseFileBt)) {
            // 选择文件
            this.chooseFile();
        } else if (obj.equals(refreshBt)) {
            // 刷新
            this.refresh();
        } else if (obj.equals(executeBt)) {
            // 执行
            this.execute();
        }
    }

    /**
     * 选择待校验的文件
     */
    private void chooseFile() {
        // 设置只能选择文件
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置允许选择多个文件
        jFileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP & XML FILE （支持Ctrl+A多选）", "zip", "xml", "txt");
        jFileChooser.setFileFilter(filter);
        int retuanVal = jFileChooser.showOpenDialog(null);
        if (retuanVal == JFileChooser.APPROVE_OPTION) {
            File[] files = jFileChooser.getSelectedFiles();
            if (ArrayUtils.isNotEmpty(files)) {
                // 动态添加组件
                addComponents(files);
            }
        }
    }

    /**
     * 动态添加组件
     *
     * @param files 文件
     */
    private void addComponents(File[] files) {
        int y = 30;
        LinkedList<ComponentVO> addComponents = new LinkedList<>();
        // 添加新组件
        if (ArrayUtils.isNotEmpty(files)) {
            List<File> fileLst = miuns(files);
            for (File file : fileLst) {
                addComponents.add(addComponent(file, y));
                y += 50;
            }
        }

        // 移动已添加的组件
        if (CollectionUtils.isNotEmpty(components)) {
            for (ComponentVO component : components) {
                moveComponent(component, y);
                y += 50;
            }
        }
        components.addAll(0, addComponents);
        // 重新设置面板首选大小，以便超出高度可以滚动显示
        centerPanel.setPreferredSize(new Dimension(875, y));
        // 通知其他控件，本控件高度变了
        centerPanel.revalidate();
        // 重绘此控件
        centerPanel.repaint();
    }

    /**
     * 去除重复上传的文件
     * @param files 文件
     * @return 去重后的文件列表
     */
    private List<File> miuns(File[] files) {
        List<String> chooesFiles = components.stream().map(item -> item.getChooseFile().getAbsolutePath()).collect(Collectors.toList());
        return Arrays.stream(files).filter(item -> !chooesFiles.contains(item.getAbsolutePath())).collect(Collectors.toList());
    }

    private ComponentVO addComponent(File file, int y) {
        JLabel filePathLabel = new JLabel("文件路径：");
        centerPanel.add(filePathLabel);
        filePathLabel.setBounds(10, y, 72, 28);

        JTextField filePath = new JTextField(file.getAbsolutePath());
        centerPanel.add(filePath);
        filePath.setBounds(90, y, 550, 28);
        // 设置为不可编辑
        filePath.setEditable(false);

        JLabel resultLabel = new JLabel("未执行");
        centerPanel.add(resultLabel);
        resultLabel.setBounds(650, y, 60, 28);

        JButton detailBt = new JButton("查看详情");
        centerPanel.add(detailBt);
        detailBt.setBounds(720, y, 82, 28);
        detailBt.setEnabled(false);

        JButton deleteBt = new JButton("删除");
        centerPanel.add(deleteBt);
        deleteBt.setBounds(815, y, 60, 28);

        // 索引
        int index = y / 50;
        ComponentVO component = ComponentVO.builder()
                .index(index)
                .chooseFile(file)
                .filePathLabel(filePathLabel)
                .filePath(filePath)
                .resultLabel(resultLabel)
                .detailBt(detailBt)
                .deleteBt(deleteBt)
                .build();
        // 添加“查看详情”点击事件
        detailBt.addActionListener(e -> new DetailsDialog(ExecuteUI.this, "详情", true, component).setVisible(true));
        // 添加“删除”点击事件
        deleteBt.addActionListener(e -> deleteFileButton(component));
        return component;
    }

    /**
     * 从中心面板中移除组件
     * @param component 组件
     */
    private void deleteFileButton(ComponentVO component) {
        // 从列表中移除
        components.remove(component.getIndex());
        // 从面板中移除组件
        JLabel filePathLabel = component.getFilePathLabel();
        JTextField filePath = component.getFilePath();
        JLabel resultLabel = component.getResultLabel();
        JButton detailBt = component.getDetailBt();
        JButton deleteBt = component.getDeleteBt();
        centerPanel.remove(filePathLabel);
        centerPanel.remove(filePath);
        centerPanel.remove(resultLabel);
        centerPanel.remove(detailBt);
        centerPanel.remove(deleteBt);

        // 重新渲染布局
        addComponents(null);
    }

    /**
     * 移动组件
     * @param component 组件
     * @param y         移动距离
     */
    private void moveComponent(ComponentVO component, int y) {
        // 索引
        int index = y / 50;
        component.setIndex(index);

        JLabel filePathLabel = component.getFilePathLabel();
        filePathLabel.setLocation(10, y);

        JTextField filePath = component.getFilePath();
        filePath.setLocation(90, y);

        JLabel resultLabel = component.getResultLabel();
        resultLabel.setLocation(650, y);

        JButton detailBt = component.getDetailBt();
        detailBt.setLocation(720, y);

        JButton deleteBt = component.getDeleteBt();
        deleteBt.setLocation(815, y);
    }

    /**
     * 执行
     */
    private void execute() {
        if (CollectionUtils.isEmpty(components)) {
            JOptionPane.showMessageDialog(this, "请先选择未执行的文件！", "提示信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // TODO 其他操作
        for (ComponentVO component : components) {
            component.getDetailBt().setEnabled(true);
        }
    }

    /**
     * 刷新面板
     */
    private void refresh() {
        // 移除面板中所有组件
        centerPanel.removeAll();
        // 清空列表
        components.clear();
        // 重新设置面板首选大小，以便超出高度可以滚动显示
        centerPanel.setPreferredSize(new Dimension(875, 0));
        // 通知其他控件，本控件高度变了
        centerPanel.revalidate();
        // 重绘此控件
        centerPanel.repaint();
    }
}
