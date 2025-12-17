-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         9.5.0 - MySQL Community Server - GPL
-- SO del servidor:              Linux
-- HeidiSQL Versión:             12.13.0.7147
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para reparaya_db
CREATE DATABASE IF NOT EXISTS `reparaya_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `reparaya_db`;

-- Volcando estructura para tabla reparaya_db.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `direccion` text,
  `notas` text,
  `foto_ine_frente` varchar(255) DEFAULT NULL,
  `foto_ine_reverso` varchar(255) DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `servicios_totales` int DEFAULT '0',
  `ultima_visita` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.cliente: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.configuracion_empresa
CREATE TABLE IF NOT EXISTS `configuracion_empresa` (
  `clave` varchar(100) NOT NULL,
  `valor` text,
  `descripcion` text,
  `categoria` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.configuracion_empresa: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.detalle_factura
CREATE TABLE IF NOT EXISTS `detalle_factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factura_id` int NOT NULL,
  `producto_id` int DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `cantidad` int DEFAULT '1',
  `precio_unitario` decimal(10,2) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalle_factura` (`factura_id`),
  KEY `fk_detalle_producto` (`producto_id`),
  CONSTRAINT `fk_detalle_factura` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`id`),
  CONSTRAINT `fk_detalle_producto` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.detalle_factura: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.factura
CREATE TABLE IF NOT EXISTS `factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `servicio_id` varchar(50) NOT NULL,
  `cliente_id` varchar(50) NOT NULL,
  `fecha_emision` date NOT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `iva` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) NOT NULL,
  `metodo_pago` varchar(50) DEFAULT NULL,
  `estado` varchar(20) DEFAULT 'Pendiente',
  `pdf_path` varchar(255) DEFAULT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_servicio` (`servicio_id`),
  KEY `fk_factura_cliente` (`cliente_id`),
  KEY `fk_factura_usuario` (`usuario_id`),
  CONSTRAINT `fk_factura_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_factura_servicio` FOREIGN KEY (`servicio_id`) REFERENCES `servicio` (`id`),
  CONSTRAINT `fk_factura_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.factura: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.gasto
CREATE TABLE IF NOT EXISTS `gasto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `categoria` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `comprobante` varchar(255) DEFAULT NULL,
  `monto` decimal(10,2) NOT NULL,
  `usuario_id` int NOT NULL,
  `notas` text,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_gasto_usuario` (`usuario_id`),
  CONSTRAINT `fk_gasto_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.gasto: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.historial_servicio
CREATE TABLE IF NOT EXISTS `historial_servicio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `servicio_id` varchar(50) NOT NULL,
  `usuario_id` int NOT NULL,
  `accion` varchar(50) DEFAULT NULL,
  `descripcion` text,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_servicio` (`servicio_id`),
  KEY `fk_historial_usuario` (`usuario_id`),
  CONSTRAINT `fk_historial_servicio` FOREIGN KEY (`servicio_id`) REFERENCES `servicio` (`id`),
  CONSTRAINT `fk_historial_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.historial_servicio: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `proveedor` varchar(100) DEFAULT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `stock` int DEFAULT '0',
  `stock_minimo` int DEFAULT '5',
  `precio_costo` decimal(10,2) DEFAULT NULL,
  `precio_venta` decimal(10,2) DEFAULT NULL,
  `descripcion` text,
  `ubicacion` varchar(100) DEFAULT NULL,
  `codigo_barras` varchar(100) DEFAULT NULL,
  `fecha_ingreso` date DEFAULT (curdate()),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.producto: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.servicio
CREATE TABLE IF NOT EXISTS `servicio` (
  `id` varchar(50) NOT NULL,
  `cliente_id` varchar(50) NOT NULL,
  `tipo_dispositivo` varchar(50) DEFAULT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `numero_serie` varchar(100) DEFAULT NULL,
  `color` varchar(30) DEFAULT NULL,
  `contrasena_dispositivo` varchar(100) DEFAULT NULL,
  `falla` text NOT NULL,
  `observaciones` text,
  `prioridad` varchar(20) DEFAULT 'Normal',
  `estado` varchar(30) DEFAULT 'Registrado',
  `tecnico_asignado` int DEFAULT NULL,
  `costo_total` decimal(10,2) DEFAULT NULL,
  `anticipo` decimal(10,2) DEFAULT '0.00',
  `fecha_ingreso` date NOT NULL,
  `fecha_estimada_entrega` date DEFAULT NULL,
  `fecha_completado` date DEFAULT NULL,
  `fecha_entrega` date DEFAULT NULL,
  `foto_frente` varchar(255) DEFAULT NULL,
  `foto_trasera` varchar(255) DEFAULT NULL,
  `foto_pantalla` varchar(255) DEFAULT NULL,
  `otras_fotos` text,
  `usuario_registro` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_cliente` (`cliente_id`),
  KEY `idx_estado` (`estado`),
  KEY `idx_fecha` (`fecha_ingreso`),
  KEY `fk_servicio_tecnico` (`tecnico_asignado`),
  KEY `fk_servicio_usuario` (`usuario_registro`),
  CONSTRAINT `fk_servicio_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_servicio_tecnico` FOREIGN KEY (`tecnico_asignado`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_servicio_usuario` FOREIGN KEY (`usuario_registro`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.servicio: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reparaya_db.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `rol` varchar(20) DEFAULT NULL,
  `estado` varchar(20) DEFAULT 'Activo',
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla reparaya_db.usuario: ~0 rows (aproximadamente)
INSERT INTO `usuario` (`id`, `username`, `password_hash`, `nombre`, `correo`, `rol`, `estado`, `fecha_registro`) VALUES
	(1, 'admin', 'admin123', 'Admin General', 'admin@reparaya.com', 'Administrador', 'Activo', '2025-12-17 09:29:24'),
	(2, 'mario_tec', 'tec123', 'Mario González', 'mario@reparaya.com', 'Técnico', 'Activo', '2025-12-17 09:29:24'),
	(3, 'ana_recep', 'recep123', 'Ana Martínez', 'ana@reparaya.com', 'Recepcionista', 'Activo', '2025-12-17 09:29:24');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
