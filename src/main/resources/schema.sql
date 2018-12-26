DROP TABLE IF EXISTS customer_sos;

CREATE TABLE customer_sos
(
    cust_id varchar(36) NOT NULL,
    cust_email varchar(200) NOT NULL,
    cust_first_name varchar(200) NOT NULL,
    cust_last_name varchar(200) NOT NULL,
    PRIMARY KEY (cust_id)
);

DROP TABLE IF EXISTS sales_order;

CREATE TABLE sales_order
(
    id varchar(36) NOT NULL,
    total_price varchar(200) NOT NULL,
    order_desc varchar(200) NOT NULL,
    cust_id varchar(36) NOT NULL,
    order_date date NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order_line_item;

CREATE TABLE order_line_item
(
    id varchar(36) NOT NULL,
    item_name varchar(200) NOT NULL,
    item_quantity varchar(200) NOT NULL,
    order_id varchar(36) NOT NULL,
    PRIMARY KEY (id)
);