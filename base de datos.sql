Drop database if exists tiendadigital;
CREATE DATABASE tiendadigital;
USE tiendadigital;
DROP TABLE IF EXISTS categoria;

CREATE TABLE categoria (
    idCategoria INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS producto;
CREATE TABLE producto (
    idProducto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(600),
    precio DECIMAL(10,2) NOT NULL,
    imagen VARCHAR(255),
    idCategoria INT NOT NULL,
    FOREIGN KEY (idCategoria) REFERENCES categoria(idCategoria)
);

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    idUsuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    password VARCHAR(128) NOT NULL,
    rol ENUM('cliente', 'admin') NOT NULL
);

DROP TABLE IF EXISTS pedido;
CREATE TABLE pedido (
    idPedido INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    fecha DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES usuario(idUsuario)
);

DROP TABLE IF EXISTS articulos_pedido;
CREATE TABLE  articulos_pedido (
    idPedido INT NOT NULL,
    idProducto INT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (idPedido, idProducto),
    FOREIGN KEY (idPedido) REFERENCES pedido(idPedido),
    FOREIGN KEY (idProducto) REFERENCES producto(idProducto)
);

INSERT INTO categoria (nombre) VALUES ('Bebidas frias');
INSERT INTO categoria (nombre) VALUES ('Bebidas calientes');
INSERT INTO categoria (nombre) VALUES ('Snacks');
INSERT INTO categoria (nombre) VALUES ('Chocolatinas');

INSERT INTO producto (nombre, descripcion, precio, imagen, idCategoria) VALUES 
('Coca-Cola Lata', 'Refresco de cola en lata 33cl', 1.00, '1.jpg', 1),
('Coca-Cola Zero Lata', 'Refresco sin azúcar 33cl', 1.00, '2.jpg', 1),
('Fanta Naranja', 'Refresco sabor naranja 33cl', 1.00, '3.jpg', 1),
('Fanta Limón', 'Refresco sabor limón 33cl', 1.00, '4.jpg', 1),
('Nestea', 'Té frío sabor limón 33cl', 1.20, '5.jpg', 1),
('Aquarius Limón', 'Bebida isotónica limón 33cl', 1.20, '6.jpg', 1),
('Aquarius Naranja', 'Bebida isotónica naranja 33cl', 1.20, '7.jpg', 1),
('Sprite', 'Refresco lima-limón 33cl', 1.00, '8.jpg', 1),
('Agua Mineral', 'Agua embotellada 50cl', 0.80, '9.jpg', 1),
('Coca-Cola Light Lata', 'Refresco cola bajo en calorías 33cl', 1.00, '10.jpg', 1);

INSERT INTO producto (nombre, descripcion, precio, imagen, idCategoria) VALUES
('Café Solo', 'Café negro, sin leche ni azúcar', 1.50, '1.jpg', 2),
('Café con Leche', 'Café con leche caliente', 1.80, '2.jpg', 2),
('Café Cortado', 'Café con un chorrito de leche', 1.60, '3.jpg', 2),
('Té Negro', 'Té caliente, sabor intenso', 1.50, '4.jpg', 2),
('Té Verde', 'Té caliente sabor suave', 1.50, '5.jpg', 2),
('Chocolate Caliente', 'Bebida de cacao caliente', 2.00, '6.jpg', 2),
('Capuchino', 'Café con espuma de leche', 2.20, '7.jpg', 2),
('Manzanilla', 'Infusión de manzanilla', 1.50, '8.jpg', 2),
('Poleo Menta', 'Infusión de poleo menta', 1.50, '9.jpg', 2),
('Café Americano', 'Café largo suave', 1.50, '10.jpg', 2);

INSERT INTO producto (nombre, descripcion, precio, imagen, idCategoria) VALUES
('Patatas Fritas Bolsa', 'Patatas fritas clásicas 150g', 1.00, '1.jpg', 3),
('Patatas Sabor Jamón', 'Patatas fritas sabor jamón 150g', 1.00, '2.jpg', 3),
('Quicos', 'Maíz frito crujiente 160g', 1.20, '3.jpg', 3),
('Pipas Saladas', 'Pipas de girasol saladas 120g', 1.00, '4.jpg', 3),
('Cacahuetes', 'Cacahuetes tostados 400g', 3.20, '5.jpg', 3),
('Mix Frutos Secos', 'Mezcla de frutos secos 195g', 1.50, '6.jpg', 3),
('Patatas Sabor Queso', 'Patatas fritas sabor queso 150g', 1.00, '7.jpg', 3),
('Torreznos', 'Snacks crujientes de panceta 75g', 1.50, '8.jpg', 3),
('Almendras Saladas', 'Almendras tostadas saladas 128g', 1.50, '9.jpg', 3),
('Gusanitos', 'Snacks de maíz sabor queso 105g', 1.00, '10.jpg', 3);

INSERT INTO producto (nombre, descripcion, precio, imagen, idCategoria) VALUES
('KitKat', 'Barrita de chocolate con barquillo', 1.20, '1.jpg', 4),
('Snickers', 'Barrita de chocolate, cacahuetes y caramelo', 1.20, '2.jpg', 4),
('Milky Way', 'Barrita de chocolate y leche', 1.20, '3.jpg', 4),
('Mars', 'Barrita de chocolate y caramelo', 1.20, '4.jpg', 4),
('Twix', 'Barrita de galleta, caramelo y chocolate', 1.20, '5.jpg', 4),
('Kinder Bueno', 'Barrita con relleno de leche y avellanas', 1.20, '6.jpg', 4),
('Nestlé Crunch', 'Chocolate crujiente', 1.20, '7.jpg', 4),
('Huesitos', 'Barrita de chocolate y barquillo fino', 1.00, '8.jpg', 4),
('Kinder Chocolate', 'Mini barritas de chocolate con leche', 1.20, '9.jpg', 4),
('Lacasitos Tubo', 'Lentejas de chocolate', 1.00, '10.jpg', 4);

INSERT INTO usuario (nombre, password, rol) VALUES
('Juan', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'admin'),
('Ricardo', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'admin'),
('Zahira', 'a60b85d409a01d46023f90741e01b79543a3cb1ba048eaefbe5d7a63638043bf', 'cliente'),
('David', 'a60b85d409a01d46023f90741e01b79543a3cb1ba048eaefbe5d7a63638043bf', 'cliente'),
('Aroha', 'a60b85d409a01d46023f90741e01b79543a3cb1ba048eaefbe5d7a63638043bf', 'cliente');
