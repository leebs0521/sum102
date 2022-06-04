package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Product;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductRepository implements ProductRepository {

    private final DataSource dataSource;

    public JdbcProductRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> findAll() {
        String sql = "select * from Product";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while(rs.next()) {  //불러올 테이블 컬럼 목록
                Product product = new Product();
                product.setTitle(rs.getString("title"));
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setUserid(rs.getString("userid"));
                product.setInfo(rs.getString("info"));
                product.setTimes(rs.getTimestamp("times"));
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<Product> findAll(String title) {
        String sql = "select DISTINCT * from Product where title LIKE concat('%',?,'%') OR name LIKE concat('%',?,'%')";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, title);
            rs = pstmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while(rs.next()) {  //불러올 테이블 컬럼 목록
                Product product = new Product();
                product.setTitle(rs.getString("title"));
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setUserid(rs.getString("userid"));
                product.setInfo(rs.getString("info"));
                product.setTimes(rs.getTimestamp("times"));
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Product save(Product product) {
        String sql = "insert into Product(title, name, price, userid, info, times) values(?,?,?,?,?, default)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, product.getTitle());
            pstmt.setString(2, product.getName());
            pstmt.setInt(3, product.getPrice());
            pstmt.setString(4, product.getUserid()); // 일단 비번 나중에 string으로 -> check
            pstmt.setString(5, product.getInfo());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs != null){
                System.out.println("good");
            } else{
                throw new SQLException("too bad");
            }
            return product;
        }catch(Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Product findOne(Long pid) {
        // 경원
        String sql = "select * from Product where id=" + pid.toString();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            Product product = new Product();
            while(rs.next()) {  //불러올 테이블 컬럼 목록
                product.setTitle(rs.getString("title"));
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setUserid(rs.getString("userid"));
                product.setInfo(rs.getString("info"));
                product.setTimes(rs.getTimestamp("times"));
            }
            return product;
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
