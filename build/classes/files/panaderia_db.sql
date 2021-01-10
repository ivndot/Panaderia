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

    id_sucursal INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(20) NOT NULL,
    direccion VARCHAR(20) NOT NULL,
    telefono VARCHAR(20) NOT NULL, -- 10 digitos
    usuario VARCHAR(20) NOT NULL,
    contrasenia VARCHAR(20) NOT NULL,

    /*Definicion de la llave primaria*/
    PRIMARY KEY (id_sucursal)
);

ALTER TABLE sucursal AUTO_INCREMENT=100;

INSERT INTO sucursal (descripcion, direccion, telefono, usuario, contrasenia) VALUES ('NORTE','CENTRAL DEL NORTE','5512345678','norte','123');
INSERT INTO sucursal (descripcion, direccion, telefono, usuario, contrasenia) VALUES ('SUR','CENTRAL DEL SUR','5512345678','sur','456');
INSERT INTO sucursal (descripcion, direccion, telefono, usuario, contrasenia) VALUES ('ESTE','CENTRAL ESTE','5545729056','este','789');


DROP TABLE IF EXISTS empleado;

CREATE TABLE empleado(

    id_empleado INT NOT NULL AUTO_INCREMENT, 
    nombre VARCHAR(20) NOT NULL,
    ape_pat VARCHAR(20) NOT NULL,
    ape_mat VARCHAR(20) NOT NULL, 
    direccion VARCHAR(20) NOT NULL,
    telefono VARCHAR(20) NOT NULL,-- 10 digitos
    rol VARCHAR(20) NOT NULL,
    id_sucursal INT NOT NULL,
    
    /*definicion de llave primaria y secundaria*/
    PRIMARY KEY (id_empleado),
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)

);

ALTER TABLE empleado AUTO_INCREMENT=200;

INSERT INTO empleado (nombre, ape_pat, ape_mat, direccion, telefono, rol, id_sucursal) VALUES ('JUAN','GOMEZ','ACEVEDO','AV. TE COL. GRANJAS','5590875432','PANADERO', 100);
INSERT INTO empleado (nombre, ape_pat, ape_mat, direccion, telefono, rol, id_sucursal) VALUES ('CESAR ARMANDO','MUNGUIA','TORRES','AV. TE COL. GRANJAS','5523908765','DISTRIBUIDOR', 101);
INSERT INTO empleado (nombre, ape_pat, ape_mat, direccion, telefono, rol, id_sucursal) VALUES ('JULIO','CHAVEZ','RODRIGUEZ','AV. TE COL. GRANJAS','5689097634','VENDEDOR', 102);

DROP TABLE IF EXISTS venta;

CREATE TABLE venta(

    id_venta INT NOT NULL AUTO_INCREMENT, 
    id_sucursal int NOT NULL, -- 4 digitos
    fecha DATE NOT NULL,
    hora TIME NOT NULL, 
    
    /*definicion de llave primaria y secundaria*/
    PRIMARY KEY (id_venta),
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)

);

INSERT INTO venta (id_sucursal, fecha, hora) VALUES (100,"2020-12-08","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (100,"2020-12-08","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (100,"2020-12-08","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2020-12-08","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2020-12-08","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (102,"2020-12-08","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (102,"2020-12-08","13:30:00");

INSERT INTO venta (id_sucursal, fecha, hora) VALUES (100,"2021-01-08","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (100,"2021-01-09","15:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (100,"2021-01-10","11:00:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (100,"2021-01-11","15:00:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2021-01-03","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2021-01-05","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2021-01-07","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2021-01-11","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2021-01-12","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (101,"2021-01-10","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (102,"2021-01-10","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (102,"2021-01-04","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (102,"2021-01-03","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (102,"2021-01-07","13:30:00");
INSERT INTO venta (id_sucursal, fecha, hora) VALUES (102,"2021-01-09","13:30:00");


DROP TABLE IF EXISTS producto;

CREATE TABLE producto(

    id_producto INT NOT NULL AUTO_INCREMENT, 
    nombre VARCHAR(100) NOT NULL,
    precio FLOAT NOT NULL,
    
    /*definicion de llave primaria y secundaria*/
    PRIMARY KEY (id_producto)

);

INSERT INTO producto (nombre, precio) VALUES ("CUERNOS DE HIGO", 12.50);
INSERT INTO producto (nombre, precio) VALUES ("CUERNOS DE CHOCOLATE", 15.50);
INSERT INTO producto (nombre, precio) VALUES ("DONA DE CHOCOLATE", 20.00);

DROP TABLE IF EXISTS ventas_producto;

CREATE TABLE ventas_producto(
    
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL, 

    /*definicion de llaves secundarias*/
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)

);

INSERT INTO ventas_producto VALUES (1,1,3);
INSERT INTO ventas_producto VALUES (1,2,4);
INSERT INTO ventas_producto VALUES (2,3,5);
INSERT INTO ventas_producto VALUES (3,1,3);
INSERT INTO ventas_producto VALUES (4,2,4);
INSERT INTO ventas_producto VALUES (5,3,5);
INSERT INTO ventas_producto VALUES (6,1,3);
INSERT INTO ventas_producto VALUES (7,2,4);
INSERT INTO ventas_producto VALUES (8,3,5);
INSERT INTO ventas_producto VALUES (9,1,3);
INSERT INTO ventas_producto VALUES (10,2,4);
INSERT INTO ventas_producto VALUES (11,3,5);
INSERT INTO ventas_producto VALUES (11,1,3);
INSERT INTO ventas_producto VALUES (12,2,4);
INSERT INTO ventas_producto VALUES (13,3,5);
INSERT INTO ventas_producto VALUES (14,1,3);
INSERT INTO ventas_producto VALUES (15,2,4);
INSERT INTO ventas_producto VALUES (16,3,5);
INSERT INTO ventas_producto VALUES (17,1,3);
INSERT INTO ventas_producto VALUES (18,2,4);
INSERT INTO ventas_producto VALUES (19,3,5);
INSERT INTO ventas_producto VALUES (20,1,3);
INSERT INTO ventas_producto VALUES (21,2,4);
INSERT INTO ventas_producto VALUES (22,3,5);

/*
SELECT sucursal.descripcion, venta.id_venta, venta.id_sucursal, venta.fecha, producto.id_producto, producto.precio, ventas_producto.cantidad 
FROM sucursal INNER JOIN venta ON sucursal.id_sucursal = venta.id_sucursal
INNER JOIN ventas_producto ON venta.id_venta = ventas_producto.id_venta 
INNER JOIN producto ON ventas_producto.id_producto = producto.id_producto WHERE MONTH(venta.fecha) = 1 AND YEAR(venta.fecha) = 2021;
*/
