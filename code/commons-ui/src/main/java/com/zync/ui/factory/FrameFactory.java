package com.zync.ui.factory;

import com.zync.ui.common.consts.FrameType;
import com.zync.ui.frame.AbstractFrame;
import com.zync.ui.frame.execute.ExecuteUI;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 窗口工厂类
 * @date 2019/5/26 22:29
 */
public final class FrameFactory {

    private FrameFactory() {
    }

    /**
     * 获取窗口
     * @param frameName 窗口名
     * @return
     */
    public static AbstractFrame getFrame(String frameName) {
        if (FrameType.EXECUTE.equals(FrameType.valueOf(frameName))) {
            return new ExecuteUI();
        } else if (FrameType.HTTP.equals(FrameType.valueOf(frameName))) {
            return null;
        } else if (FrameType.SCRAPY.equals(FrameType.valueOf(frameName))) {
            return null;
        }
        return null;
    }
}
