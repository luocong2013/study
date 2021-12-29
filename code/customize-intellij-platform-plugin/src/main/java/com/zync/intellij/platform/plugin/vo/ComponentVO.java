package com.zync.intellij.platform.plugin.vo;

import javax.swing.*;
import java.io.File;

/**
 * 界面组件对象
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/24 12:56
 */
public class ComponentVO {

    /**
     * 索引
     */
    private int index;
    /**
     * 选择的文件
     */
    private File chooseFile;
    /**
     * 文件路径label
     */
    private JLabel filePathLabel;
    /**
     * 文件路径文本框
     */
    private JTextField filePath;
    /**
     * 结果
     */
    private JLabel resultLabel;
    /**
     * 查看详情按钮
     */
    private JButton detailBt;
    /**
     * 删除按钮
     */
    private JButton deleteBt;

    private ComponentVO(Builder builder) {
        index = builder.index;
        chooseFile = builder.chooseFile;
        filePathLabel = builder.filePathLabel;
        filePath = builder.filePath;
        resultLabel = builder.resultLabel;
        detailBt = builder.detailBt;
        deleteBt = builder.deleteBt;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public File getChooseFile() {
        return chooseFile;
    }

    public void setChooseFile(File chooseFile) {
        this.chooseFile = chooseFile;
    }

    public JLabel getFilePathLabel() {
        return filePathLabel;
    }

    public void setFilePathLabel(JLabel filePathLabel) {
        this.filePathLabel = filePathLabel;
    }

    public JTextField getFilePath() {
        return filePath;
    }

    public void setFilePath(JTextField filePath) {
        this.filePath = filePath;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }

    public void setResultLabel(JLabel resultLabel) {
        this.resultLabel = resultLabel;
    }

    public JButton getDetailBt() {
        return detailBt;
    }

    public void setDetailBt(JButton detailBt) {
        this.detailBt = detailBt;
    }

    public JButton getDeleteBt() {
        return deleteBt;
    }

    public void setDeleteBt(JButton deleteBt) {
        this.deleteBt = deleteBt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int index;
        private File chooseFile;
        private JLabel filePathLabel;
        private JTextField filePath;
        private JLabel resultLabel;
        private JButton detailBt;
        private JButton deleteBt;

        public Builder() {
        }

        public Builder index(int val) {
            index = val;
            return this;
        }

        public Builder chooseFile(File val) {
            chooseFile = val;
            return this;
        }

        public Builder filePathLabel(JLabel val) {
            filePathLabel = val;
            return this;
        }

        public Builder filePath(JTextField val) {
            filePath = val;
            return this;
        }

        public Builder resultLabel(JLabel val) {
            resultLabel = val;
            return this;
        }

        public Builder detailBt(JButton val) {
            detailBt = val;
            return this;
        }

        public Builder deleteBt(JButton val) {
            deleteBt = val;
            return this;
        }

        public ComponentVO build() {
            return new ComponentVO(this);
        }
    }

    @Override
    public String toString() {
        return "ComponentVO{" +
                "index=" + index +
                ", chooseFile=" + chooseFile +
                ", filePathLabel=" + filePathLabel +
                ", filePath=" + filePath +
                ", resultLabel=" + resultLabel +
                ", detailBt=" + detailBt +
                ", deleteBt=" + deleteBt +
                '}';
    }
}
