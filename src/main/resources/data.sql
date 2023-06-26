-- Categorias
INSERT INTO
    `restaurant_powerup`.`category` (`id`, `description`, `name`)
VALUES
    ('1', 'Platos ligeros para abrir el apetito al inicio de la comida.', 'Entradas'),
    ('2', 'Variedad de carnes, aves, pescados y platos vegetarianos destacados.', 'Platos principales'),
    ('3', 'Mezclas frescas de vegetales con aderezos y ingredientes complementarios.', 'Ensaladas'),
    ('4', 'Preparaciones líquidas calientes con caldos, vegetales, carnes o pescados.', 'Sopas'),
    ('5', 'Destacan carnes rojas, pollo, cerdo y otras aves.', 'Carnes y aves'),
    ('6', 'Platos con variedad de productos del mar, pescados y mariscos.', 'Mariscos y pescados'),
    ('7', 'Variedad de platos de pasta fresca y pizzas.', 'Pastas y pizzas'),
    ('8', 'Platos de preparación rápida y fácil de consumir.', 'Comidas Rapidas'),
    ('9', 'Opciones líquidas como agua, refrescos, jugos, té, café, vino, cerveza.', 'Bebidas');

-- Restaurantes
INSERT INTO
    `restaurant_powerup`.`restaurant` (id, address, id_owner, name, nit, phone, url_logo)
VALUES
    (1, 'Calle Principal 123, Ciudad', 1, 'Burger Paradise', '123456789', '123-456-7890', 'https://ejemplo.com/burgerparadise.jpg'),
    (2, 'Avenida Central 456, Ciudad', 2, 'Sabor Colombiano', '987654321', '987-654-3210', 'https://ejemplo.com/saborcolombiano.jpg');

-- Relacionar Empleados con Restaurante
INSERT INTO
    `restaurant_powerup`.`employee_restaurant` (`id_employee`, `id_restaurant`)
VALUES
    ('4', '1'),
    ('5', '2');

-- Platos
INSERT INTO
    `restaurant_powerup`.`dish` (active, description, id_restaurant, name, price, url_image, id_category)
VALUES
    (1, 'Deliciosa hamburguesa con carne jugosa, queso derretido y aderezos', 1, 'Hamburguesa Clásica', 15000, 'https://ejemplo.com/hamburguesa.jpg', 2),
    (1, 'Papas fritas crujientes y doradas', 1, 'Papas Fritas', 6500, 'https://ejemplo.com/papasfritas.jpg', 1),
    (1, 'Refrescante ensalada mixta con lechuga, tomate, pepino y aderezo', 1, 'Ensalada Mixta', 11000, 'https://ejemplo.com/ensaladamixta.jpg', 3),
    (1, 'Sopa de pollo con fideos y verduras', 1, 'Sopa de Pollo', 9000, 'https://ejemplo.com/sopadepollo.jpg', 4),
    (1, 'Tierna carne asada acompañada de arroz y frijoles', 2, 'Carne Asada', 35000, 'https://ejemplo.com/carneasada.jpg', 5),
    (1, 'Cazuela de mariscos con arroz', 2, 'Cazuela de Mariscos', 45000, 'https://ejemplo.com/cazuelamariscos.jpg', 6),
    (1, 'Deliciosos ravioles rellenos de queso con salsa de tomate', 2, 'Ravioles de Queso', 25000, 'https://ejemplo.com/ravioles.jpg', 7),
    (1, 'Refrescante jugo de frutas naturales', 2, 'Jugo de Frutas Naturales', 9500, 'https://ejemplo.com/jugodefrutas.jpg', 8),
    (1, 'Bandeja Paisa, una combinación de sabores colombianos', 2, 'Bandeja Paisa', 25000, 'https://ejemplo.com/bandejapaisa.jpg', 2),
    (1, 'Ensalada de aguacate, tomate y cebolla', 1, 'Ensalada de Aguacate', 15000, 'https://ejemplo.com/ensaladaaguacate.jpg', 3),
    (1, 'Ajiaco, una sopa tradicional de la región andina', 2, 'Ajiaco', 19000, 'https://ejemplo.com/ajiaco.jpg', 4),
    (1, 'Bistec a la criolla, acompañado de arroz, frijoles y plátano', 2, 'Bistec a la Criolla', 22000, 'https://ejemplo.com/bisteccriolla.jpg', 5),
    (1, 'Ceviche de camarón con limón y ají', 2, 'Ceviche de Camarón', 32000, 'https://ejemplo.com/cevichecamaron.jpg', 6),
    (1, 'Lasagna de carne, gratinada con queso derretido', 2, 'Lasagna de Carne', 25000, 'https://ejemplo.com/lasagnacarne.jpg', 7),
    (1, 'Bebida refrescante Colombiana', 2, 'Limonada con Panela', 6500, 'https://ejemplo.com/refrescolulo.jpg', 8);