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
constraint relation_equipo_user	foreign key (equipo_id) references equipo (id)
ON DELETE CASCADE

);


CREATE TABLE  estrategia (

id int NOT NULL  PRIMARY KEY,
estado varchar(50) NOT NULL,
fechaInicio varchar(50) not null,
fechaFin    varchar(50) not null,
estrategia_id INT NOT NULL,		
constraint relation_equipo_estrategia foreign key (estrategia_id)	references equipo (id)
ON DELETE CASCADE
);

CREATE TABLE tarea (

id int NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
rtc varchar(25) NOT NULL,
tipo varchar(25) NOT NULL,
estadoInicio varchar(50) NOT NULL,
estadoFinal varchar(50) NOT NULL

);
CREATE TABLE estrategia_tarea(    

tarea_id int NOT NULL,
estrategia_id int NOT NULL,

primary Key (tarea_id,estrategia_id),

CONSTRAINT fk_tarea FOREIGN KEY (tarea_id) REFERENCES estrategia (id) ON DELETE CASCADE,
CONSTRAINT fk_estrategia FOREIGN KEY (estrategia_id) REFERENCES tarea (id) ON DELETE CASCADE


);


CREATE TABLE daily (

id int NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
fecha date NOT NULL,
daily_id INT NOT NULL,
constraint relation_estrategia_daily foreign key (daily_id) references estrategia (id)
ON DELETE CASCADE
    
);

CREATE TABLE daily_tarea (
daily_id int  NOT NULL,
tarea_id int  NOT NULL,
estadoActual varchar(50) NOT NULL,
primary Key (daily_id,tarea_id),
CONSTRAINT fk_tarea FOREIGN KEY (tarea_id) REFERENCES daily (id) ON DELETE CASCADE,
CONSTRAINT fk_daily FOREIGN KEY (daily_id) REFERENCES tarea (id) ON DELETE CASCADE
);

INSERT INTO equipo (name) values ('admin');
select * from user;
INSERT INTO user (username,password,equipo_id) values ('marcos','1234',1);
INSERT INTO user (username,password,equipo_id) values ('alejandro','1234',1);
INSERT INTO user (username,password,equipo_id) values ('ismael','1234',1);


