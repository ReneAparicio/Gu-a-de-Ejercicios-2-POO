DROP DATABASE IF EXISTS CarClean
CREATE DATABASE CarClean

CREATE TABLE Clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    vip BOOLEAN NOT NULL
);

CREATE TABLE Automotor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    client_id INT,
    FOREIGN KEY (client_id) REFERENCES Clientes(id)
);

CREATE TABLE Servicio (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL,
    precio DOUBLE NOT NULL,
    client_id INT,
    FOREIGN KEY (client_id) REFERENCES Clientes(id)
);