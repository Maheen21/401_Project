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

INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES 
('Chicken Sandwich', 'A hearty sandwich with grilled chicken breast and fresh vegetables.',
 'Toast wheat bread, grill chicken breast, layer with tomato slices and drizzle olive oil. Assemble sandwich.',
 15, 'http://example.com/images/chicken_sandwich.jpg'),

('Vegan Delight', 'A satisfying vegan meal featuring tofu and vegan cheese.',
 'Pan-fry tofu, melt vegan cheese over it, garnish with fresh basil.',
 20, 'http://example.com/images/vegan_delight.jpg'),

('Pork Roast', 'Slow-roasted pork belly with crispy edges.',
 'Season pork belly and roast until crispy on the outside and tender on the inside.',
 90, 'http://example.com/images/pork_roast.jpg'),

('Beef Steak', 'A juicy beef steak seared to perfection.',
 'Season beef and sear in olive oil until desired doneness is reached.',
 25, 'http://example.com/images/beef_steak.jpg'),

('Egg Omelette', 'A classic omelette with fresh tomatoes.',
 'Beat eggs, pour into pan, add tomato slices, and fold when set.',
 10, 'http://example.com/images/egg_omelette.jpg'),

('Almond Smoothie', 'A refreshing smoothie made with almond milk and banana.',
 'Blend almond milk with a banana until smooth.',
 5, 'http://example.com/images/almond_smoothie.jpg');
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

-- Recipe 2: Chicken Sandwich
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (5, 2, 5, 2, 'slices', TRUE);     -- Wheat Bread
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (6, 2, 7, 150, 'grams', TRUE);    -- Chicken Breast
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (7, 2, 1, 1, 'piece', TRUE);      -- Tomato
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (8, 2, 4, 1, 'tablespoon', FALSE);  -- Olive Oil

-- Recipe 3: Vegan Delight
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (9, 3, 10, 200, 'grams', TRUE);   -- Tofu
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (10, 3, 11, 80, 'grams', TRUE);   -- Vegan Cheese
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (11, 3, 3, 3, 'leaves', TRUE);    -- Basil

-- Recipe 4: Pork Roast
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (12, 4, 12, 300, 'grams', TRUE);  -- Pork Belly

-- Recipe 5: Beef Steak
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (13, 5, 9, 250, 'grams', TRUE);   -- Beef
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (14, 5, 4, 1, 'tablespoon', FALSE);  -- Olive Oil

-- Recipe 6: Egg Omelette
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (15, 6, 8, 2, 'units', TRUE);     -- Eggs
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (16, 6, 1, 1, 'piece', TRUE);     -- Tomato

-- Recipe 7: Almond Smoothie
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (17, 7, 6, 250, 'ml', TRUE);      -- Almond Milk
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (18, 7, 13, 1, 'unit', TRUE);     -- Banana
