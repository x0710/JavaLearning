package com.code;

import javafx.stage.Stage;

/**
 * 定义可以运行的接口
 * 所有的可被运行打开的开始窗体都应该实现这个接口
 * @author 15940
 *
 */
public interface Startable {
	/**
	 * 该窗口加载时被调用
	 * 在方法实现中不应该手动调用{@link Stage#show()}
	 * @return 要打开的窗体
	 */
	Stage init();
	/**
	 * 该窗口的唯一标识
	 * @return key
	 */
	String instruction();
}
