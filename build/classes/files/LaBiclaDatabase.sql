DROP DATABASE IF EXISTS la_bicla_db;
/*CREATE DATABASE la_bicla_db;*/
CREATE DATABASE la_bicla_db CHARACTER SET utf8 COLLATE utf8_general_ci;
USE la_bicla_db;

/*ALTER DATABASE la_bicla_db CHARACTER SET utf8 COLLATE utf8_general_ci;*/
/*ALTER DATABASE la_bicla_db CHARACTER SET latin1 COLLATE latin1_spanish_ci;*/
/*ALTER DATABASE la_bicla_db CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;*/
-- Drop Tables
        
DROP TABLE IF EXISTS users; 
   


CREATE TABLE users (
                            
	email VARCHAR(100) NOT NULL PRIMARY KEY,
                                            
	password VARCHAR(100) NOT NULL,

	firstname VARCHAR(100) NOT NULL,

	lastname VARCHAR(100) NOT NULL


--	role VARCHAR(50) NOT NULL,

--	estatus VARCHAR(50) NOT NULL,

--	clave_activacion VARCHAR(50),

--	password_es_temporal BOOLEAN NOT NULL,

--	vigencia_password INTEGER NOT NULL,

--	ultima_actualizacion_password DATE NOT NULL

            
);

DROP TABLE IF EXISTS products;

CREATE TABLE products (
	
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(250) NOT NULL,
	brand VARCHAR(100) NOT NULL,
	price DECIMAL(7,2) NOT NULL,
	quantity INTEGER NOT NULL,
	image VARCHAR(50) NOT NULL
);

INSERT INTO users VALUES ("admin@novalidserver.net", "passw0rd", "Admin", "");
INSERT INTO users VALUES ("guillermart@gmail.com", "passw0rd", "Guillermo", "Martinez");
INSERT INTO users VALUES ("luisa.lane@gmail.com", "passw0rd", "Luisa", "Lane");
INSERT INTO users VALUES ("maryjane.watson@gmail.com", "passw0rd", "Mary Jane", "Watson");
INSERT INTO users VALUES ("tony.stark@gmail.com", "passw0rd", "Tony", "Stark");
INSERT INTO users VALUES ("ivan_alamos@outlook.com", "ivndot", "Ivan", "Alamos");

-- insert into usuarios values ("admin@novalidserver.net", "passw0rd", "Admin", "", "ADMIN", "ACTIVO", null, false, 0, curdate());
-- insert into usuarios values ("guillermart@gmail.com", "passw0rd", "Guillermo", "Martinez" ,"SOCIO", "ACTIVO", null, false, 0, curdate());

INSERT INTO products VALUES( 1, "Diablos Infernales", "Diablos para carga...", "Benotto",50.00, 25,"diablos.jpg" );
INSERT INTO products VALUES( 2, "Asiento", "Asiento para bicicleta deportiva...", "Benotto",300.00, 12,"asiento.jpg" );
INSERT INTO products VALUES( 3, "Luces", "Luces de neon parpadenates", "Phillips",150.00, 50,"luces.jpg" );
INSERT INTO products VALUES( 4, "Llanta", "Llanta bicicleta monta&ntilde;a rodada 20", "Tornel",200.00, 2,"llanta_rodada20.jpg" );
INSERT INTO products VALUES( 5, "Llanta", "Llanta bicicleta monta&ntilde;a rodada 28", "Tornel",230.00, 4,"llanta_rodada28.jpg" );
INSERT INTO products VALUES( 6, "Rin", "Rin cromado para bicileta rodada 26", "Patita",140.00, 1,"rin_rodada26.jpg" );
INSERT INTO products VALUES( 7, "Cadena", "Cadena de medio eslabon", "Shimano",500.00, 5,"cadena.jpg" );


-- INSERT INTO products VALUES( 8, "Asiento Aero", "Asiento aerodinamico", "Shimano",750.00, 5 , "asiento2.jpg");




  