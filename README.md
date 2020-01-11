# Java连接Mysql数据库

## 一、环境基础：

1. jdk版本

  ![image](https://user-images.githubusercontent.com/19297162/68989654-ba265100-0884-11ea-8f0a-380ebff7a966.png)

2. mysql版本 -> 环境搭建链接（https://github.com/MrSayyes/Grocery-store/blob/master/环境配置/Mysql环境搭建.md）

  ![image](https://user-images.githubusercontent.com/19297162/68989640-a11da000-0884-11ea-99d9-8aaf2b4c1168.png)

## 二、mysql连接驱动包下载
首先要下载Connector/J地址：http://www.mysql.com/downloads/connector/j/

这是MySQL官方提供的连接方式（即MySQL连接驱动），解压后得到jar库文件，需要在工程中导入该库文件。

解压后的文件：

![image](https://user-images.githubusercontent.com/19297162/68989737-ab8c6980-0885-11ea-982e-101c32418d30.png)

## 三、eclipse工具通过代码实现
1. 新建project，然后工程目录下面新建lib文件夹，目录结构如下：

  ![image](https://user-images.githubusercontent.com/19297162/68989781-674d9900-0886-11ea-8cff-e468e6c77520.png)

2. lib文件夹下面cp过来mysql连接驱动包：

  ![image](https://user-images.githubusercontent.com/19297162/68989801-9bc15500-0886-11ea-966b-587fdefe576a.png)

3. mysql连接驱动包加入构建路径

  `对于mysql驱动包右击 -> Build Path -> Add To Build Path`

4. 在src下面新建包com.test，以及包下面新建类 MysqlDemo.java

  ![image](https://user-images.githubusercontent.com/19297162/68990031-36229800-0889-11ea-918e-4bea9fefb685.png)

5. MysqlDemo.java代码（这里使用的是mysql库的User表）
```
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
```
6. 代码运行输出结果如下（前图为mysql执行，后图为程序运行，两者一致）：

  ![image](https://user-images.githubusercontent.com/19297162/68990156-ad0c6080-088a-11ea-990c-5f24e65f6136.png)

  ![image](https://user-images.githubusercontent.com/19297162/68990162-c1505d80-088a-11ea-83ab-6f3fec1176f9.png)

  