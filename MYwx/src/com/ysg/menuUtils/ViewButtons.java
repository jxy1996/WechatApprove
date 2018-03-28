package com.ysg.menuUtils;

/**
 * 复杂按钮（父按钮）
 * 包含有二级菜单项的一级菜单。这类菜单项包含有二个属性：name和sub_button，而sub_button以是一个子菜单项数组
 * @date 2013-09-10
 */
public class ViewButtons extends Button {
	private ViewButton[] sub_button;

	public ViewButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(ViewButton[] sub_button) {
		this.sub_button = sub_button;
	}
}