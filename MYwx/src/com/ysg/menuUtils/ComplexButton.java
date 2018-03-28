package com.ysg.menuUtils;

/**
 * 复杂按钮（父按钮）
 * 包含有二级菜单项的一级菜单。这类菜单项包含有二个属性：name和sub_button，而sub_button以是一个子菜单项数组
 * @date 2013-09-10
 */
public class ComplexButton extends Button {
	private ClickButton[] sub_button;

	public ClickButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(ClickButton[] sub_button) {
		this.sub_button = sub_button;
	}
}