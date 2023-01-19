package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{
    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) { //스프링을 통해 Datasource를 주입받음
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //쿼리 결과를 담는 객체


        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //RETURN_GENERATED_KEYS : 임의의 ID 값을 전달
            pstmt.setString(1, member.getName()); //1번쨰 물음표에 member.getName() 입력

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys(); //name이 저장되면서 발생한  읨의의 id를 꺼내옴.

            if(rs.next()){ //resultSet에 값이 있다면
                member.setId(rs.getLong(1)); //값을 꺼내서 member에 삽입
            }else{
                throw new SQLException("ID 조회 실패");
            }
            return member;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally{
            close(conn, pstmt, rs); //사용이 끝난 후에는 Connection, PreparedStatement, ResultSet을 다시 닫아줘야함.
            //과거 국비에서 배울 때...프로젝트 도중 Connection을 안달아서 틈만나면 메모리 누수로 서버가 죽었던 기억이 있지..
        }
    }


    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery(); //조회는 excuteQuery
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery(); //조회는 excuteQuery
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        //스프링 Framework를 통해서 Connection을 사용시에는 DataSourceUtils를 통해서 획득함.
        //DB Transaction을 걸기 위해선 하나의 Connection 객체를 유지해야 하기 떄문이다.
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        //네트워크 연결 객체 생성시 Connection -> Statement -> ResultSet 순으로 생성
        //연결 해제시 생성의 역순으로 닫아줌.
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }


}
