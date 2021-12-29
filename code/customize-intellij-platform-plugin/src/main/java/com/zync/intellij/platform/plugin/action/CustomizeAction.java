package com.zync.intellij.platform.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.zync.intellij.platform.plugin.frame.execute.ExecuteUI;

/**
 * @author luocong
 * @version v1.0
 * @date 2021/12/29 16:08
 */
public class CustomizeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 点击按钮会调到这里
        new ExecuteUI().setVisible(true);
    }
}
