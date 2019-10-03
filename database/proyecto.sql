-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-10-2019 a las 14:32:17
-- Versión del servidor: 10.4.6-MariaDB
-- Versión de PHP: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `analista`
--

CREATE TABLE `analista` (
  `id` int(11) NOT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `tarea` int(100) NOT NULL,
  `inicial` varchar(6) DEFAULT NULL,
  `hora_inicial` varchar(6) DEFAULT NULL,
  `final` varchar(6) DEFAULT NULL,
  `hora_final` varchar(6) DEFAULT NULL,
  `tiempo` varchar(8) DEFAULT NULL,
  `horas` int(10) DEFAULT NULL,
  `min` int(10) DEFAULT NULL,
  `valor` varchar(10) DEFAULT NULL,
  `valor_hora` varchar(5) NOT NULL,
  `valor_minuto` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `analista`
--

INSERT INTO `analista` (`id`, `nombre`, `tarea`, `inicial`, `hora_inicial`, `final`, `hora_final`, `tiempo`, `horas`, `min`, `valor`, `valor_hora`, `valor_minuto`) VALUES
(0, 'jhon', 0, '2019/0', '9:59', '2019/0', '9:59', '0:0', 0, 0, '0', '4444', 75),
(1, 'carlos', 3, '2019/0', '1:33', '2019/0', '3:37', '2:4', 0, 4, '0', '4444', 75);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cantidaderror`
--

CREATE TABLE `cantidaderror` (
  `fallas` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cantidaderror`
--

INSERT INTO `cantidaderror` (`fallas`) VALUES
('maquina.mala'),
('producto.defectuoso');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `motivo`
--

CREATE TABLE `motivo` (
  `id` int(11) NOT NULL,
  `paro` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `motivo`
--

INSERT INTO `motivo` (`id`, `paro`) VALUES
(14, '5S'),
(8, 'ACCIDENTE.DE.TRABAJO'),
(42, 'ALISTAMIENTO.MAQUINAS.CONFECCION'),
(31, 'ALISTAMIENTO.PARA.ROLLOS.DE.CAMILLA.CORT'),
(30, 'ALMACENAR.PRODUCTO.EN.ESTIBA'),
(15, 'ASISTENCIA.MEDICA'),
(4, 'AUSENCIA.DE.FLUIDO.ELECTRICO'),
(43, 'CALAMIDAD.DOMESTICA'),
(57, 'CAMPANA.DE.BIENESTAR.SOCIAL'),
(38, 'CAPACITACION.EMPRESA'),
(3, 'CAPACITACION.METODOS.DE.INGENIERIA'),
(37, 'COMISION.POR.LA.EMPRESA'),
(36, 'DESCANSO.POR.VOTACION'),
(33, 'DESCANSO.REMUNERADO'),
(60, 'DESMONTE.DE.ROLLO.POR.CALIDAD'),
(5, 'ELABORACION.DE.MUESTRAS'),
(6, 'FALTA.DE.TRABAJO.E.INSUMOS.PLANTA'),
(26, 'FALTA.ENTREGA.INSUMOS.ABASTECIMIENTO'),
(41, 'FUMIGACION'),
(51, 'HORAS.COMPENSATORIAS'),
(48, 'HORAS.COMPENSATORIAS.LIDER.DE.PROCESO'),
(20, 'INCAPACIDAD.POR.ACCIDENTE.DE.TRABAJO'),
(19, 'INCAPACIDAD.POR.ENFERMEDAD.GENERAL'),
(7, 'INDUCCION.PROCESO.PRODUCTIVO'),
(56, 'LEY.MARIA'),
(32, 'LICENCIA.NO.REMUNERADA'),
(28, 'LICENCIA.POR.LUTO'),
(34, 'LICENCIA.POR.MATERNIDAD'),
(58, 'LLEGADA.TARDE.DEL.PERSONAL'),
(1, 'MANTENIMIENTO.CORRECTIVO'),
(50, 'PAGO.DE.HORAS.PERMISO.LIDER.DEL.PROCESO'),
(24, 'PARO.*.PEGAS.ENRREDO.O.CALIDAD.ELASTICO'),
(9, 'PATINAR.EN.PLANTA'),
(17, 'PAUSAS.ACTIVAS'),
(39, 'PERMISO.DE.LACTANCIA'),
(22, 'PERMISO.EPS'),
(21, 'PERMISO.PERSONAL'),
(44, 'PERSONAL.TRABAJA.EN.ABASTECIMIENTO'),
(54, 'PUERTA.INTERNA.ABIERTA.TARDE'),
(11, 'REALIZAR.INVENTARIO'),
(53, 'REALIZAR.PREINVENTARIO'),
(47, 'RECEPCION.DE.MATERIAS.PRIMAS'),
(18, 'RECESO.HIDRATACION.Y.ALIMENTACION'),
(27, 'REEMPLAZO.PERSONAL.EN.OTRO.PROCESO'),
(49, 'REGISTRO.DE.TIEMPO.DE.PERSONAL.INDIRECTO'),
(13, 'REPROCESO'),
(45, 'REUNION.COMITE.CONVIVENCIA'),
(25, 'REUNION.COPASST'),
(55, 'REUNION.DE.BRIGADA'),
(2, 'REUNION.DE.PRODUCCION'),
(46, 'REUNION.SEGURIDAD.Y.SALUD.EN.EL.TRABAJO'),
(52, 'SUBIR.ROLLOS.DE.TELA.AL.2.PISO'),
(40, 'SUSPENSION'),
(59, 'TEMBLOR'),
(16, 'TIEMPO.HIDRATACION'),
(23, 'TIEMPO.PROCESO.GESTION.HUMANA'),
(12, 'TIEMPOS.DE.INGENIERIA'),
(29, 'TRANSPORTE.INTERNO.LLEGA.TARDE'),
(35, 'VACACIONES'),
(10, 'VALIDACION.MUESTREO.DE.PRODUCTO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `motivo_paro`
--

CREATE TABLE `motivo_paro` (
  `ddd` int(11) NOT NULL,
  `numero_op` varchar(12) NOT NULL,
  `id` int(11) NOT NULL,
  `tiempo_descanso` int(11) NOT NULL,
  `motivo_descanso` varchar(50) NOT NULL,
  `fecha` varchar(12) NOT NULL,
  `hora` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operador`
--

CREATE TABLE `operador` (
  `zxc` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `numero_op` varchar(12) DEFAULT NULL,
  `nombre` varchar(70) DEFAULT NULL,
  `tarea` varchar(50) DEFAULT NULL,
  `cantidad` varchar(10) NOT NULL,
  `no_conforme` int(11) DEFAULT NULL,
  `cantidad_fallas` varchar(20) DEFAULT NULL,
  `inicial` text DEFAULT NULL,
  `hora_inicial` varchar(6) DEFAULT NULL,
  `final` text DEFAULT NULL,
  `hora_final` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `operador`
--

INSERT INTO `operador` (`zxc`, `id`, `numero_op`, `nombre`, `tarea`, `cantidad`, `no_conforme`, `cantidad_fallas`, `inicial`, `hora_inicial`, `final`, `hora_final`) VALUES
(1, 34, '5175-1', 'adriana', NULL, '0', NULL, NULL, '', '', '', ''),
(2, 114, '5175-1', 'erika', NULL, '0', NULL, NULL, '', '', '', ''),
(3, 262, '5175-1', 'yoxana', NULL, '0', NULL, NULL, '', '', '', ''),
(4, 882, '5175-1', 'juan david', NULL, '0', NULL, NULL, NULL, '', '', ''),
(5, 34, '5173-6', 'adriana', NULL, '0', NULL, NULL, NULL, NULL, '', ''),
(6, 114, '5173-6', 'erika', NULL, '0', NULL, NULL, NULL, '', '', ''),
(7, 262, '5173-6', 'yoxanas', NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL),
(8, 882, '5173-6', 'juan david', NULL, '0', NULL, NULL, NULL, '', '', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id` int(11) NOT NULL,
  `numero_id` varchar(12) DEFAULT NULL,
  `cod_producto` int(7) DEFAULT NULL,
  `descripcion` varchar(50) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id`, `numero_id`, `cod_producto`, `descripcion`, `cantidad`) VALUES
(20, '5175-1', 101640, 'PAQ CIRUGIA GENERAL NO 14 (201641)', 528),
(21, '5173-6', 101642, 'PAQ CIRUGIA GENERAL NO 14 (301643)', 350);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` varchar(15) NOT NULL,
  `cargo` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `cargo`) VALUES
('1', 'administrador'),
('2', 'analista'),
('3', 'operador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea`
--

CREATE TABLE `tarea` (
  `id` int(11) NOT NULL,
  `tarea` varchar(40) NOT NULL,
  `cantidadpentiente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tarea`
--

INSERT INTO `tarea` (`id`, `tarea`, `cantidadpentiente`) VALUES
(4, 'armar contenido paquete', 504),
(5, 'envolver 2.50*1.40 cubremesa', 528),
(6, 'envolver 2.00*1.50 cubremesa', 350),
(7, 'empacar y sellar paquete', 528);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiempo`
--

CREATE TABLE `tiempo` (
  `id` int(100) NOT NULL,
  `tarea` varchar(10) NOT NULL,
  `tiempo_est_SEG` int(5) DEFAULT NULL,
  `tiempo_esp_H` varchar(5) DEFAULT NULL,
  `t_paro` int(11) DEFAULT NULL,
  `t_real` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiempo`
--

INSERT INTO `tiempo` (`id`, `tarea`, `tiempo_est_SEG`, `tiempo_esp_H`, `t_paro`, `t_real`, `cantidad`) VALUES
(40, 'cosita', 12, '6.666', 5, 12, 2000),
(41, 'gorro', 12, '5', 4, 50, 1500);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(10) NOT NULL,
  `nomusuario` varchar(50) DEFAULT NULL,
  `apeusuario` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `cedula` varchar(50) DEFAULT NULL,
  `rol` varchar(15) DEFAULT NULL,
  `cargo` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nomusuario`, `apeusuario`, `password`, `cedula`, `rol`, `cargo`) VALUES
(1, 'Mainco', 'Health Care', '$2y$10$JTX89Bc2pfha7Xs5wVrWjeORFgzt7yU0SqyHYjf3TY1XVzOI9OKJG', '123', '1', 'administrador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `valor`
--

CREATE TABLE `valor` (
  `valor_hora` int(4) NOT NULL,
  `valor_minuto` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `valor`
--

INSERT INTO `valor` (`valor_hora`, `valor_minuto`) VALUES
(4444, 75);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `analista`
--
ALTER TABLE `analista`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tarea` (`tarea`),
  ADD KEY `hora_final` (`hora_final`),
  ADD KEY `hora_inicial` (`hora_inicial`),
  ADD KEY `inicial` (`inicial`),
  ADD KEY `final` (`final`);

--
-- Indices de la tabla `cantidaderror`
--
ALTER TABLE `cantidaderror`
  ADD KEY `fallas` (`fallas`);

--
-- Indices de la tabla `motivo`
--
ALTER TABLE `motivo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `paro` (`paro`);

--
-- Indices de la tabla `motivo_paro`
--
ALTER TABLE `motivo_paro`
  ADD PRIMARY KEY (`ddd`),
  ADD KEY `id` (`id`);

--
-- Indices de la tabla `operador`
--
ALTER TABLE `operador`
  ADD PRIMARY KEY (`zxc`),
  ADD KEY `id` (`id`);

--
-- Indices de la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cantidad` (`cantidad`) USING HASH,
  ADD KEY `numero_id` (`numero_id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cargo` (`cargo`);

--
-- Indices de la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tarea_id` (`tarea`);

--
-- Indices de la tabla `tiempo`
--
ALTER TABLE `tiempo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tarea` (`tarea`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Roles_Usuarios` (`rol`);

--
-- Indices de la tabla `valor`
--
ALTER TABLE `valor`
  ADD KEY `valor_hora` (`valor_hora`),
  ADD KEY `valor_minuto` (`valor_minuto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `motivo`
--
ALTER TABLE `motivo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT de la tabla `motivo_paro`
--
ALTER TABLE `motivo_paro`
  MODIFY `ddd` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `operador`
--
ALTER TABLE `operador`
  MODIFY `zxc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `tarea`
--
ALTER TABLE `tarea`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tiempo`
--
ALTER TABLE `tiempo`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
