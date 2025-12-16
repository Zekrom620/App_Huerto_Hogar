-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-12-2025 a las 06:16:59
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `huerto_hogar`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `boletas`
--

CREATE TABLE `boletas` (
  `id` bigint(20) NOT NULL,
  `detalle_productos` varchar(5000) DEFAULT NULL,
  `estado` varchar(255) NOT NULL,
  `fecha_compra` datetime(6) NOT NULL,
  `total` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `boletas`
--

INSERT INTO `boletas` (`id`, `detalle_productos`, `estado`, `fecha_compra`, `total`, `user_id`) VALUES
(1, '[{\"id\":1,\"nombre\":\"Manzanas Fuji\",\"precio\":1200,\"imagen\":\"img/Manzanas Fuji.jpg\",\"qty\":3},{\"id\":3,\"nombre\":\"Plátanos Cavendish\",\"precio\":800,\"imagen\":\"img/Plátanos Cavendish.jpg\",\"qty\":4}]', 'Pendiente', '2025-12-03 01:30:36.000000', 6120, 5),
(2, '[{\"id\":8,\"nombre\":\"Quinua Orgánica\",\"precio\":3800,\"imagen\":\"img/Quinua Orgánica.jpg\",\"qty\":5}]', 'Pendiente', '2025-12-05 02:41:40.000000', 19000, 5),
(3, '- Manzanas Fuji: $1200\n- Naranjas Valencia: $1000\n- Plátanos Cavendish: $800\n- Zanahorias Orgánicas: $900\n- Espinacas Frescas: $700', 'Pendiente', '2025-12-11 02:32:58.000000', 4600, 0),
(4, '- Manzanas Fuji: $1200', 'Pendiente', '2025-12-11 03:32:24.000000', 1200, 0),
(5, '- Naranjas Valencia: $1000\n- Naranjas Valencia: $1000', 'Pendiente', '2025-12-11 03:42:25.000000', 2000, 0),
(6, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200', 'Pendiente', '2025-12-11 03:47:03.000000', 4800, 0),
(7, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Naranjas Valencia: $1000\n- Naranjas Valencia: $1000\n- Naranjas Valencia: $1000\n- Plátanos Cavendish: $800\n- Plátanos Cavendish: $800\n- Plátanos Cavendish: $800\n- Plátanos Cavendish: $800', 'Pendiente', '2025-12-11 03:47:15.000000', 11000, 0),
(8, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200', 'Pendiente', '2025-12-12 20:57:41.000000', 3600, 0),
(9, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200', 'Pendiente', '2025-12-12 21:10:07.000000', 3600, 0),
(10, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Naranjas Valencia: $1000', 'Pendiente', '2025-12-12 21:12:17.000000', 4600, 0),
(11, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200', 'Pendiente', '2025-12-12 21:35:38.000000', 3600, 0),
(12, '- Plátanos Cavendish: $800\n- Plátanos Cavendish: $800\n- Plátanos Cavendish: $800\n- Plátanos Cavendish: $800', 'Pendiente', '2025-12-12 21:36:22.000000', 3200, 0),
(14, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200', 'pendiente', '2025-12-12 21:56:50.000000', 3600, 0),
(15, '- Manzanas Fuji: $1200\n- Naranjas Valencia: $1000\n- Plátanos Cavendish: $800\n- Zanahorias Orgánicas: $900', 'pendiente', '2025-12-12 22:17:10.000000', 3900, 0),
(16, '- Manzanas Fuji: $1200\n- Manzanas Fuji: $1200\n- Zanahorias Orgánicas: $900\n- Zanahorias Orgánicas: $900\n- Espinacas Frescas: $700\n- Espinacas Frescas: $700', 'confirmado', '2025-12-12 22:28:45.000000', 5600, 0),
(17, '- Manzanas Fuji: $1200\n- Naranjas Valencia: $1000\n- Plátanos Cavendish: $800', 'pendiente', '2025-12-12 23:04:31.000000', 3000, 0),
(18, '- Manzanas Fuji: $1200\n- Naranjas Valencia: $1000\n- Zanahorias Orgánicas: $900\n- Espinacas Frescas: $700', 'confirmada', '2025-12-12 23:17:38.000000', 3800, 0),
(19, 'Naranjas Valencia x22', 'Pendiente', '2025-12-16 04:42:30.000000', 22000, 8),
(20, 'Manzanas Fuji x10', 'Pendiente', '2025-12-16 04:50:00.000000', 12000, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contactos`
--

CREATE TABLE `contactos` (
  `id` bigint(20) NOT NULL,
  `asunto` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `fecha_envio` datetime(6) DEFAULT NULL,
  `mensaje` varchar(1000) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `origen` varchar(255) DEFAULT NULL,
  `precio` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`id`, `categoria`, `codigo`, `descripcion`, `imagen`, `nombre`, `origen`, `precio`, `stock`) VALUES
(1, 'frutas-frescas', 'FR001', 'Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule. Perfectas para meriendas saludables o como ingrediente en postres. Se distinguen por su textura firme y su sabor equilibrado entre dulce y ácido.', 'uploads/manzana_fuji.png', 'Manzanas Fuji', 'Valle del Maule', 1200, 75),
(2, 'frutas-frescas', 'FR002', 'Jugosas y ricas en vitamina C, estas naranjas Valencia son ideales para zumos frescos y refrescantes. Cultivadas en condiciones climáticas óptimas que aseguran su dulzura y jugosidad.', 'uploads/naranja_valencia.png', 'Naranjas Valencia', 'Región de Coquimbo', 1000, 200),
(3, 'frutas-frescas', 'FR003', 'Plátanos maduros y dulces, perfectos para el desayuno o como snack energético. Estos plátanos son ricos en potasio y vitaminas, ideales para mantener una dieta equilibrada.', 'uploads/platano_cavendish.png', 'Plátanos Cavendish', 'Importación Justa', 800, 250),
(4, 'verduras-organicas', 'VR001', 'Zanahorias crujientes cultivadas sin pesticidas en la Región de O\'Higgins. Excelente fuente de vitamina A y fibra, ideales para ensaladas, jugos o como snack saludable.', 'uploads/zanahoria_organica.png', 'Zanahorias Orgánicas', 'Región de O\'Higgins', 900, 100),
(5, 'verduras-organicas', 'VR002', 'Espinacas frescas y nutritivas (bolsa de 500g), cultivadas bajo prácticas orgánicas que garantizan su calidad y valor nutricional, perfectas para ensaladas y batidos verdes.', 'uploads/espinacas_frescas.png', 'Espinacas Frescas', 'Productores Locales', 700, 80),
(6, 'verduras-organicas', 'VR003', 'Pimientos rojos, amarillos y verdes, ideales para salteados y platos coloridos. Ricos en antioxidantes y vitaminas, añaden un toque vibrante y saludable a cualquier receta.', 'uploads/pimientos_tricolores.png', 'Pimientos Tricolores', 'Central de Abastos', 1500, 120),
(7, 'productos-organicos', 'PO001', 'Miel pura y orgánica producida por apicultores locales. Rica en antioxidantes y con un sabor inigualable, perfecta para endulzar de manera natural tus comidas y bebidas. (Frasco 500g)', 'uploads/miel_organica.png', 'Miel Orgánica', 'Colmenares del Sur', 5000, 50),
(8, 'productos-organicos', 'PO002', 'Grano andino altamente nutritivo, procesado responsablemente para mantener sus beneficios saludables. Estos productos son perfectos para quienes buscan opciones alimenticias que aporten bienestar.', 'uploads/quinua_organica.png', 'Quinua Orgánica', 'Altiplano Chileno', 3800, 75),
(9, 'lacteos', 'PL001', 'Leche fresca de campo que conserva su frescura y sabor auténtico. Proviene de granjas locales dedicadas a la producción responsable y de calidad. Rica en calcio y nutrientes esenciales.', 'uploads/leche_entera.png', 'Leche Entera de Campo', 'Granja de Los Lagos', 1800, 90);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `comuna` varchar(255) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `region` varchar(255) NOT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `rut` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `comuna`, `contrasena`, `correo`, `nombre`, `region`, `rol`, `direccion`, `rut`) VALUES
(1, 'Providencia', '$2a$10$seo8wEgBaB0D04X7iGyz/uJ/.jdRPiG0ndoIjgwo6GdTUZxqn0WvO', 'mauricio@profesor.duoc.cl', 'Mauricio González', 'Santiago', 'administrador', NULL, ''),
(3, 'Santiago', '$2a$10$zVKbBMiraY8CHyp.NRiEPONto/qB8LQ8IYYMGsQSTmSEi90wLhM7W', 'sergio@gmail.com', 'sergio', 'Santiago', 'cliente', NULL, ''),
(5, 'Santiago', '$2a$10$AbPfntX2B1c9c3cFD9hhguELiCMdAH9vumUg4vG4qthzme.co8Yzy', 'suazo@gmail.com', 'pele suazo', 'Santiago', 'cliente', 'el roble 500', '15873489-4'),
(6, 'atacama', '$2a$10$UtOUqdkIhL0pzHZV73Er5.i4yaqf5Vicf.DLbgmwwNokDnpJxfjBe', 'bryan@gmail.com', 'bryan', 'atacama', 'cliente', 'atacama 123', '123456789'),
(7, 'santiago', '$2a$10$uCN9BRscBO96MtMsqvUS6OGU9sPuGYhsRzk4lt18XU60/EuRnmtOi', 'francisco@gmail.com', 'francisco', 'metropolitana', 'cliente', 'grancisco 1101', '57962581'),
(8, 'santiago', '$2a$10$yTTOAiuDOca7gZPRGULFhedRHDXzUgd1Qd9yuv24bQHHWqTdGQt.2', 'matias@gmail.com', 'matias', 'metropolitana', 'cliente', 'duoc', '57946358');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `boletas`
--
ALTER TABLE `boletas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `contactos`
--
ALTER TABLE `contactos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKglp83g62hbf9v7i6cibudd0p6` (`codigo`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKqs4hlsdf7l1k1u4on057c0949` (`correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `boletas`
--
ALTER TABLE `boletas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `contactos`
--
ALTER TABLE `contactos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
