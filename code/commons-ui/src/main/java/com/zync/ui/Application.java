package com.zync.ui;

import com.zync.ui.common.consts.FrameType;
import com.zync.ui.common.utils.PropertiesUtil;
import com.zync.ui.frame.execute.ExecuteUI;
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
        String frame = PropertiesUtil.get("commons.ui.frame");
        if (FrameType.EXECUTE.equals(FrameType.valueOf(frame))) {
            EventQueue.invokeLater(() -> new ExecuteUI().setVisible(true));
        }
    }
}
