-- 初始化产品分组
INSERT INTO product_groups(name) VALUES ('default');
-- 初始化产品列表
INSERT INTO products(product_id, product_groupid, product_name, unit_price)
VALUES ('bda342a2-27c0-4cb1-bed3-11a786391365', 1, '糖尿病早期筛查', 0.03);
INSERT INTO products(product_id, product_groupid, product_name, unit_price)
VALUES ('83f7c071-7858-4e3f-b000-3c011db15e41', 1, '肾病早期筛查', 0.03);
INSERT INTO products(product_id, product_groupid, product_name, unit_price)
VALUES ('27e21552-3127-4b9d-8c79-6c791a22b3ec', 1, '中风早期筛查', 0.03);
-- 初始化产品折扣返现信息
INSERT INTO product_discounts(product_discount_id, product_id, discount_type, discount, cashback)
VALUES ('c188f77a-546d-482d-8167-a49a0b8effa0', 'bda342a2-27c0-4cb1-bed3-11a786391365', 'share', 0.01, 0.01);
INSERT INTO product_discounts(product_discount_id, product_id, discount_type, discount, cashback)
VALUES ('d9a46de0-2dd0-45bc-9d41-a5221eaaadec', '83f7c071-7858-4e3f-b000-3c011db15e41', 'share', 0.01, 0.01);
INSERT INTO product_discounts(product_discount_id, product_id, discount_type, discount, cashback)
VALUES ('598320c8-1be1-42be-9042-263443ba0126', '27e21552-3127-4b9d-8c79-6c791a22b3ec', 'share', 0.01, 0.01);