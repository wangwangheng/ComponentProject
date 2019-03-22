package com.xinye.architecture.stateful;

/**
 * 状态
 *
 * @author wangheng
 */
public interface IStateful {

    /**
     * 是否能执行Fragment事务
     */
    boolean canPerformFragmentTransaction();

    /**
     * 是否能显示、隐藏Dialog
     */
    boolean canPerformDialog();

    /**
     * 是否能更新View
     */
    boolean canPerformUpdateView();

    boolean isUIExists();
}
