package com.rnkrsoft.gitserver.ssh;

import java.sql.SQLException;

import com.rnkrsoft.gitserver.db.DBHelper;
import com.rnkrsoft.gitserver.db.entity.Permission;
import com.rnkrsoft.gitserver.db.entity.Repository;
import com.rnkrsoft.gitserver.db.entity.User;
import com.rnkrsoft.gitserver.exception.SshAuthorizationException;
import android.content.Context;

public class SshAuthorizationManager {
//	private final static String TAG = SshAuthorizationManager.class.getSimpleName();
	
	private DBHelper dbHelper;

	public SshAuthorizationManager(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	public boolean hasRepositoryPullPermission(String username, String repositoryMapping) throws SshAuthorizationException {
		return hasRepositoryPermission(username, repositoryMapping, true);
	}
	
	public boolean hasRepositoryPushPermission(String username, String repositoryMapping) throws SshAuthorizationException {
		return hasRepositoryPermission(username, repositoryMapping, false);
	}
		
	private boolean hasRepositoryPermission(String username, String repositoryMapping, boolean checkPull) throws SshAuthorizationException {
		try {
			// Query for user by username
			User user = dbHelper.getUserDao().queryForUsername(username);

			if(user == null) {
				throw new SshAuthorizationException("There should exactly one record in the database for username: " + username);
			}
			
			// Query for repository with specified mapping
			Repository repository = dbHelper.getRepositoryDao().queryForMappingAndActive(repositoryMapping);
			
			if(repository == null) {
				throw new SshAuthorizationException("There should exactly one record in the database for repository mapping: " + repositoryMapping);
			}
			
			// Query for permission with specified user and repository ids
			Permission permission = dbHelper.getPermissionDao().queryForUserAndRepository(user.getId(), repository.getId());
			
			// If there is no permission record in the database - there is no positive permission set for the user.
			if(permission == null) {
				return false;
			}
			
			// Check for pull or push
			if(checkPull) {
				// If there is permission record - there is a pull privileges.
				return true;
			} else {
				return !permission.isReadOnly();
			}
		} catch (SQLException e) {
			throw new SshAuthorizationException("I/O problem while quering the database.", e);
		}
	}
}
