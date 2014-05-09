package com.wfcsu.wfweb.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @title:提供数据库连接的方法和执行数据库语句
 * @date:2012-7-26
 * @author Cat
 * 
 */
public class ConnDB {
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pstm = null;
	private static DataSource source;

	// 获取数据源
	static {
		try {
			Context initial = new InitialContext();
			source = (DataSource) initial.lookup("java:comp/env/jdbc/wfcsu");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取数据源
	public static DataSource getDataSource() {
		return source;
	}

	// 创建连接
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 执行查询语句
	public ResultSet executeQuery(String sql, Object[] params) {
		if (params == null) {
			params = new Object[0];
		}
		try {
			conn = getConnection();
			pstm = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			System.out.println(pstm);
			rs = pstm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 执行更新操作
	public int executeUpdate(String sql, Object[] params) {
		int r = 0;
		conn = getConnection();
		if (params == null) {
			params = new Object[0];
		}
		try {
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			System.out.println(pstm);
			r = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	// 释放连接
	public void free() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}

	// 释放连接
	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}

	// 得到数据库所有列名
	private static String[] getColNames(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		String[] colNames = new String[count];
		for (int i = 1; i <= count; i++) {
			colNames[i - 1] = rsmd.getColumnLabel(i);
		}
		return colNames;
	}

	// 利用反射自动封装Vo类
	public static ArrayList<Object> getVo(String sql, Object[] params, Class clazz)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {
		Connection conn = ConnDB.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Object> list = new ArrayList<Object>();
		
		if (params == null) {
			params = new Object[0];
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			rs = pstm.executeQuery();
			String[] colNames = getColNames(rs);

			Object obj = null;
			Method[] ms = clazz.getMethods();
			while (rs.next()) {
				obj = clazz.newInstance();
				for (int i = 0; i < colNames.length; i++) {
					String scolName = colNames[i];
					String c = scolName.toLowerCase();
					String colName = c.substring(0, 1).toUpperCase()
							+ c.substring(1);
					String methodName = "set" + colName;
					for (Method m : ms) {
						if (methodName.equals(m.getName())) {
							m.invoke(obj, rs.getObject(colName)+"");
							break;
						}
					}
				}
				list.add(obj);
			}
			return list;
		} finally {
			free(rs, pstm, conn);
		}

	}
	
}
