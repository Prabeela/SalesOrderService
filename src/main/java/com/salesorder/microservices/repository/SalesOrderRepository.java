package com.salesorder.microservices.repository;

import java.sql.Date;
import java.sql.PreparedStatement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.salesorder.microservices.domain.Item;
import com.salesorder.microservices.domain.SalesOrder;


@Repository
public class SalesOrderRepository {
	
	
	private final JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT_SALES_ORDER_TBL = "insert into sales_order(id,total_price,order_desc,cust_id,order_date) values(?,?,?,?,?)";
    private final String SQL_INSERT_ORDER_LINE_ITEM_TBL = "insert into order_line_item(id,item_name,item_quantity,order_id) values(?,?,?,?)";
    
   

    @Autowired
    public SalesOrderRepository(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public SalesOrder saveSalesOrderDtls(SalesOrder salesOrder) {
        assert salesOrder.getOrderDate() != null;
        assert salesOrder.getCust_id() != null;
        assert salesOrder.getDescription() != null;
        assert salesOrder.getPrice()!=null;

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_SALES_ORDER_TBL);
            ps.setString(1, salesOrder.getId());
            ps.setString(2, salesOrder.getPrice());
            ps.setString(3, salesOrder.getDescription());
            ps.setString(4, salesOrder.getCust_id());
            ps.setDate(5, new java.sql.Date(salesOrder.getOrderDate().getTime()));
           
            return ps;
        });

        return salesOrder;
    }

    public void saveOrderLineItemDtls(SalesOrder salesOrder,Item item,String quantity) {
    	
    	
        assert salesOrder.getOrderDate() != null;
        assert salesOrder.getCust_id() != null;
        assert salesOrder.getDescription() != null;
        assert salesOrder.getPrice()!=null;

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ORDER_LINE_ITEM_TBL);
            ps.setString(1, java.util.UUID.randomUUID().toString());
            ps.setString(2, item.getName());
            ps.setString(3, quantity);
            ps.setString(4, salesOrder.getId());
            
            return ps;
            
        });

        
    }
    
}
