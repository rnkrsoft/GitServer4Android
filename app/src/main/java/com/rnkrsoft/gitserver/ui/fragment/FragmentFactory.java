package com.rnkrsoft.gitserver.ui.fragment;

import com.rnkrsoft.gitserver.ui.util.FragmentType;

public final class FragmentFactory {
	public static BaseFragment createFragment(FragmentType fragmentType) {
		switch (fragmentType) {
		case USERS:
			return new UsersFragment();
		case REPOSITORIES:
			return new RepositoriesFragment();
		}
		
		return null;
	}
}
