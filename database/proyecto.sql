-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-10-2019 a las 16:24:17
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
  `nombre` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tarea` int(100) NOT NULL,
  `inicial` varchar(6) COLLATE utf8_spanish_ci DEFAULT NULL,
  `hora_inicial` varchar(6) COLLATE utf8_spanish_ci DEFAULT NULL,
  `final` varchar(6) COLLATE utf8_spanish_ci DEFAULT NULL,
  `hora_final` varchar(6) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tiempo` varchar(8) COLLATE utf8_spanish_ci DEFAULT NULL,
  `horas` int(10) DEFAULT NULL,
  `min` int(10) DEFAULT NULL,
  `valor` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `valor_hora` varchar(5) COLLATE utf8_spanish_ci NOT NULL,
  `valor_minuto` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
  `fallas` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
  `paro` varchar(40) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
  `numero_op` varchar(12) COLLATE utf8_spanish_ci NOT NULL,
  `id` int(11) NOT NULL,
  `tiempo_descanso` int(11) NOT NULL,
  `motivo_descanso` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `fecha` varchar(12) COLLATE utf8_spanish_ci NOT NULL,
  `hora` varchar(10) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `motivo_paro`
--

INSERT INTO `motivo_paro` (`ddd`, `numero_op`, `id`, `tiempo_descanso`, `motivo_descanso`, `fecha`, `hora`) VALUES
(1, '5224', 37, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:13'),
(2, '5224', 282, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:13'),
(3, '5224', 285, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:20'),
(4, '5224', 284, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:19'),
(5, '5224', 220, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:26'),
(6, '5224', 143, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:26'),
(7, '5224', 260, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:32'),
(8, '5224', 223, 5, 'PAUSAS.ACTIVAS', '2019/10/10', '08:32'),
(9, '5224', 260, 5, '5S', '2019/10/10', '08:38'),
(10, '5224', 223, 5, '5S', '2019/10/10', '08:38'),
(11, '5224', 143, 5, '5S', '2019/10/10', '08:45'),
(12, '5224', 220, 5, '5S', '2019/10/10', '08:45'),
(13, '5224', 114, 5, 'PAUSAS.ACTIVAS', '2019/10/11', '08:00'),
(14, '5302', 114, 15, 'RECESO.HIDRATACION.Y.ALIMENTACION', '2019/10/11', '10:24'),
(15, '5308', 284, 5, 'RECESO.HIDRATACION.Y.ALIMENTACION', '2019/10/11', '12:05'),
(16, '5308', 285, 5, 'RECESO.HIDRATACION.Y.ALIMENTACION', '2019/10/11', '12:05'),
(17, '5287', 37, 15, 'RECESO.HIDRATACION.Y.ALIMENTACION', '2019/10/16', '10:09'),
(18, '5287', 282, 15, 'RECESO.HIDRATACION.Y.ALIMENTACION', '2019/10/16', '10:10'),
(19, '5287', 285, 15, 'RECESO.HIDRATACION.Y.ALIMENTACION', '2019/10/16', '11:10'),
(20, '5287', 284, 15, 'RECESO.HIDRATACION.Y.ALIMENTACION', '2019/10/16', '11:11');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operador`
--

CREATE TABLE `operador` (
  `id` int(11) NOT NULL,
  `numero_op` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nombre` varchar(70) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tarea` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cantidad` int(10) NOT NULL,
  `no_conforme` varchar(11) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cantidad_fallas` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `inicial` mediumtext COLLATE utf8_spanish_ci DEFAULT NULL,
  `hora_inicial` varchar(6) COLLATE utf8_spanish_ci DEFAULT NULL,
  `final` mediumtext COLLATE utf8_spanish_ci DEFAULT NULL,
  `hora_final` varchar(6) COLLATE utf8_spanish_ci DEFAULT NULL,
  `eficencia` varchar(12) COLLATE utf8_spanish_ci DEFAULT NULL,
  `eficacia` varchar(12) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id` int(11) NOT NULL,
  `numero_id` varchar(12) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cod_producto` int(7) DEFAULT NULL,
  `descripcion` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id`, `numero_id`, `cod_producto`, `descripcion`, `cantidad`) VALUES
(22, '5290', 362, 'SABANA PLANA 2.00*2.00 NO ESTERIL MT5A', 210),
(23, '5261', 301, 'PAQ.HEMODINAMIA NO.6 NO EST (200300)', 208),
(24, '5282', 301, 'PAQ.HEMODINAMIA NO.6 NO EST (200300)', 240),
(25, '5283', 2698, 'PAQ.CARDIOVASCULAR NO.9 NO EST (202699)', 90),
(26, '5273', 2809, 'PAQ.QUIRURGICO PTERGIO NO EST (202808)', 30),
(27, '5274', 522, 'KIT CATETER CENTAR NO EST (200563)', 132),
(28, '5275', 215, 'PAQ.LIPOSUCCION NO.1 NO EST (200216)', 64),
(29, '5276', 787, 'PAQ.PLATIA NO ESTERIL (200786)', 210),
(30, '5224', 2310, 'CAMPO HEMODIN.3.40*2.00(PAQ)LC0', 480),
(31, '5302', 396, 'CAMPO QUIRURGICO 11.45*1.50 SN R', 1248),
(33, '5300', 112, 'CAMPO AUX 0.80*0.65/ADH (PAQ)MT5A', 4072),
(34, '5308', 112, 'CAMPO AUX 0.80*0.65/ADH (PAQ)MT5A', 2832),
(35, '5287', 1576, 'PAQ.HEMODINAMIA NO.6 NO EST (201585)', 320),
(36, '5002', 362, 'BATA MANGA JAPONESA MT5A\r\n', 800);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `cargo` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
  `numero_op` int(11) NOT NULL,
  `tarea` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `cantidadpentiente` int(11) DEFAULT NULL,
  `extandar` varchar(12) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tarea`
--

INSERT INTO `tarea` (`id`, `numero_op`, `tarea`, `cantidadpentiente`, `extandar`) VALUES
(15, 5002, 'PEGAR TIRAS CON SILICONA', 0, '00:36');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiempo`
--

CREATE TABLE `tiempo` (
  `id` int(100) NOT NULL,
  `tarea` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `tiempo_est_SEG` int(5) DEFAULT NULL,
  `tiempo_esp_H` varchar(5) COLLATE utf8_spanish_ci DEFAULT NULL,
  `t_paro` int(11) DEFAULT NULL,
  `t_real` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
  `nomusuario` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `apeusuario` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cedula` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `rol` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cargo` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
  MODIFY `ddd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `tarea`
--
ALTER TABLE `tarea`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `tiempo`
--
ALTER TABLE `tiempo`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
