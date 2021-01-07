DROP DATABASE IF EXISTS panaderia_db;
CREATE DATABASE panaderia_db CHARACTER SET utf8 COLLATE utf8_general_ci;

USE panaderia_db;

DROP TABLE IF EXISTS administrador;

CREATE TABLE administrador(

    id_admin VARCHAR(10) PRIMARY KEY, -- 4 digitos
    nombre VARCHAR(20) NOT NULL,
    ape_pat VARCHAR(20) NOT NULL,
    ape_mat VARCHAR(20) NOT NULL,
    usuario VARCHAR(20) NOT NULL,
    contrasenia VARCHAR(20) NOT NULL

);

INSERT INTO administrador VALUES ('1234','JORGE ARTURO','GIMENEZ','GARCÍA','admin','admin');

DROP TABLE IF EXISTS sucursal;

CREATE TABLE sucursal(

    id_sucursal VARCHAR(10) PRIMARY KEY, -- 4 digitos
    descripcion VARCHAR(20) NOT NULL,
    direccion VARCHAR(20) NOT NULL,
    telefono VARCHAR(20) NOT NULL, -- 10 digitos
    usuario VARCHAR(20) NOT NULL,
    contrasenia VARCHAR(20) NOT NULL

);

INSERT INTO sucursal VALUES ('A123','NORTE','CENTRAL DEL NORTE','5512345678','norte','123');
INSERT INTO sucursal VALUES ('A456','SUR','CENTRAL DEL SUR','5512345678','sur','456');
INSERT INTO sucursal VALUES ('A789','ESTE','CENTRAL ESTE','5545729056','este','789');


DROP TABLE IF EXISTS empleado;

CREATE TABLE empleado(

    id_empleado VARCHAR(10), -- 4 digitos
    nombre VARCHAR(20) NOT NULL,
    ape_pat VARCHAR(20) NOT NULL,
    ape_mat VARCHAR(20) NOT NULL, 
    direccion VARCHAR(20) NOT NULL,
    telefono VARCHAR(20) NOT NULL,-- 10 digitos
    rol VARCHAR(20) NOT NULL,
    id_sucursal VARCHAR(20) NOT NULL,
    
    /*definicion de llave primaria y secundaria*/
    PRIMARY KEY (id_empleado),
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)

);

INSERT INTO empleado VALUES ('B123','JUAN','GOMEZ','ACEVEDO','AV. TE COL. GRANJAS','5590875432','PANADERO','A123');
INSERT INTO empleado VALUES ('B456','CESAR ARMANDO','MUNGUIA','TORRES','AV. TE COL. GRANJAS','5523908765','DISTRIBUIDOR','A456');
INSERT INTO empleado VALUES ('B789','JULIO','CHAVEZ','RODRIGUEZ','AV. TE COL. GRANJAS','5689097634','VENDEDOR','A789');