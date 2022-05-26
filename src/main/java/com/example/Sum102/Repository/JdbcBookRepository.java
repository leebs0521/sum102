package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Books;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookRepository implements BookRepository{

    private final DataSource dataSource;

    public JdbcBookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

        public List<Books> findAll() {
            String sql = "select * from books";
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<Books> books = new ArrayList<>();
                while(rs.next()) {  //불러올 테이블 컬럼 목록
                    Books book = new Books();
                    book.setBookId(rs.getLong("bookid"));
                    book.setbName(rs.getString("bName"));
                    book.setbPrice(rs.getInt("bPrice"));
                    book.setUserID(rs.getInt("userID"));
                    book.setbInfo(rs.getString("bInfo"));
                    books.add(book);
                }
                return books;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
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
