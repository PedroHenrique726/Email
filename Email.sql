create database email;

CREATE TABLE Usuario 
( 
 ID INT PRIMARY KEY NOT NULL,
 Email VARCHAR(30) NOT NULL,
 Nome VARCHAR(20) NOT NULL,
 Senha VARCHAR(20) NOT NULL,
 Contatos VARCHAR(100)
); 

CREATE TABLE Mensagens 
( 
 De VARCHAR(30) NOT NULL,
 Para VARCHAR(60) NOT NULL,
 Assunto VARCHAR(30) NOT NULL,
 Mensagem VARCHAR(1000) NOT NULL
); 

alter table usuario add Contatos varchar(100);

alter table usuario rename column Username to Nome;

CREATE TABLE Mensagens 
( 
 De VARCHAR(30) NOT NULL,
 Para VARCHAR(30) NOT NULL,
 Assunto VARCHAR(30) NOT NULL,
 Mensagem VARCHAR(1000) NOT NULL,
 ID INT PRIMARY KEY,
 UsuarioID INT, 
  FOREIGN KEY (UsuarioID) REFERENCES Usuario (id)
); 

CREATE TABLE Contatos 
( 
 Username VARCHAR(20) NOT NULL,
 Email VARCHAR(30) PRIMARY KEY NOT NULL
); 

CREATE TABLE Rascunho 
( 
 Email VARCHAR(30) PRIMARY KEY,
 De VARCHAR(30) NOT NULL,
 Para VARCHAR(30) NOT NULL,
 Assunto VARCHAR(50) NOT NULL,
 Mensagem VARCHAR(1000) NOT NULL
);