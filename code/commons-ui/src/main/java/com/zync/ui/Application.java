package com.zync.ui;

import com.zync.ui.common.utils.PropertiesUtil;
import com.zync.ui.factory.FrameFactory;
import com.zync.ui.frame.AbstractFrame;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 程序入口
 * @date 2019/5/25 15:58
 */
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        String frameName = PropertiesUtil.get("commons.ui.frame.name");
        if (StringUtils.isBlank(frameName)) {
            LOGGER.info("启动失败！没有配置窗口类型，请检查配置文件中窗口类型是否配置");
            return;
        }
        AbstractFrame frame = FrameFactory.getFrame(frameName);
        if (frame == null) {
            LOGGER.info("启动失败！没有该类型【{}】的窗口的实现，请检查配置文件中窗口类型是否配置错误", frameName);
            return;
        }
        EventQueue.invokeLater(() -> frame.setVisible(true));
    }
}
