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

	// �������ݿ�
	public void connectToDatebase() throws Exception {
		// ��ȡ�ļ�
		Properties pro = new Properties();
		InputStream is = new FileInputStream("D:\\Ecplise\\WorkSpace\\Database\\src\\test.properties");
		pro.load(is);
		// װ����������
		String diverName=pro.getProperty("driverName");
		String url=pro.getProperty("url");
		String userName=pro.getProperty("userName");
		String passWord=pro.getProperty("passWord");
		Class.forName(diverName);
		// ���ӵ����ݿ�
		conn = DriverManager.getConnection(url, userName, passWord);
	}

	public void allQuery() throws SQLException {
		// ��ѯ������
		String allQueryStr = "select * from students";
		Statement allQueryPreStatement = conn.createStatement();
		ResultSet result = allQueryPreStatement.executeQuery(allQueryStr);
		System.out.format("%-10s %20s %10s %11s %22s %10s\n", "ѧ��", "����", "�Ա�", "רҵ", "ѧУ", "����");
		while (result.next()) {
			System.out.format("%-5d %15s %5s %13s %10s %5d\n", result.getInt(1), result.getString(2),
					result.getString(3), result.getString(4), "\t" + result.getString(5), result.getInt(6));
		}
	}

	// ���������ѯ��Ϣ
	public void toAgeQuery(int age) throws SQLException {
		System.out.println("����Ϊ" + age + "��ѧ���У�\n");
		String whereQueryStr = "select * from students where ����=?";
		PreparedStatement whereQueryPreStatement = conn.prepareStatement(whereQueryStr);
		whereQueryPreStatement.setInt(1, age);
		ResultSet result = whereQueryPreStatement.executeQuery();
		System.out.format("%-10s %20s %10s %11s %22s %10s\n", "ѧ��", "����", "�Ա�", "רҵ", "ѧУ", "����");
		while (result.next()) {
			System.out.format("%-5d %15s %5s %13s %10s %5d\n", result.getInt(1), result.getString(2),
					result.getString(3), result.getString(4), "\t" + result.getString(5), result.getInt(6));
		}
	}

	// ����ѧ�Ÿ���ѧ��������ѧУ
	public void toNumUpdateSchool(String schoolName, int num) throws SQLException {
		String updateStr = "update students set ѧУ=? where ѧ��=?";
		PreparedStatement updatePreStatement = conn.prepareStatement(updateStr);
		updatePreStatement.setString(1, schoolName);
		updatePreStatement.setInt(2, num);
		int result = updatePreStatement.executeUpdate();
		System.out.println("��������ɣ���Ӱ���������" + result);
		updatePreStatement.close();
	}

	// �����µ�ѧ����Ϣ
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
		System.out.println("��������ɣ���Ӱ���������" + result);
	}

	// ��������ɾ��ѧ����Ϣ
	public void toNameDelete(String name) throws SQLException {
		String deleteStr = "delete from students where ����=?";
		PreparedStatement deleteStrPreStatement = conn.prepareStatement(deleteStr);
		deleteStrPreStatement.setString(1, name);
		int result = deleteStrPreStatement.executeUpdate();
		System.out.println("ɾ������ɣ���Ӱ���������" + result);
	}

	public static void main(String[] args) throws Exception {

		MysqlTest mysql = new MysqlTest();
		mysql.connectToDatebase();
		mysql.allQuery();
		mysql.toAgeQuery(19);
		mysql.toNumUpdateSchool("just", 5);
		mysql.allQuery();
		mysql.insert(33, "zyc", "��", "�������", "just", 19);
		mysql.allQuery();
		mysql.toNameDelete("zyc");
		mysql.allQuery();
//		mysql.conn.close();
	}

}
