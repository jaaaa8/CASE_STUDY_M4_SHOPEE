drop database case_std_shopee;
create database if not exists case_std_shopee;
use case_std_shopee;

create table account(
	account_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER','ADMIN') DEFAULT 'USER',
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table category(
	category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

create table product(
	product_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description TEXT,
	price INT NOT NULL,
    stock INT DEFAULT 0,
    image_url VARCHAR(255),
	category_id INT,
	seller_id INT,
    FOREIGN KEY (category_id) REFERENCES category(category_id) on update cascade on delete cascade,
    FOREIGN KEY (seller_id) REFERENCES account(account_id) on update cascade on delete cascade
);

create table orders(
	order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    status ENUM('IN PROGRESS','COMPLETED') DEFAULT 'IN PROGRESS',
    total INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES account(account_id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table sub_orders(
	sub_order_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    confirmed_by INT,
    seller_id INT,
    status ENUM('PENDING','CONFIRMED','SHIPPED','COMPLETED','CANCELLED') DEFAULT 'PENDING',
    total INT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES account(account_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (confirmed_by) REFERENCES account(account_id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table order_items(
	order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    sub_order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price INT NOT NULL,
    is_checked BOOLEAN default TRUE,
    FOREIGN KEY (sub_order_id) REFERENCES sub_orders(sub_order_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

create table review(
	review_id INT,
    product_id INT,
    account_id INT,
    rating INT,
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

