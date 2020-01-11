package com.test;
import java.sql.*;

public class MysqlDemo {
	// MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
	// static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	// static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";

	// MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "root";// root密码，根据mysql设置的密码来填写

	public static void main(String[] args) {
		// 数据库连接
		Connection connection = null;
		// 预编译的Statement，使用预编译的Statement提高数据库性能
		PreparedStatement preparedStatement = null;
		// 结果集
		ResultSet resultSet = null;
		try {
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);
			// 打开链接
			System.out.println("连接数据库...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			// 定义sql语句
			String sql = "select User from user";
			// 获取预处理statement
			preparedStatement = connection.prepareStatement(sql);
			// 向数据库发出sql执行查询，查询出结果集
			resultSet = preparedStatement.executeQuery();
			// 展开结果集数据库
			while (resultSet.next()) {
				// 通过字段检索
				String name = resultSet.getString("User");
				// 输出数据
				System.out.println("mysql用户: " + name);
			}
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		} finally {
			// 释放资源
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("查询结束!");
	}
}