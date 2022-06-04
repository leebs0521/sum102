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
        String sql = "insert into Product(name, price, userid, info, times) values(?,?,?,?, default)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getPrice());
            pstmt.setString(3, product.getUserid()); // 일단 비번 나중에 string으로 -> check
            pstmt.setString(4, product.getInfo());
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
        return null;
    }

    @Override
    public Product test1(){
        Product product = new Product();
        product.setId(1L);
        product.setInfo("테스트");
        product.setName("테스트");
        product.setPrice(10000);
        product.setTimes(new Timestamp(1L));

        return product;
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
