package com.ensinthanaigal.server.util;

public enum Category {

	TRAVEL(1, "travel"), COOKING(2, "cooking"), TECHNOLOGY(3, "tech"), ENTERTAINMENT(
			4, "entertainment");

	private int value;
	private String label;

	private Category(int value, String label) {
		this.value = value;
		this.label = label;

	}

	public static String getLabel(int value) {
		Category[] catTypeArr = Category.values();
		for (int i = 0; i < catTypeArr.length; i++) {
			Category catType = catTypeArr[i];
			if (catType.value == value) {
				return catType.label;
			}
		}
		return null;
	}

	public static int getValue(String label) {
		Category[] catTypeArr = Category.values();
		for (int i = 0; i < catTypeArr.length; i++) {
			Category catType = catTypeArr[i];
			if (catType.label.equalsIgnoreCase(label)) {
				return catType.value;
			}
		}
		return 0;
	}

}
