-- Stock Reservation Logic:
-- Stock reduces ONLY on CONFIRM, not on DRAFT
-- If CONFIRMED order is CANCELLED, stock is restored
-- Handled atomically with @Transactional in OrderService

CREATE TABLE categories (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE customers (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          price FLOAT NOT NULL,
                          stock_quantity INTEGER NOT NULL,
                          category_id BIGINT NOT NULL,
                          FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE orders (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        status VARCHAR(50) NOT NULL,
                        total_amount FLOAT,
                        customer_id BIGINT NOT NULL,
                        FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE order_items (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             quantity INTEGER NOT NULL,
                             price_at_time FLOAT NOT NULL,
                             order_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (product_id) REFERENCES products(id)
);