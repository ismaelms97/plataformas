drop table equipo;
drop table user;
drop table role;
drop table user_equipo_role;
drop table estrategia;
drop table tarea;
drop table estrategia_tarea;
drop table daily;
drop table daily_tarea;


CREATE TABLE  equipo (

id int NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
 name varchar(50) NOT NULL   
);

CREATE TABLE  user (

id int NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
username varchar(50) NOT NULL,
password varchar(100) NOT NULL,
equipo_id INT NOT NULL,
constraint equipo_id foreign key (equipo_id) references equipo (id)
ON DELETE CASCADE

);
CREATE TABLE  role (

id int NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
role varchar(50) NOT NULL

);

CREATE TABLE  user_equipo_role (

id_equipo int NOT NULL,
id_user int NOT NULL,
id_role int NOT NULL,

primary Key (id_equipo,id_user,id_role),

CONSTRAINT id_equipo FOREIGN KEY (id_equipo)  REFERENCES equipo (id) ON DELETE CASCADE, 
CONSTRAINT id_user FOREIGN KEY (id_user) REFERENCES user (id) ON DELETE CASCADE,
CONSTRAINT id_role FOREIGN KEY (id_role) REFERENCES role (id) ON DELETE CASCADE
    
);



CREATE TABLE  estrategia (

id int NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
nombre varchar(50) NOT NULL,
estado varchar(50) NOT NULL,
fechaInicio varchar(50) not null,
fechaFin    varchar(50) not null,
equipo_id INT NOT NULL,		
constraint equipo_id foreign key (equipo_id)	references equipo (id)
ON DELETE CASCADE
);

CREATE TABLE tarea (

id int NOT NULL  PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
tipo varchar(25) NOT NULL,
prioridad varchar(50) not null,
resumen   varchar(255) not null,
tama�o   varchar(4) not null,
complejidad   varchar(25) not null,
peticionario   varchar(25) not null,
relevante   boolean,
urgente   boolean,
planificado  varchar(25) not null

);

CREATE TABLE estrategia_tarea(    

estadoInicio varchar(50) NOT NULL,
estadoFinal varchar(50) NOT NULL,
tarea_id int NOT NULL,
estrategia_id int NOT NULL,

primary Key (tarea_id,estrategia_id),

CONSTRAINT tarea_id FOREIGN KEY (tarea_id)  REFERENCES tarea (id) ON DELETE CASCADE, 
CONSTRAINT estrategia_id FOREIGN KEY (estrategia_id) REFERENCES estrategia (id) ON DELETE CASCADE


);


CREATE TABLE daily (

id int NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
fecha varchar(20) NOT NULL,
estrategia_id INT NOT NULL,
constraint estrategia_id foreign key (estrategia_id) references estrategia (id)
ON DELETE CASCADE
    
);


CREATE TABLE daily_tarea (
daily_id int  NOT NULL,
tarea_id int  NOT NULL,
estadoActual varchar(50) NOT NULL,
subEstadoActual varchar (50) NOT NULL,
propiedad   varchar(25) not null,
primary Key (daily_id,tarea_id),
CONSTRAINT tarea_id FOREIGN KEY (tarea_id) REFERENCES tarea (id) ON DELETE CASCADE,
CONSTRAINT daily_id FOREIGN KEY (daily_id) REFERENCES daily (id) ON DELETE CASCADE
);

INSERT INTO equipo (name) values ('Equipo 1');
INSERT INTO equipo (name) values ('Equipo 2');

INSERT INTO user (username,password,equipo_id) values ('marcos','A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=',1);
INSERT INTO user (username,password,equipo_id) values ('alejandro','A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=',1);
INSERT INTO user (username,password,equipo_id) values ('ismael','A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=',1);
INSERT INTO user (username,password,equipo_id) values ('german','A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=',1);
INSERT INTO user (username,password,equipo_id) values ('pepe','A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=',2);

INSERT INTO role (role) values ('admin');
INSERT INTO role (role) values ('root');

INSERT INTO user_equipo_role (id_equipo,id_user,id_role) values (1,1,1);
INSERT INTO user_equipo_role (id_equipo,id_user,id_role) values (1,2,1);
INSERT INTO user_equipo_role (id_equipo,id_user,id_role) values (1,3,1);
INSERT INTO user_equipo_role (id_equipo,id_user,id_role) values (1,4,1);
INSERT INTO user_equipo_role (id_equipo,id_user,id_role) values (2,5,2);



