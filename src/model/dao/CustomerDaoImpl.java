package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.CustomerDao;
import model.vo.CustomerVO;

public class CustomerDaoImpl implements CustomerDao {
	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@192.168.0.35:1521:xe";
	final static String USER = "yun";
	final static String PASS = "1234";

	public CustomerDaoImpl() throws Exception {
		// 1. 드라이버로딩
		Class.forName(DRIVER); // 드라이버 로딩
		System.out.println("드라이버로딩 성공");
	}

	public void insertCustomer(CustomerVO vo) throws Exception {
		// 지역변수 초기화
		Connection con = null;
		PreparedStatement ps = null;

		// 2. Connection 연결객체 얻어오기
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "INSERT INTO CUSTOMER(custName , custTel1 , custAddr , custEmail , custTel2) "
					+ " VALUES(?,?,?,?,?) ";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPhone());
			ps.setString(3, vo.getAddress());
			ps.setString(4, vo.getEmail());
			ps.setString(5, vo.getAddPhone());

			// 5. sql 전송
			ps.executeUpdate();

		} finally {
			ps.close();
			con.close();
		}
	}

	/*
	 * 메소드명 : selectByTel 인자 : 검색 할 전화번호 리턴값 : 전화번호 검색에 따른 고객 정보 역할 : 사용자가 입력한 전화번호를
	 * 받아서 해당하는 고객정보를 리턴
	 */
	public CustomerVO selectByTel(String Phone) throws Exception {
		// tfCustTelSearch
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;


		try {
			// 2. 연결객체 얻어오기
			con = DriverManager.getConnection(URL, USER, PASS);
			CustomerVO dao = new CustomerVO();

			// 3. sql 문장 만들기
			String sql = "SELECT * FROM CUSTOMER WHERE custTel1 = ?";
		//	System.out.println(sql);
			
			// 4. 전송객체
			ps = con.prepareStatement(sql);
			ps.setString(1, Phone);

			// 5. 전송 - executeQuery()
			rs = ps.executeQuery();

			// 결과를 CustomerVO 에 담기
			if (rs.next()) {
			dao.setName(rs.getString("custName"));
			dao.setPhone(rs.getString("custTel1"));
			dao.setAddress(rs.getString("custAddr"));
			dao.setEmail(rs.getString("custEmail"));
			dao.setAddPhone(rs.getString("custTel2"));
			}
			// 6. 닫기

			return dao;

			}finally {
			rs.close();
			ps.close();
			con.close();
		}
	}

	public int updateCustomer(CustomerVO vo) throws Exception { // 핸드폰 번호만 남기고 전부 수정
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		// 2. Connection 연결객체 얻어오기
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "UPDATE CUSTOMER SET custName = ? , custAddr = ? , custEmail = ? , custTel2 = ? WHERE custTel1 = ?";


			// 4. sql 전송객체 (PreparedStatement)

			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getAddress());
			ps.setString(3, vo.getEmail());
			ps.setString(4, vo.getAddPhone());
			ps.setString(5, vo.getPhone());

			// 5. sql 전송
			result = ps.executeUpdate();

		} finally {
			ps.close();
			con.close();
		}


		return result;
	}
}
