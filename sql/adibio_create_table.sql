
-- 描述用户基本信息
DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers
(
    row_id SERIAL,
    customer_id VARCHAR(255) NOT NULL,
    telphone VARCHAR(255),
    email VARCHAR(255),
    openid VARCHAR(255),
    register_time TIMESTAMP(0) NOT NULL,
    zhifubao_account VARCHAR(255),
    bank_card_number VARCHAR(255),
    CONSTRAINT customers_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT customers_customerid_unique UNIQUE(customer_id),
    CONSTRAINT customers_telphone_unique UNIQUE(telphone),
    CONSTRAINT customers_email_unique UNIQUE(email),
    CONSTRAINT customers_openid_unique UNIQUE(openid)
);

-- 描述用户微信相关信息，这个暂时不用
DROP TABLE IF EXISTS customer_wechats CASCADE;
CREATE TABLE customer_wechats
(
    row_id SERIAL,
    customer_id VARCHAR(255) NOT NULL,
    openid VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(255),
    retoken_time TIMESTAMP(0),
    nickname VARCHAR(255),
    sex INT,
    province VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    headimgurl VARCHAR(255),
    privilege JSON,
    unionid VARCHAR(255),
    CONSTRAINT wechats_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT wechats_customer_id_unique UNIQUE (customer_id),
    CONSTRAINT wechats_openid_unique UNIQUE (openid)
);

-- 患者描述信息
DROP TABLE IF EXISTS patient_infos CASCADE;
CREATE TABLE patient_infos 
(
    row_id SERIAL,
    customer_id VARCHAR(255) NOT NULL,
    patient_infoid VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(5) NOT NULL,
    age NUMERIC NOT NULL,
    country VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    has_diabetic INT NOT NULL,
    is_pregnant INT NOT NULL,
    height NUMERIC,
    weight NUMERIC,
    CONSTRAINT patient_infos_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT patient_infos_infoid_unique UNIQUE (patient_infoid),
    CONSTRAINT patient_infos_unique UNIQUE (customer_id, name, gender, age, country, city, district, address, phone, has_diabetic, is_pregnant)
);
-- 描述订单基本信息
DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders 
(
    row_id SERIAL,
    order_id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    price decimal NOT NULL,
    status VARCHAR(255) NOT NULL,
    description TEXT,
    CONSTRAINT orders_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT orders_orderid_unique UNIQUE (order_id)
);

-- 描述订单项目及数量信息
DROP TABLE IF EXISTS order_items CASCADE;
CREATE TABLE order_items
(
    row_id SERIAL,
    order_id VARCHAR(255) NOT NULL,
    order_itemid VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT order_items_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT order_items_itemid_unique UNIQUE (order_itemid)
);

-- 描述订单采集信息
DROP TABLE IF EXISTS order_infos CASCADE;
CREATE TABLE order_infos
(
    row_id SERIAL,
    order_itemid VARCHAR(255) NOT NULL,
    order_infoid VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    patient_infoid VARCHAR(255) NOT NULL,
    CONSTRAINT order_infos_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT order_infos_infoid_unique UNIQUE (order_infoid)
);

-- 创建订单检测人历史信息记录
DROP TABLE IF EXISTS order_patient_infos CASCADE ;
CREATE TABLE order_patient_infos (
    row_id SERIAL,
    order_patient_infoid VARCHAR(255) NOT NULL ,
    order_infoid VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(5) NOT NULL,
    age NUMERIC NOT NULL,
    country VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    has_diabetic INT NOT NULL,
    is_pregnant INT NOT NULL,
    height NUMERIC,
    weight NUMERIC,
    CONSTRAINT order_patient_infos_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT order_patient_infos_infoid UNIQUE (order_patient_infoid)
);

-- 描述订单事件信息
DROP TABLE IF EXISTS order_events CASCADE;
CREATE TABLE order_events
(
    row_id SERIAL,
    order_eventid VARCHAR(255) NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    event_time TIMESTAMP(0) NOT NULL,
    event_title VARCHAR(255) NOT NULL,
    event_executor VARCHAR(255) NOT NULL,
    description TEXT,
    CONSTRAINT order_events_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT order_events_eventid_unique UNIQUE (order_eventid)
);

-- 描述产品分组基本信息
DROP TABLE IF EXISTS product_groups CASCADE;
CREATE TABLE product_groups
(
    row_id SERIAL,
    product_groupid SERIAL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    CONSTRAINT product_groups_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT product_groups_groupid_unique UNIQUE (product_groupid),
    CONSTRAINT product_groups_name_unique UNIQUE (name)
);

-- 描述产品基本信息
DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products 
(
    row_id SERIAL,
    product_id VARCHAR(255) NOT NULL,
    product_groupid VARCHAR(255) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    unit_price DECIMAL NOT NULL,
    description TEXT,
    CONSTRAINT products_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT products_productid_unique UNIQUE(product_id),
    CONSTRAINT products_name_unique UNIQUE(product_name)
);
-- 描述产品折扣和返现信息
DROP TABLE IF EXISTS product_discounts CASCADE;
CREATE TABLE product_discounts
(
    row_id SERIAL,
    product_discount_id VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    discount_type VARCHAR(255) NOT NULL,
    discount DECIMAL NOT NULL,
    cashback DECIMAL,
    description TEXT,
    CONSTRAINT product_discounts_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT product_discounts_product_discountid_unique UNIQUE (product_discount_id)
);

-- 描述购物车内存储的患者订单信息
DROP TABLE IF EXISTS cart_patient_infos CASCADE;
CREATE TABLE cart_patient_infos 
(
    row_id SERIAL,
    cart_itemid VARCHAR(255) NOT NULL,
    cart_patient_infoid VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    patient_infoid VARCHAR(255) NOT NULL,
    CONSTRAINT cart_patient_infos_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT cart_patient_infos_unique UNIQUE (cart_patient_infoid)
);

-- 描述购物车信息
DROP TABLE IF EXISTS shopping_carts CASCADE;
CREATE TABLE shopping_carts 
(
    row_id SERIAL,
    customer_id VARCHAR(255) NOT NULL,
    cart_id VARCHAR(255) NOT NULL
);

-- 描述购物车项目及数量信息
DROP TABLE IF EXISTS cart_items CASCADE;
CREATE TABLE cart_items
(
    row_id SERIAL,
    cart_id VARCHAR(255) NOT NULL,
    cart_itemid VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT cart_items_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT cart_items_itemid_unique UNIQUE (cart_itemid)
);
-- 描述分享事件
DROP TABLE IF EXISTS shares CASCADE;
CREATE TABLE shares
(
    row_id SERIAL,
    share_id VARCHAR(255) NOT NULL,
    from_id VARCHAR(255) NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    CONSTRAINT shares_rowid_pk PRIMARY KEY (row_id),
    CONSTRAINT shares_shareid_unique UNIQUE (share_id)
);




