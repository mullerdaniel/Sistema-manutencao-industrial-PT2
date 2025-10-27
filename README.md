# Sistema-manutencao-industrial-PT2
┏ ┓  ━  ┗ ┛  ┃

# Banco de Dados

* CREATE DATABASE manutencao_industrial;
* USE manutencao_industrial;

<br>
<br>

CREATE TABLE Fornecedor (<br>
id INT PRIMARY KEY AUTO_INCREMENT,<br>
nome VARCHAR(100) NOT NULL,<br>
cnpj VARCHAR(14) UNIQUE NOT NULL<br>
);

<br>

CREATE TABLE Material (<br>
id INT PRIMARY KEY AUTO_INCREMENT,<br>
nome VARCHAR(100) NOT NULL,<br>
unidade VARCHAR(20) NOT NULL, -- Ex.: kg, m, peça<br>
estoque DOUBLE NOT NULL<br>
);

<br>

CREATE TABLE Requisicao (<br>
id INT PRIMARY KEY AUTO_INCREMENT,<br>
setor VARCHAR(50) NOT NULL,<br>
dataSolicitacao DATE NOT NULL,<br>
status VARCHAR(20) NOT NULL -- PENDENTE / ATENDIDA / CANCELADA<br>
);

<br>

CREATE TABLE RequisicaoItem (<br>
idRequisicao INT NOT NULL,<br>
idMaterial INT NOT NULL,<br>
quantidade DOUBLE NOT NULL,<br>
PRIMARY KEY (idRequisicao, idMaterial),<br>
FOREIGN KEY (idRequisicao) REFERENCES Requisicao(id),<br>
FOREIGN KEY (idMaterial) REFERENCES Material(id)<br>
);

<br>

CREATE TABLE NotaEntrada (<br>
id INT PRIMARY KEY AUTO_INCREMENT,<br>
idFornecedor INT NOT NULL,<br>
dataEntrada DATE NOT NULL,<br>
FOREIGN KEY (idFornecedor) REFERENCES Fornecedor(id)<br>
);

<br>

CREATE TABLE NotaEntradaItem (<br>
idNotaEntrada INT NOT NULL,<br>
idMaterial INT NOT NULL,<br>
quantidade DOUBLE NOT NULL,<br>
PRIMARY KEY (idNotaEntrada, idMaterial),<br>
FOREIGN KEY (idNotaEntrada) REFERENCES NotaEntrada(id),<br>
FOREIGN KEY (idMaterial) REFERENCES Material(id)<br>
);
