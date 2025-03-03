CREATE DATABASE online_book_store;
USE online_book_store;

-- Tabel pentru utilizatori
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    address TEXT,
    role ENUM('admin', 'user') DEFAULT 'user',  -- Adăugăm roluri pentru utilizator și admin
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel pentru cărți
CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100),
    price DECIMAL(10, 2),
    category VARCHAR(50),
    availability BOOLEAN DEFAULT TRUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel pentru comenzi
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10, 2),
    status ENUM('pending', 'completed', 'cancelled') DEFAULT 'pending',  -- Statusul comenzii
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Tabel pentru detalii despre produsele din comandă (pentru a sprijini comenzile multiple)
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    book_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);
