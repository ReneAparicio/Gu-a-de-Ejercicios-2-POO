CREATE DATABASE IF NOT EXISTS agenda_contactos_jsp;


USE agenda_contactos_jsp



CREATE TABLE contactos
 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    
nombre VARCHAR(255) NOT NULL,
    
telefono VARCHAR(20) NOT NULL,
  
  email VARCHAR(255) NOT NULL
);


INSERT INTO contactos (nombre, telefono, email) VALUES ('Aimee Rose', '1234-5678', 'rose@gmail.com');