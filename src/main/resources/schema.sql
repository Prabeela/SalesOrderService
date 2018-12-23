DROP TABLE IF EXISTS customer_sos;

CREATE TABLE customer_sos
(
    cust_id varchar(36) NOT NULL,
    cust_email varchar(200) NOT NULL,
    cust_first_name varchar(200) NOT NULL,
    cust_last_name varchar(200) NOT NULL,
    PRIMARY KEY (cust_id)
);