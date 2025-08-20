create database carwash;
use carwash;

create table usuario (
	idusuario int primary key auto_increment,
	nombre varchar(15) not null,
    clave varchar(10) not null
);

create table servicio (
	idservicio varchar(5) primary key,
    descripcion varchar(100) not null,
    costo float not null,
    observacion varchar(100)
);

create table vehiculo (
	placa varchar(18) primary key,
    descripcion varchar(18) not null,
    fabricante varchar(18) not null,
    modelo varchar(18) not null,
    cliente varchar(18) not null,
    dnicliente varchar(8) not null,
    brevete varchar(20) not null,
    observacion varchar(100)
);

create table personal (
	dni_persona varchar(8) primary key,
    nombre varchar(100) not null,
    direccion varchar(80) not null,
    movil varchar(9) not null,
    fecha_ingreso date not null,
    email varchar(50) not null,
    sueldo float not null
);

create table servicio_otorgado (
	idservicio_otorgado varchar(7) primary key,
    fecha date not null,
    documento varchar(20) not null,
    costo float not null,
    observacion varchar(100) not null,
    idservicio varchar(5) not null,
	CONSTRAINT FK_servicio_sotrogado FOREIGN KEY (idservicio) REFERENCES servicio(idservicio),
    placa varchar(18) not null,
	CONSTRAINT FK_vehiculo_sotorgado FOREIGN KEY (placa) REFERENCES vehiculo(placa),
    dni_persona varchar(8) not null,
	CONSTRAINT FK_personal_sotorgado FOREIGN KEY (dni_persona) REFERENCES personal(dni_persona)
)