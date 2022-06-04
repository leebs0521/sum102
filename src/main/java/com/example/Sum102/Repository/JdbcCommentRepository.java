package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Comment;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCommentRepository implements CommentRepository{

    private final DataSource dataSource;

    public JdbcCommentRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public List<Comment> findAll(Long pid) {
        String sql = "select * from Comment where pid = (?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, pid);
            rs = pstmt.executeQuery();
            while (rs.next()) {  // 불러올 테이블 칼럼 목록
                Comment comment = new Comment();
                comment.setId(rs.getLong("id"));
                comment.setPid(rs.getLong("pid"));
                comment.setUserid(rs.getString("userid"));
                comment.setComment(rs.getString("comment"));
                comment.setTimes(rs.getTimestamp("times"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return comments;
    }

    @Override
    public Comment save(Comment comment) {
        // 원영이형
        String sql = "insert into Comment(pid, userid, comment, times) values(?, ?, ?, default)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(2, comment.getUserid());
            pstmt.setLong(1, comment.getPid());
            pstmt.setString(3, comment.getComment()); // 일단 비번 나중에 string으로 -> check
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs != null){
                System.out.println("good");
            } else{
                throw new SQLException("too bad");
            }
            return comment;
        }catch(Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }


    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
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
