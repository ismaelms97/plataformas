drop table equipo;
drop table user;
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
propiedad   varchar(25) not null,
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
primary Key (daily_id,tarea_id),
CONSTRAINT tarea_id FOREIGN KEY (tarea_id) REFERENCES tarea (id) ON DELETE CASCADE,
CONSTRAINT daily_id FOREIGN KEY (daily_id) REFERENCES daily (id) ON DELETE CASCADE
);

INSERT INTO equipo (name) values ('admin');
INSERT INTO equipo (name) values ('root');
INSERT INTO user (username,password,equipo_id) values ('marcos','1234',1);
INSERT INTO user (username,password,equipo_id) values ('alejandro','1234',1);
INSERT INTO user (username,password,equipo_id) values ('ismael','1234',1);
INSERT INTO user (username,password,equipo_id) values ('german','1234',1);
INSERT INTO user (username,password,equipo_id) values ('pepe','1234',2);



INSERT INTO estrategia (nombre,estado,fechaInicio,fechaFin,equipo_id) values ('estrategia 1','en Pausa','2019-03','2019-04',1);
INSERT INTO estrategia (nombre,estado,fechaInicio,fechaFin,equipo_id) values ('finalizada','Finalizada','2019-2-20','2019-2-27',1);

INSERT INTO tarea (tipo,prioridad,resumen,tama�o,complejidad,propiedad,peticionario,relevante,urgente,planificado) values ('normal','alta','En resumen...','4','media','propiedad mia','peticion',true,true,'planificado');
INSERT INTO tarea (tipo,prioridad,resumen,tama�o,complejidad,propiedad,peticionario,relevante,urgente,planificado) values ('normal','baja','En resumen...','6','alta','propiedad tuya','peticion',true,false,'planificado');

INSERT INTO estrategia_tarea (estadoInicio,estadoFinal,tarea_id,estrategia_id) values ('En Curso (desarrollo)','aceptaci�n a las pruebas',1,1);
INSERT INTO estrategia_tarea (estadoInicio,estadoFinal,tarea_id,estrategia_id) values ('Pte.Cuantificar','en an�lisis',2,1);

INSERT INTO daily (fecha,estrategia_id) values ('2019-03',1);

INSERT INTO daily_tarea (daily_id,tarea_id,estadoActual,subEstadoActual) values (1,1,'en curso','trabajando');
INSERT INTO daily_tarea (daily_id,tarea_id,estadoActual,subEstadoActual) values (1,2,'en curso','trabajando');


