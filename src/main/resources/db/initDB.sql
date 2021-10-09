DROP TABLE Empleado IF EXISTS;
DROP TABLE Proyecto IF EXISTS;

CREATE TABLE Empleado (
  id         	INTEGER IDENTITY PRIMARY KEY,
  nombre	 	VARCHAR(40),
  direccion		VARCHAR(30)
);
CREATE INDEX Empleado_nombre ON Empleado (nombre);

CREATE TABLE Proyecto (
	id		INTEGER IDENTITY PRIMARY KEY,
	nombre	VARCHAR(30),
	estado  VARCHAR(10),
	empleado_id INTEGER NOT NULL
);
ALTER TABLE Proyecto ADD CONSTRAINT fk_Empleado_Proyecto FOREIGN KEY (empleado_id) REFERENCES Empleado (id);
/*
 Un empleado supervisa uno o más proyectos y un proyecto es supervisado por un único empleado
 */