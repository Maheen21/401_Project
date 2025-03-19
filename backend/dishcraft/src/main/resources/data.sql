-- This file is used to populate the database with initial data
-- It is run automatically when the application starts
INSERT INTO dietary_restrictions (id, name) VALUES (1, 'Gluten-Free');
INSERT INTO dietary_restrictions (id, name) VALUES (2, 'Dairy-Free');
INSERT INTO dietary_restrictions (id, name) VALUES (3, 'Nut-Free');
INSERT INTO dietary_restrictions (id, name) VALUES (4, 'Vegetarian');
INSERT INTO dietary_restrictions (id, name) VALUES (5, 'Vegan');
INSERT INTO dietary_restrictions (id, name) VALUES (6, 'Kosher');
INSERT INTO dietary_restrictions (id, name) VALUES (7, 'Halal');

-- 2. ingredients table data

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (1, 'Tomato', 'Vegetable', 'Fresh red tomato', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (2, 'Mozzarella Cheese', 'Dairy', 'Creamy mozzarella cheese', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (3, 'Basil', 'Herb', 'Fresh basil leaves', 'SUB');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (4, 'Olive Oil', 'Oil', 'Extra virgin olive oil', 'SUB');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (5, 'Wheat Bread', 'Grain', 'Traditional wheat bread containing gluten', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (6, 'Almond Milk', 'Dairy Alternative', 'Plant-based milk made from almonds', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (7, 'Chicken Breast', 'Meat', 'Boneless, skinless chicken breast', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (8, 'Eggs', 'Poultry Product', 'Chicken eggs', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (9, 'Beef', 'Meat', 'Lean beef cut', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (10, 'Tofu', 'Soy Product', 'Firm tofu, protein-rich', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (11, 'Vegan Cheese', 'Dairy Alternative', 'Plant-based cheese substitute', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (12, 'Pork Belly', 'Meat', 'Rich pork belly, not permitted in Halal/Kosher diets', 'MAIN');

INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (13, 'Banana', 'Fruit', 'Ripe banana', 'SUB');

--3. ingredient_dietary_restrictions join table data

-- Mozzarella Cheese is dairy product and not suitable for vegans

INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (2, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (2, 5);

-- Wheat Bread contains gluten and is not suitable for gluten-free diets
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (5, 1);

-- Almond Milk contains a nut and is not suitable for nut-free diets
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (6, 3);

-- Chicken Breast is a meat product and not suitable for vegetarians and vegans
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (7, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (7, 5);

-- Eggs are a poultry product and not suitable for vegetarians and vegans
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (8, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (8, 5);

-- Beef is a meat product and not suitable for vegetarians and vegans
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (9, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (9, 5);

-- pork belly is a meat product and not suitable for vegetarians and vegans and not permitted in Halal/Kosher diets
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (12, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (12, 5);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (12, 6);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (12, 7);

-- 4. recipes table data
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Caprese Salad', 'A simple Italian salad with fresh ingredients.',
        'Slice tomatoes and mozzarella cheese. Layer with basil, drizzle olive oil, and season with salt and pepper.',
        10, 'http://example.com/images/caprese.jpg');

--5. recipe_ingredients join table data

-- Recipe 1: Caprese Salad
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (1, 1, 1, 2, 'pieces', TRUE);   -- Tomato
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (2, 1, 2, 100, 'grams', TRUE);    -- Mozzarella Cheese
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (3, 1, 3, 5, 'leaves', TRUE);     -- Basil
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (4, 1, 4, 2, 'tablespoons', FALSE);  -- Olive Oil