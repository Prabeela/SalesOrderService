package com.salesorder.microservices.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.salesorder.microservices.domain.Customer;

import java.sql.Date;

import java.util.List;

@Repository
public class CustomerSOSRepository {

	private final JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT = "insert into customer_sos_541455(cust_id,cust_email,cust_first_name,cust_last_name) values(?,?,?,?)";
    private final String SQL_QUERY_ALL = "select * from customer_sos_541455";
    private final String SQL_QUERY_BY_ID = "select * from customer_sos_541455 where cust_id=?";

    private final RowMapper<Customer> rowMapper = (ResultSet rs, int row) -> {
        Customer customer = new Customer();
        customer.setId(rs.getString("cust_id"));
        customer.setEmail(rs.getString("cust_email"));
        customer.setFirst_name(rs.getString("cust_first_name"));
        customer.setLast_name(rs.getString("cust_last_name"));
        return customer;
    };

    @Autowired
    public CustomerSOSRepository(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public Customer save(Customer customer) {
        assert customer.getEmail() != null;
        assert customer.getFirst_name() != null;
        assert customer.getLast_name() != null;

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getFirst_name());
            ps.setString(4, customer.getLast_name());
            return ps;
        });

        return customer;
    }

    public List<Customer> findAll() {
        return this.jdbcTemplate.query(SQL_QUERY_ALL, rowMapper);
    }

    public Customer findOne(String id) {
        return this.jdbcTemplate.queryForObject(SQL_QUERY_BY_ID, new Object[]{id}, rowMapper);
    }
}
