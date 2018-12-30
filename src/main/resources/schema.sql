DROP TABLE IF EXISTS customer_sos_541455;

CREATE TABLE customer_sos_541455
(
    cust_id varchar(36) NOT NULL,
    cust_email varchar(200) NOT NULL,
    cust_first_name varchar(200) NOT NULL,
    cust_last_name varchar(200) NOT NULL,
    PRIMARY KEY (cust_id)
);

DROP TABLE IF EXISTS sales_order_541455;

CREATE TABLE sales_order_541455
(
    id varchar(36) NOT NULL,
    total_price varchar(200) NOT NULL,
    order_desc varchar(200) NOT NULL,
    cust_id varchar(36) NOT NULL,
    order_date date NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order_line_item_541455;

CREATE TABLE order_line_item_541455
(
    id varchar(36) NOT NULL,
    item_name varchar(200) NOT NULL,
    item_quantity varchar(200) NOT NULL,
    order_id varchar(36) NOT NULL,
    PRIMARY KEY (id)
);