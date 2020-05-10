package com.rnkrsoft.gitserver.db;

import java.sql.SQLException;

import com.rnkrsoft.gitserver.db.dao.PermissionDao;
import com.rnkrsoft.gitserver.db.dao.RepositoryDao;
import com.rnkrsoft.gitserver.db.dao.UserDao;
import com.rnkrsoft.gitserver.db.entity.Permission;
import com.rnkrsoft.gitserver.db.entity.Repository;
import com.rnkrsoft.gitserver.db.entity.User;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {
	private final static String TAG = DBHelper.class.getSimpleName();
	
	private final static String DB_NAME = "git_server.db";
	private final static int DB_VERSION = 20150210;
	
	private UserDao userDao;
	private RepositoryDao repositoryDao;
	private PermissionDao permissionDao;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Repository.class);
			TableUtils.createTable(connectionSource, Permission.class);
		} catch (SQLException e) {
			Log.e(TAG, "Unable to create databases", e);
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, Repository.class, true);
			TableUtils.dropTable(connectionSource, Permission.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			Log.e(TAG, "Unable to upgrade database from version " + oldVersion + " to new " + newVersion, e);
		}
	}
	
	public UserDao getUserDao() throws SQLException {
		if (userDao == null) {
			Dao<User, Integer> dao = getDao(User.class);
			userDao = new UserDao(this, dao);
		}
		
		return userDao;
	}
	
	public RepositoryDao getRepositoryDao() throws SQLException {
		if (repositoryDao == null) {
			Dao<Repository, Integer> dao = getDao(Repository.class);
			repositoryDao = new RepositoryDao(this, dao);
		}
		
		return repositoryDao;
	}
	
	public PermissionDao getPermissionDao() throws SQLException {
		if (permissionDao == null) {
			Dao<Permission, Integer> dao = getDao(Permission.class);
			permissionDao = new PermissionDao(this, dao);
		}
		
		return permissionDao;
	}
}
