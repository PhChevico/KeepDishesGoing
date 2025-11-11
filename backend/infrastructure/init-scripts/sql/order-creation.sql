-- Create schema
CREATE SCHEMA IF NOT EXISTS orders;

-- ===========================================================
-- Restaurant Projection Table
-- ===========================================================
CREATE TABLE IF NOT EXISTS orders.restaurant_projection (
                                                            id UUID PRIMARY KEY,
                                                            name VARCHAR(255) NOT NULL,
                                                            email VARCHAR(255),
                                                            street VARCHAR(255),
                                                            number INT,
                                                            postal_code INT,
                                                            city VARCHAR(255),
                                                            country VARCHAR(255),
                                                            cuisine_type VARCHAR(100),
                                                            default_time_preparation INT,
                                                            picture VARCHAR(255),
                                                            price_range VARCHAR(50)
);

-- ===========================================================
-- Dishes Table (matches DishJpaEntity)
-- ===========================================================
CREATE TABLE IF NOT EXISTS orders.dishes (
                                             id UUID PRIMARY KEY,
                                             name VARCHAR(255) NOT NULL,
                                             type_of_dish VARCHAR(50) NOT NULL,
                                             description TEXT,
                                             price NUMERIC(10,2) NOT NULL,
                                             picture VARCHAR(255),
                                             dish_status VARCHAR(50) NOT NULL,
                                             food_menu_id UUID,
                                             restaurant_id UUID NOT NULL REFERENCES orders.restaurant_projection(id) ON DELETE CASCADE
);

-- ===========================================================
-- Dish ↔ FOOD_TAG relationship (Enum Collection)
-- ===========================================================
CREATE TABLE IF NOT EXISTS orders.dish_food_tags (
                                                     dish_id UUID NOT NULL REFERENCES orders.dishes(id) ON DELETE CASCADE,
                                                     tag VARCHAR(50) NOT NULL,
                                                     PRIMARY KEY (dish_id, tag)  -- composite key instead of food_tag_id
);


-- ===========================================================
-- Example Sample Data
-- ===========================================================
INSERT INTO orders.restaurant_projection (
    id, name, email, street, number, postal_code, city, country, cuisine_type,
    default_time_preparation, picture, price_range
)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Pizza Palace', 'info@pizzapalace.com',
     'Main Street', 1, 2000, 'Antwerp', 'Belgium', 'ITALIAN', 30, 'pizza.jpg', 'REGULAR'),

    ('22222222-2222-2222-2222-222222222222', 'Sushi World', 'contact@sushiworld.com',
     'Sushi Street', 5, 1000, 'Brussels', 'Belgium', 'JAPANESE', 45, 'sushi.jpg', 'PREMIUM');

-- ===========================================================
-- Sample Dishes
-- ===========================================================
INSERT INTO orders.dishes (id, name, type_of_dish, description, price, picture, dish_status, food_menu_id, restaurant_id)
VALUES
    ('d1111111-1111-1111-1111-111111111111', 'Margherita Pizza', 'MAIN', 'Classic pizza with tomato and mozzarella', 10.50, 'margherita.jpg', 'AVAILABLE', NULL, '11111111-1111-1111-1111-111111111111'),
    ('d2222222-2222-2222-2222-222222222222', 'Salmon Nigiri', 'MAIN', 'Fresh salmon over rice', 8.00, 'salmon.jpg', 'AVAILABLE', NULL, '22222222-2222-2222-2222-222222222222');


-- Table: baskets
CREATE TABLE IF NOT EXISTS orders.baskets (
                                              id UUID PRIMARY KEY,
                                              restaurant_id UUID NOT NULL,
                                              total_price NUMERIC(10,2),                -- better for money
                                              basket_status VARCHAR(20) DEFAULT 'ACTIVE',
                                              message TEXT,
                                              order_id UUID UNIQUE,
                                              anonymous_id UUID NOT NULL                -- added column
);




-- Table: basket_items
CREATE TABLE IF NOT EXISTS orders.basket_items (
                                                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- auto-generate UUID
                                                   basket_id UUID NOT NULL,
                                                   dish_id UUID NOT NULL,
                                                   price_at_addition DOUBLE PRECISION NOT NULL,
                                                   quantity INT NOT NULL,
                                                   CONSTRAINT fk_basket
                                                       FOREIGN KEY(basket_id)
                                                           REFERENCES orders.baskets(id)
                                                           ON DELETE CASCADE
);

-- Optional: index for faster lookups
CREATE INDEX IF NOT EXISTS idx_basket_items_basket_id
    ON orders.basket_items(basket_id);
