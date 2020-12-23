DROP DATABASE IF EXISTS panaderia_db;
CREATE DATABASE panaderia_db CHARACTER SET utf8 COLLATE utf8_general_ci;

USE panaderia_db;

DROP TABLE IF EXISTS administrador;

CREATE TABLE administrador(

    id_admin VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    ape_pat VARCHAR(20) NOT NULL,
    ape_mat VARCHAR(20) NOT NULL,
    usuario VARCHAR(20) NOT NULL,
    contrasenia VARCHAR(20) NOT NULL

);

INSERT INTO administrador VALUES ('12345','JORGE ARTURO','GIMENEZ','GARCÍA','admin','admin');

DROP TABLE IF EXISTS sucursal;

CREATE TABLE sucursal(

    id_sucursal VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(20) NOT NULL,
    direccion VARCHAR(20) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    usuario VARCHAR(20) NOT NULL,
    contrasenia VARCHAR(20) NOT NULL

);

INSERT INTO sucursal VALUES ('A1234','NORTE','CENTRAL DEL NORTE','5512345678','norte','123');
INSERT INTO sucursal VALUES ('A456','SUR','CENTRAL DEL SUR','5512345678','sur','456');