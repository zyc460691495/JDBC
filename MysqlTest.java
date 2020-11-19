import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MysqlTest {

	Connection conn;

	// 连接数据库
	public void connectToDatebase() throws Exception {
		// 读取文件
		Properties pro = new Properties();
		InputStream is = new FileInputStream("D:\\Ecplise\\WorkSpace\\Database\\src\\test.properties");
		pro.load(is);
		// 装入驱动程序
		String diverName=pro.getProperty("driverName");
		String url=pro.getProperty("url");
		String userName=pro.getProperty("userName");
		String passWord=pro.getProperty("passWord");
		Class.forName(diverName);
		// 连接到数据库
		conn = DriverManager.getConnection(url, userName, passWord);
	}

	public void allQuery() throws SQLException {
		// 查询整个表
		String allQueryStr = "select * from students";
		Statement allQueryPreStatement = conn.createStatement();
		ResultSet result = allQueryPreStatement.executeQuery(allQueryStr);
		System.out.format("%-10s %20s %10s %11s %22s %10s\n", "学号", "姓名", "性别", "专业", "学校", "年龄");
		while (result.next()) {
			System.out.format("%-5d %15s %5s %13s %10s %5d\n", result.getInt(1), result.getString(2),
					result.getString(3), result.getString(4), "\t" + result.getString(5), result.getInt(6));
		}
	}

	// 根据年龄查询信息
	public void toAgeQuery(int age) throws SQLException {
		System.out.println("年龄为" + age + "的学生有：\n");
		String whereQueryStr = "select * from students where 年龄=?";
		PreparedStatement whereQueryPreStatement = conn.prepareStatement(whereQueryStr);
		whereQueryPreStatement.setInt(1, age);
		ResultSet result = whereQueryPreStatement.executeQuery();
		System.out.format("%-10s %20s %10s %11s %22s %10s\n", "学号", "姓名", "性别", "专业", "学校", "年龄");
		while (result.next()) {
			System.out.format("%-5d %15s %5s %13s %10s %5d\n", result.getInt(1), result.getString(2),
					result.getString(3), result.getString(4), "\t" + result.getString(5), result.getInt(6));
		}
	}

	// 根据学号更新学生的所属学校
	public void toNumUpdateSchool(String schoolName, int num) throws SQLException {
		String updateStr = "update students set 学校=? where 学号=?";
		PreparedStatement updatePreStatement = conn.prepareStatement(updateStr);
		updatePreStatement.setString(1, schoolName);
		updatePreStatement.setInt(2, num);
		int result = updatePreStatement.executeUpdate();
		System.out.println("更新已完成，受影响的行数：" + result);
		updatePreStatement.close();
	}

	// 插入新的学生信息
	public void insert(int num, String name, String sex, String major, String schoolName, int age) throws SQLException {
		String insertStr = "insert into students values(?,?,?,?,?,?)";
		PreparedStatement insertStrPreStatement = conn.prepareStatement(insertStr);
		insertStrPreStatement.setInt(1, num);
		insertStrPreStatement.setString(2, name);
		insertStrPreStatement.setString(3, sex);
		insertStrPreStatement.setString(4, major);
		insertStrPreStatement.setString(5, schoolName);
		insertStrPreStatement.setInt(6, age);
		int result = insertStrPreStatement.executeUpdate();
		System.out.println("插入已完成，受影响的行数：" + result);
	}

	// 根据姓名删除学生信息
	public void toNameDelete(String name) throws SQLException {
		String deleteStr = "delete from students where 姓名=?";
		PreparedStatement deleteStrPreStatement = conn.prepareStatement(deleteStr);
		deleteStrPreStatement.setString(1, name);
		int result = deleteStrPreStatement.executeUpdate();
		System.out.println("删除已完成，受影响的行数：" + result);
	}

	public static void main(String[] args) throws Exception {

		MysqlTest mysql = new MysqlTest();
		mysql.connectToDatebase();
		mysql.allQuery();
		mysql.toAgeQuery(19);
		mysql.toNumUpdateSchool("just", 5);
		mysql.allQuery();
		mysql.insert(33, "zyc", "男", "软件工程", "just", 19);
		mysql.allQuery();
		mysql.toNameDelete("zyc");
		mysql.allQuery();
//		mysql.conn.close();
	}

}
