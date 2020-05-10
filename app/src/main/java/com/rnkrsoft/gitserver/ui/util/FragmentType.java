package com.rnkrsoft.gitserver.ui.util;

public enum FragmentType {
	
	USERS("用户"),
	REPOSITORIES("仓库");
	
	private String title;

	private FragmentType(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}
