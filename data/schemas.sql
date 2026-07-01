USE
inventory_db;

-- 1. Tabelas Independentes (Não possuem chaves estrangeiras)
CREATE TABLE person
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255)       NOT NULL,
    birth_date DATE,
    document   VARCHAR(20) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255)   NOT NULL,
    description    TEXT,
    price          DECIMAL(10, 2) NOT NULL,
    is_active      BOOLEAN  DEFAULT TRUE,
    stock_quantity INT      DEFAULT 0,
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE coupon
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    code           VARCHAR(50) UNIQUE NOT NULL,
    discount_type  ENUM('Fixed', 'Percentual') NOT NULL,
    discount_value DECIMAL(10, 2)     NOT NULL,
    minimum_price  DECIMAL(10, 2)     NOT NULL,
    expires_at     DATE               NOT NULL,
    is_active      BOOLEAN  DEFAULT TRUE,
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Tabelas Nível 1 (Dependem apenas de uma tabela)
CREATE TABLE user
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    person_id  INT UNIQUE         NOT NULL, -- UNIQUE pois é uma relação 1 para 1
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    is_admin   BOOLEAN  DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE stock_movement
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    product_id    INT            NOT NULL,
    quantity      INT            NOT NULL,
    movement_type ENUM('IN', 'OUT', 'ADJUST') NOT NULL,
    unit_value    DECIMAL(10, 2) NOT NULL,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE cart
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL,
    status     ENUM('Open', 'Closed', 'Expired') DEFAULT 'Open',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

-- 3. Tabelas Nível 2 (Dependem de várias tabelas)
CREATE TABLE cart_item
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    cart_id    INT            NOT NULL,
    product_id INT            NOT NULL,
    quantity   INT            NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT            NOT NULL,
    coupon_id      INT NULL, -- Pode ser nulo se o cliente não usar cupom
    status         ENUM('Created', 'Paid', 'Preparation', 'Shipped', 'Delivered', 'Canceled') NOT NULL,
    total_value    DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50)    NOT NULL,
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (coupon_id) REFERENCES coupon (id)
);

CREATE TABLE order_item
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    order_id   INT            NOT NULL,
    product_id INT            NOT NULL,
    quantity   INT            NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE delivery
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    order_id      INT UNIQUE NOT NULL, -- UNIQUE pois 1 pedido tem 1 entrega
    status        ENUM('Preparing', 'Shipped', 'InTransit', 'Delivered', 'Cancelled') NOT NULL,
    carrier       VARCHAR(100),
    tracking_code VARCHAR(100),
    shipping_date DATE,
    delivery_date DATE,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);