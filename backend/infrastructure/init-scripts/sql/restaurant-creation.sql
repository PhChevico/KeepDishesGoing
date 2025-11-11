CREATE SCHEMA IF NOT EXISTS restaurant;

-- =======================================
-- Table: Restaurant
-- =======================================
CREATE TABLE restaurant.restaurant (
                                       id UUID PRIMARY KEY,
                                       owner_id UUID NOT NULL,
                                       name VARCHAR(255) NOT NULL,
                                       email VARCHAR(255) NOT NULL,
                                       street VARCHAR(255) NOT NULL,
                                       number INT NOT NULL,
                                       postal_code INT NOT NULL,
                                       city VARCHAR(255) NOT NULL,
                                       country VARCHAR(255) NOT NULL,
                                       cuisine_type VARCHAR(50) NOT NULL,
                                       default_time_preparation INT NOT NULL,
                                       picture VARCHAR(255),
                                       price_range VARCHAR(50)
);

-- =======================================
-- Table: Food Menu
-- =======================================
CREATE TABLE restaurant.food_menus (
                                       id UUID PRIMARY KEY,
                                       restaurant_id UUID NOT NULL REFERENCES restaurant.restaurant(id) ON DELETE CASCADE
);

-- =======================================
-- Table: Dish
-- =======================================
CREATE TABLE restaurant.dishes (
                                   id UUID PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   type_of_dish VARCHAR(50) NOT NULL, -- Enum: TYPE_OF_DISH
                                   description TEXT,
                                   price NUMERIC(10,2) NOT NULL,
                                   picture VARCHAR(255),
                                   dish_status VARCHAR(50) NOT NULL,  -- Enum: DISH_STATUS
                                   food_menu_id UUID REFERENCES restaurant.food_menus(id) ON DELETE CASCADE,
                                   restaurant_id UUID NOT NULL REFERENCES restaurant.restaurant(id) ON DELETE CASCADE
);

-- =======================================
-- ElementCollection Table: dish_food_tags
-- =======================================
CREATE TABLE IF NOT EXISTS restaurant.dish_food_tags (
                                                         food_tag_id UUID PRIMARY KEY,
                                                         dish_id UUID NOT NULL REFERENCES restaurant.dishes(id) ON DELETE CASCADE, -- Changed to restaurant.dishes
                                                         tag VARCHAR(50) NOT NULL
);


-- =======================================
-- Table: restaurant_orders
-- =======================================
CREATE TABLE restaurant.restaurant_orders (
                                              order_id UUID PRIMARY KEY,
                                              street VARCHAR(255) NOT NULL,
                                              number INT NOT NULL,
                                              postal_code INT NOT NULL,
                                              city VARCHAR(255) NOT NULL,
                                              country VARCHAR(255) NOT NULL,
                                              restaurant_id UUID NOT NULL REFERENCES restaurant.restaurant(id) ON DELETE CASCADE,
                                              total_price NUMERIC(10, 2) NOT NULL,
                                              status VARCHAR(50) NOT NULL
);

-- =======================================
-- Table: order_lines
-- =======================================
CREATE TABLE restaurant.order_lines (
                                        id UUID PRIMARY KEY,
                                        dish_id UUID NOT NULL REFERENCES restaurant.dishes(id) ON DELETE CASCADE,
                                        price_at_addition NUMERIC(10, 2) NOT NULL,
                                        quantity INT NOT NULL,
                                        order_id UUID NOT NULL REFERENCES restaurant.restaurant_orders(order_id) ON DELETE CASCADE
);



-- =======================================
-- Sample Data
-- =======================================

-- Restaurants
INSERT INTO restaurant.restaurant (
    id, owner_id, name, email, street, number, postal_code, city, country, cuisine_type,
    default_time_preparation, picture, price_range
) VALUES
      ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
       'Bella Italia', 'contact@bellaitalia.com', 'Main Street', 12, 1000, 'Brussels',
       'Belgium', 'ITALIAN', 30, 'bella_italia.jpg', 'REGULAR'),

      ('22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
       'Tokyo Sushi', 'info@tokyosushi.jp', 'Sakura Avenue', 5, 1010, 'Antwerp',
       'Belgium', 'JAPANESE', 25, 'tokyo_sushi.jpg', 'REGULAR');

-- Food Menus
INSERT INTO restaurant.food_menus (id, restaurant_id) VALUES
                                                          ('f1111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111'),
                                                          ('f2222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222');

-- Dishes
INSERT INTO restaurant.dishes (
    id, name, type_of_dish, description, price, picture, dish_status, food_menu_id, restaurant_id
) VALUES
      ('d1111111-1111-1111-1111-111111111111', 'Margherita Pizza', 'MAIN',
       'Classic pizza with tomato, mozzarella, and basil', 10.50, 'margherita.jpg', 'AVAILABLE',
       'f1111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111'),

      ('d1111111-1111-1111-1111-111111111112', 'Spaghetti Carbonara', 'MAIN',
       'Spaghetti with creamy sauce, pancetta, and parmesan', 12.00, 'carbonara.jpg', 'AVAILABLE',
       'f1111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111'),

      ('d1111111-1111-1111-1111-111111111113', 'Tiramisu', 'DESSERT',
       'Classic Italian coffee-flavored dessert', 6.00, 'tiramisu.jpg', 'AVAILABLE',
       'f1111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111'),

      ('d2222222-2222-2222-2222-222222222221', 'Salmon Nigiri', 'MAIN',
       'Fresh salmon over rice', 8.00, 'salmon_nigiri.jpg', 'AVAILABLE',
       'f2222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222'),

      ('d2222222-2222-2222-2222-222222222222', 'California Roll', 'MAIN',
       'Crab, avocado, and cucumber roll', 7.50, 'california_roll.jpg', 'AVAILABLE',
       'f2222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222'),

      ('d2222222-2222-2222-2222-222222222223', 'Miso Soup', 'STARTER',
       'Traditional miso soup with tofu and seaweed', 3.50, 'miso_soup.jpg', 'AVAILABLE',
       'f2222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222');

