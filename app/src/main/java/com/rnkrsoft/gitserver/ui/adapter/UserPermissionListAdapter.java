package com.rnkrsoft.gitserver.ui.adapter;

import java.util.List;

import com.rnkrsoft.gitserver.db.entity.Permission;
import android.content.Context;

public class UserPermissionListAdapter extends BasePermissionListAdapter {

	public UserPermissionListAdapter(Context context, List<Permission> items, int resourceIconPull, int resourceIconPushPull) {
		super(context, items, resourceIconPull, resourceIconPushPull);
	}
	
	@Override
	protected String getItemName(int position) {
		return items.get(position).getUser().getFullname();
	}

}
