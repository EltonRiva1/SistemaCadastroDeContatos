-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 18-Jul-2018 às 02:05
-- Versão do servidor: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sistema-cadastro`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `area`
--

CREATE TABLE IF NOT EXISTS `area` (
  `codigoDaArea` int(11) NOT NULL AUTO_INCREMENT,
  `descricaoDaArea` varchar(100) NOT NULL,
  `dataCadastro` date NOT NULL,
  `data_desativacao` date DEFAULT NULL,
  PRIMARY KEY (`codigoDaArea`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=42 ;

--
-- Extraindo dados da tabela `area`
--

INSERT INTO `area` (`codigoDaArea`, `descricaoDaArea`, `dataCadastro`, `data_desativacao`) VALUES
(15, 'Informática', '2018-04-11', NULL),
(17, 'Construção', '2018-04-01', NULL),
(21, 'Segurança', '2018-04-03', NULL),
(23, 'Alimentos', '2018-04-12', NULL),
(26, 'Educação', '2018-04-05', '2018-04-25'),
(27, 'Fábrica', '2018-04-11', '2018-04-24'),
(28, 'Pedagogia', '2018-04-02', NULL),
(29, 'Engenharia', '2018-04-23', NULL),
(30, 'Esporte', '2018-04-05', NULL),
(31, 'Almoxarifado', '2018-04-24', NULL),
(32, 'Estoque', '2018-04-23', '2018-04-24'),
(33, 'Teste4', '2018-04-06', '2018-04-24'),
(34, 'Educação123', '2018-04-17', NULL),
(35, 'Logistica', '2018-04-24', '2018-04-24'),
(36, 'Financeiro', '2018-04-04', NULL),
(37, 'Academia', '2018-04-11', '2018-04-24'),
(38, 'Natação', '2018-04-05', '2018-04-24'),
(39, 'Teste', '2018-04-12', '2018-04-24'),
(40, 'Contabilidade', '2018-04-04', NULL),
(41, 'Oficina', '2018-04-05', NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `contato`
--

CREATE TABLE IF NOT EXISTS `contato` (
  `codigoDoContato` int(11) NOT NULL AUTO_INCREMENT,
  `nomeDoContato` varchar(100) NOT NULL,
  `codigoCPF` varchar(14) NOT NULL,
  `telResidencial` varchar(13) NOT NULL,
  `telCelular` varchar(14) NOT NULL,
  `emailContato` varchar(50) NOT NULL,
  `dataCadastro` date NOT NULL,
  `data_desativacao` date DEFAULT NULL,
  PRIMARY KEY (`codigoDoContato`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Extraindo dados da tabela `contato`
--

INSERT INTO `contato` (`codigoDoContato`, `nomeDoContato`, `codigoCPF`, `telResidencial`, `telCelular`, `emailContato`, `dataCadastro`, `data_desativacao`) VALUES
(1, 'Elton Riva Moura Lima', '021.668.275-43', '(71)3011-2498', '(71)99646-9564', 'notleamil@hotmail.com', '2018-04-22', NULL),
(8, 'Cilano da Silva Matos', '372.968.433-73', '(71)3256-8898', '(71)98878-5616', 'email@email.com', '2018-04-05', NULL),
(10, 'Fulano da Silva Sauro', '868.131.282-05', '(71)3256-9548', '(77)97978-7897', 'email2@email.com', '2018-04-04', NULL),
(11, 'Ramon Rodrigues', '053.383.977-73', '(71)3259-8885', '(71)98874-5162', 'email6@email.com', '2018-03-01', NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `empresa`
--

CREATE TABLE IF NOT EXISTS `empresa` (
  `codigoDaEmpresa` int(11) NOT NULL AUTO_INCREMENT,
  `nomeDaEmpresa` varchar(100) NOT NULL,
  `codigoCNPJ` varchar(18) NOT NULL,
  `dataCadastro` date NOT NULL,
  `data_desativacao` date DEFAULT NULL,
  PRIMARY KEY (`codigoDaEmpresa`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20 ;

--
-- Extraindo dados da tabela `empresa`
--

INSERT INTO `empresa` (`codigoDaEmpresa`, `nomeDaEmpresa`, `codigoCNPJ`, `dataCadastro`, `data_desativacao`) VALUES
(2, 'Solutis', '00.124.587/9791-31', '2018-04-15', '2018-04-15'),
(3, 'Itaú', '34.434.343/4366-77', '2018-04-11', '2018-04-24'),
(15, 'Cedro Tech', '92.068.223/0001-09', '2018-04-03', NULL),
(16, 'Caixa Econômica', '27.757.075/0001-78', '2018-04-05', NULL),
(17, 'Drograsil', '73.511.404/0001-13', '2018-04-06', NULL),
(18, 'ABC', '34.167.061/0001-60', '2018-04-25', NULL),
(19, 'Academia', '87.328.354/0001-48', '2018-04-04', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
