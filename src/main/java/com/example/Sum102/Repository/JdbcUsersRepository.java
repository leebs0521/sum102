package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Users;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcUsersRepository implements UsersRepository {
    private final DataSource dataSource;

    public JdbcUsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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


    @Override
    public Users save(Users users) {
        String sql = "insert into Users(id, name, passwd) values(?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, users.getId());
            pstmt.setString(2, users.getName());
            pstmt.setString(3, users.getPasswd());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs != null) {
                System.out.println("회원가입 성공");
            } else {
                throw new SQLException("id 조회실패");
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Boolean checkUser(Users users) {
        String sql = "select count(*) from Users where id = \"" + users.getId() + "\"";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            rs = pstmt.executeQuery(sql);

            if (rs.next()) {

                if(rs.getInt("Count(*)") == 1) {
                    System.out.println("이미 존재하는 아이디");
                    return false;
                }
                else {
                    System.out.println("회원 가입 가능");
                    return true;
                }
            } else {
                throw new SQLException("id 조회실패");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Boolean loginCheck(Users users) {
        String sql = "select count(*) from Users where id = \"" + users.getId() + " \" and passwd =\"" + users.getPasswd() + "\"";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            rs = pstmt.executeQuery(sql);

            if (rs.next()) {

                if(rs.getInt("Count(*)") == 1) {
                    System.out.println("로그인 성공");
                    return true;
                }
                else {
                    System.out.println("로그인 실패");
                    return false;
                }
            } else {
                throw new SQLException("id 조회실패");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
}