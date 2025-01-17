package siestageek.memberapplication.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static siestageek.memberapplication.BuildConfig.DB_URL;
import static siestageek.memberapplication.BuildConfig.DB_USER;
import static siestageek.memberapplication.BuildConfig.DB_PWD;

public class MariaDBHelper {

    // 보안상의 이유로 DB 연결정보는 gradle의 build.config에 작성
    // 먼저, gradle.properties에 DB 연결정보 작성
    // 그 후 build.gradle에서 이것을 사용한다고 설정
/*    private static final String DB_URL = "jdbc:mariadb://:3306/";
    private static final String DB_USER = "";
    private static final String DB_PWD = "";*/

//    create table member (
//    mno int primary key auto_increment,
//    userid varchar(18) unique,
//    passwd varchar(18) not null,
//    name varchar(18) not null,
//    email text not null,
//    regdate datetime default current_timestamp());


    // MariaDB Driver 초기화
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    // 회원 가입 처리
    public boolean insertMember(String userid, String passwd, String name, String email) {
        String sql = "insert into member (userid, passwd, name, email) values (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            pstmt.setString(2, passwd);
            pstmt.setString(3, name);
            pstmt.setString(4, email);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    // 아이디 중복 확인
    public boolean useridCheck(String userid) {
        String sql = "select mno from member where userid = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userid);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    // 회원 목록 조회 메서드
    public List<String> getAllUsers() {
        return null;
    }

    // 로그인 확인
    public boolean loginUser(String userid, String passwd) {
        return false;
    }

    // 데이터베이스 연결 객체 가져오기
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
    }




}
