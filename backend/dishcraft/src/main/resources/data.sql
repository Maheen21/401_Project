-- This file is used to populate the database with initial data
-- It is run automatically when the application starts

-- 1. dietary_restrictions table data
INSERT INTO dietary_restrictions (id, name) VALUES (1, 'Gluten-Free');
INSERT INTO dietary_restrictions (id, name) VALUES (2, 'Dairy-Free');
INSERT INTO dietary_restrictions (id, name) VALUES (3, 'Nut-Free');
INSERT INTO dietary_restrictions (id, name) VALUES (4, 'Vegetarian');
INSERT INTO dietary_restrictions (id, name) VALUES (5, 'Vegan');
INSERT INTO dietary_restrictions (id, name) VALUES (6, 'Kosher');
INSERT INTO dietary_restrictions (id, name) VALUES (7, 'Halal');

-- 2. ingredients table data (covering all ingredients from the 20 recipes)
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (1, 'spaghetti', 'Grain', 'Italian pasta made from wheat.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (2, 'pancetta', 'Meat', 'Cured pork belly.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (3, 'egg yolks', 'Protein', 'Yolks from chicken eggs.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (4, 'parmesan cheese', 'Dairy', 'Aged Italian cheese.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (5, 'chicken breast', 'Meat', 'Boneless chicken breast.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (6, 'soy sauce', 'Condiment', 'Fermented soybean sauce.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (7, 'bell peppers', 'Vegetable', 'Colorful sweet peppers.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (8, 'broccoli', 'Vegetable', 'Green cruciferous vegetable.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (9, 'flour', 'Grain', 'Ground wheat flour.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (10, 'butter', 'Dairy', 'Cream churned into fat.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (11, 'sugar', 'Sweetener', 'Granulated sweetener.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (12, 'chocolate chips', 'Sweetener', 'Small chocolate pieces.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (13, 'ground beef', 'Meat', 'Minced beef.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (14, 'taco seasoning', 'Spice', 'Blend of spices for tacos.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (15, 'tortillas', 'Grain', 'Flatbread for tacos.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (16, 'salsa', 'Condiment', 'Spicy tomato-based sauce.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (17, 'coconut milk', 'Dairy Alternative', 'Milk from coconut.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (18, 'curry paste', 'Spice', 'Spicy paste for curry.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (19, 'potatoes', 'Vegetable', 'Starchy root vegetable.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (20, 'carrots', 'Vegetable', 'Orange root vegetable.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (21, 'milk', 'Dairy', 'Cow’s milk.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (22, 'eggs', 'Protein', 'Whole chicken eggs.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (23, 'salmon fillets', 'Seafood', 'Fillets of salmon.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (24, 'lemon juice', 'Fruit', 'Juice from lemons.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (25, 'olive oil', 'Oil', 'Oil from olives.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (26, 'garlic', 'Spice', 'Aromatic bulb.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (27, 'pizza dough', 'Grain', 'Dough for pizza base.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (28, 'tomato sauce', 'Condiment', 'Sauce from tomatoes.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (29, 'mozzarella', 'Dairy', 'Soft Italian cheese.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (30, 'basil', 'Herb', 'Fresh basil leaves.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (31, 'lentils', 'Grain', 'Dried lentils.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (32, 'vegetable broth', 'Liquid', 'Broth from vegetables.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (33, 'onion', 'Vegetable', 'Bulb vegetable.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (34, 'cumin', 'Spice', 'Ground cumin seeds.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (35, 'apples', 'Fruit', 'Fresh apples.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (36, 'cinnamon', 'Spice', 'Ground cinnamon.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (37, 'romaine lettuce', 'Vegetable', 'Crisp lettuce.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (38, 'caesar dressing', 'Condiment', 'Creamy salad dressing.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (39, 'croutons', 'Grain', 'Toasted bread cubes.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (40, 'beef sirloin', 'Meat', 'Sirloin cut of beef.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (41, 'sour cream', 'Dairy', 'Fermented cream.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (42, 'mushrooms', 'Vegetable', 'Edible fungi.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (43, 'egg noodles', 'Grain', 'Flat wheat noodles.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (44, 'bananas', 'Fruit', 'Ripe bananas.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (45, 'fettuccine', 'Grain', 'Flat pasta.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (46, 'heavy cream', 'Dairy', 'High-fat cream.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (47, 'beef broth', 'Liquid', 'Broth from beef.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (48, 'baguette', 'Grain', 'French bread.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (49, 'gruyere cheese', 'Dairy', 'Swiss cheese.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (50, 'shrimp', 'Seafood', 'Fresh shrimp.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (51, 'linguine', 'Grain', 'Thin pasta.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (52, 'blueberries', 'Fruit', 'Fresh blueberries.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (53, 'lamb leg', 'Meat', 'Leg of lamb.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (54, 'rosemary', 'Herb', 'Fresh rosemary.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (55, 'pumpkin', 'Vegetable', 'Fresh pumpkin.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (56, 'cream', 'Dairy', 'Light cream.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (57, 'vegetable stock', 'Liquid', 'Stock from vegetables.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (58, 'mascarpone', 'Dairy', 'Italian cream cheese.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (59, 'ladyfingers', 'Grain', 'Light sponge biscuits.', 'MAIN');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (60, 'coffee', 'Beverage', 'Brewed coffee.', 'SUB');
INSERT INTO ingredients (id, name, category, description, rank) 
VALUES (61, 'cocoa powder', 'Sweetener', 'Unsweetened cocoa.', 'SUB');

-- 3. ingredient_dietary_restrictions join table data
-- Spaghetti: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (1, 1);
-- Pancetta: Non-vegetarian, non-vegan, non-Kosher, non-Halal
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (2, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (2, 5);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (2, 6);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (2, 7);
-- Egg Yolks: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (3, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (3, 5);
-- Parmesan Cheese: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (4, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (4, 5);
-- Chicken Breast: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (5, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (5, 5);
-- Flour: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (9, 1);
-- Butter: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (10, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (10, 5);
-- Chocolate Chips: Contains dairy (typically), non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (12, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (12, 5);
-- Ground Beef: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (13, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (13, 5);
-- Tortillas: Contains gluten (assuming wheat-based)
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (15, 1);
-- Milk: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (21, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (21, 5);
-- Eggs: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (22, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (22, 5);
-- Salmon Fillets: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (23, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (23, 5);
-- Pizza Dough: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (27, 1);
-- Mozzarella: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (29, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (29, 5);
-- Beef Sirloin: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (40, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (40, 5);
-- Sour Cream: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (41, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (41, 5);
-- Egg Noodles: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (43, 1);
-- Fettuccine: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (45, 1);
-- Heavy Cream: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (46, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (46, 5);
-- Baguette: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (48, 1);
-- Gruyere Cheese: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (49, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (49, 5);
-- Shrimp: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (50, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (50, 5);
-- Linguine: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (51, 1);
-- Lamb Leg: Non-vegetarian, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (53, 4);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (53, 5);
-- Cream: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (56, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (56, 5);
-- Mascarpone: Contains dairy, non-vegan
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (58, 2);
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (58, 5);
-- Ladyfingers: Contains gluten
INSERT INTO ingredient_dietary_restrictions (ingredient_id, dietary_restriction_id) VALUES (59, 1);

-- 4. recipes table data (all 20 recipes from the JSON document)
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Spaghetti Carbonara', 'A classic Italian pasta dish with creamy sauce and pancetta.', 
        'Cook spaghetti, fry pancetta, mix egg yolks with parmesan, toss with pasta.', 
        25, 'https://www.allrecipes.com/thmb/QtmdHdH04CHgBbjUsISCm_RLTM0=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/11973-spaghetti-carbonara-ii-DDMFS-4x3-6edea51e421e4457ac0c3269f3be5157.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Chicken Stir-Fry', 'Quick and healthy stir-fry with tender chicken and veggies.', 
        'Stir-fry chicken with soy sauce, add bell peppers and broccoli, cook until tender.', 
        20, 'https://natashaskitchen.com/wp-content/uploads/2018/08/Chicken-Stir-Fry-1-1.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Chocolate Chip Cookies', 'Chewy cookies loaded with chocolate chips.', 
        'Cream butter and sugar, mix in flour and chocolate chips, bake at 350°F.', 
        30, 'https://cdn.loveandlemons.com/wp-content/uploads/2024/08/chocolate-chip-cookie-recipe.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Beef Tacos', 'Spicy beef tacos with fresh toppings.', 
        'Cook ground beef with taco seasoning, warm tortillas, top with salsa.', 
        35, 'https://www.onceuponachef.com/images/2023/08/Beef-Tacos.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Vegetable Curry', 'A flavorful vegan curry with coconut milk.', 
        'Sauté curry paste, add coconut milk, potatoes, and carrots, simmer until tender.', 
        40, 'https://www.indianhealthyrecipes.com/wp-content/uploads/2023/07/vegetable-curry-recipe.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Pancakes', 'Fluffy pancakes perfect for breakfast.', 
        'Mix flour, milk, eggs, and sugar, cook on a griddle until golden.', 
        20, 'https://www.allrecipes.com/thmb/dOTb-yf_t1CXgw91lg69Wvzrl8E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AR-162760-fluffy-pancakes-DDMFS-beauty-3x4-35d4b54b54464701b32870d4b1646bfe.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Grilled Salmon', 'Juicy salmon fillets with a lemon glaze.', 
        'Marinate salmon in lemon juice, olive oil, and garlic, grill until cooked.', 
        25, 'https://hips.hearstapps.com/hmg-prod/images/how-to-grill-salmon-recipe1-1655870645.jpg?crop=0.6666666666666667xw:1xh;center,top&resize=1200:*');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Margherita Pizza', 'Simple pizza with fresh tomatoes and mozzarella.', 
        'Roll out pizza dough, spread tomato sauce, top with mozzarella and basil, bake.', 
        45, 'https://kristineskitchenblog.com/wp-content/uploads/2024/07/margherita-pizza-22-2-500x500.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Lentil Soup', 'Hearty and warming soup with lentils and spices.', 
        'Cook lentils with vegetable broth, onion, and cumin, simmer until soft.', 
        50, 'https://www.noracooks.com/wp-content/uploads/2018/11/square-1.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Apple Pie', 'Classic dessert with a flaky crust and spiced apples.', 
        'Make crust with flour, fill with apples, sugar, and cinnamon, bake until golden.', 
        60, 'https://www.inspiredtaste.net/wp-content/uploads/2019/11/Homemade-Apple-Pie-From-Scratch-1200.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Caesar Salad', 'Crisp romaine with creamy dressing and croutons.', 
        'Toss romaine lettuce with caesar dressing, croutons, and parmesan.', 
        15, 'https://www.grocery.coop/sites/default/files/styles/recipe_photo/public/NCG_Dennis_Becker_Classic_Caesar_Salad_715_x_477.jpg?itok=sdL3NiyO');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Beef Stroganoff', 'Creamy beef dish served over noodles.', 
        'Cook beef sirloin with mushrooms, mix with sour cream, serve over egg noodles.', 
        40, 'https://godairyfree.org/wp-content/uploads/2022/12/Beef-Stroganoff-online-feature2.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Banana Bread', 'Moist and sweet bread made with ripe bananas.', 
        'Mash bananas, mix with flour, sugar, and butter, bake at 350°F.', 
        60, 'https://www.allrecipes.com/thmb/kcdxlIXhpJJUY08OMgsE1yLpX6U=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/6984-banana-sour-cream-bread-DDMFS-4x3-42e521007c6241ca9db1a870f93d70b4.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Chicken Alfredo', 'Creamy pasta with grilled chicken.', 
        'Cook fettuccine, grill chicken, mix with heavy cream and parmesan.', 
        30, 'https://iwashyoudry.com/wp-content/uploads/2022/08/Chicken-Alfredo-Low-Res-21.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('French Onion Soup', 'Rich soup topped with melted cheese.', 
        'Caramelize onions, add beef broth, top with baguette and gruyere, broil.', 
        70, 'https://feelgoodfoodie.net/wp-content/uploads/2024/10/French-Onion-Soup-11.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Shrimp Scampi', 'Garlicky shrimp served over pasta.', 
        'Sauté shrimp with garlic and butter, toss with linguine.', 
        20, 'https://littlespoonfarm.com/wp-content/uploads/2024/07/Shrimp-Scampi-Recipe-photo-1.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Blueberry Muffins', 'Soft muffins bursting with blueberries.', 
        'Mix flour, sugar, and milk, fold in blueberries, bake at 375°F.', 
        35, 'https://www.kingarthurbaking.com/sites/default/files/styles/featured_image/public/2022-12/KABC_Quick-Breads_Blueberry-Muffin_08304.jpg?itok=EM7XxPfL');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Roasted Lamb', 'Tender lamb with rosemary and garlic.', 
        'Rub lamb leg with rosemary, garlic, and olive oil, roast until tender.', 
        90, 'https://food.fnr.sndimg.com/content/dam/images/food/fullset/2016/2/26/0/FN_Ina-Garten_Herb-Roasted-Lamb-H1_s4x3.jpg.rend.hgtvcom.1280.1280.suffix/1558026543714.webp');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Pumpkin Soup', 'Creamy soup with seasonal pumpkin flavor.', 
        'Cook pumpkin with vegetable stock and onion, blend with cream.', 
        45, 'https://www.healthyfood.com/wp-content/uploads/2019/07/Creamy-pumpkin-soup-1024x638.jpg');
INSERT INTO recipes (name, description, instruction, cooking_time, image_url)
VALUES ('Tiramisu', 'Layered Italian dessert with coffee and mascarpone.', 
        'Dip ladyfingers in coffee, layer with mascarpone, dust with cocoa powder.', 
        30, 'https://www.kingarthurbaking.com/sites/default/files/styles/featured_image/public/2023-03/Tiramisu_1430-1.jpg?itok=DUplf517');

-- 5. recipe_ingredients join table data (for all 20 recipes)
-- Recipe 1: Spaghetti Carbonara
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (1, 1, 1, 400, 'g', TRUE);    -- spaghetti
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (2, 1, 2, 150, 'g', TRUE);    -- pancetta
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (3, 1, 3, 3, '', TRUE);       -- egg yolks
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (4, 1, 4, 50, 'g', TRUE);     -- parmesan cheese

-- Recipe 2: Chicken Stir-Fry
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (5, 2, 5, 500, 'g', TRUE);    -- chicken breast
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (6, 2, 6, 2, 'tbsp', TRUE);   -- soy sauce
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (7, 2, 7, 2, '', TRUE);       -- bell peppers
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (8, 2, 8, 200, 'g', TRUE);    -- broccoli

-- Recipe 3: Chocolate Chip Cookies
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (9, 3, 9, 300, 'g', TRUE);    -- flour
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (10, 3, 10, 200, 'g', TRUE);  -- butter
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (11, 3, 11, 150, 'g', TRUE);  -- sugar
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (12, 3, 12, 200, 'g', TRUE);  -- chocolate chips

-- Recipe 4: Beef Tacos
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (13, 4, 13, 500, 'g', TRUE);  -- ground beef
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (14, 4, 14, 2, 'tbsp', TRUE); -- taco seasoning
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (15, 4, 15, 8, '', TRUE);     -- tortillas
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (16, 4, 16, 100, 'ml', TRUE); -- salsa

-- Recipe 5: Vegetable Curry
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (17, 5, 17, 400, 'ml', TRUE); -- coconut milk
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (18, 5, 18, 2, 'tbsp', TRUE); -- curry paste
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (19, 5, 19, 300, 'g', TRUE);  -- potatoes
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (20, 5, 20, 200, 'g', TRUE);  -- carrots

-- Recipe 6: Pancakes
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (21, 6, 9, 200, 'g', TRUE);   -- flour
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (22, 6, 21, 300, 'ml', TRUE); -- milk
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (23, 6, 22, 2, '', TRUE);     -- eggs
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (24, 6, 11, 2, 'tbsp', TRUE); -- sugar

-- Recipe 7: Grilled Salmon
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (25, 7, 23, 4, '', TRUE);     -- salmon fillets
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (26, 7, 24, 3, 'tbsp', TRUE); -- lemon juice
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (27, 7, 25, 2, 'tbsp', TRUE); -- olive oil
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (28, 7, 26, 2, 'cloves', TRUE); -- garlic

-- Recipe 8: Margherita Pizza
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (29, 8, 27, 300, 'g', TRUE);  -- pizza dough
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (30, 8, 28, 100, 'ml', TRUE); -- tomato sauce
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (31, 8, 29, 150, 'g', TRUE);  -- mozzarella
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (32, 8, 30, 10, 'leaves', TRUE); -- basil

-- Recipe 9: Lentil Soup
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (33, 9, 31, 200, 'g', TRUE);  -- lentils
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (34, 9, 32, 1, 'L', TRUE);    -- vegetable broth
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (35, 9, 33, 1, '', TRUE);     -- onion
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (36, 9, 34, 1, 'tsp', TRUE);  -- cumin

-- Recipe 10: Apple Pie
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (37, 10, 9, 400, 'g', TRUE);  -- flour
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (38, 10, 35, 6, '', TRUE);    -- apples
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (39, 10, 11, 150, 'g', TRUE); -- sugar
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (40, 10, 36, 1, 'tsp', TRUE); -- cinnamon

-- Recipe 11: Caesar Salad
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (41, 11, 37, 1, 'head', TRUE); -- romaine lettuce
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (42, 11, 38, 100, 'ml', TRUE); -- caesar dressing
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (43, 11, 39, 50, 'g', TRUE);  -- croutons
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (44, 11, 4, 30, 'g', TRUE);   -- parmesan

-- Recipe 12: Beef Stroganoff
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (45, 12, 40, 600, 'g', TRUE); -- beef sirloin
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (46, 12, 41, 200, 'ml', TRUE); -- sour cream
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (47, 12, 42, 250, 'g', TRUE); -- mushrooms
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (48, 12, 43, 300, 'g', TRUE); -- egg noodles

-- Recipe 13: Banana Bread
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (49, 13, 44, 3, '', TRUE);    -- bananas
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (50, 13, 9, 250, 'g', TRUE);  -- flour
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (51, 13, 11, 150, 'g', TRUE); -- sugar
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (52, 13, 10, 100, 'g', TRUE); -- butter

-- Recipe 14: Chicken Alfredo
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (53, 14, 45, 400, 'g', TRUE); -- fettuccine
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (54, 14, 5, 500, 'g', TRUE);  -- chicken breast
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (55, 14, 46, 200, 'ml', TRUE); -- heavy cream
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (56, 14, 4, 100, 'g', TRUE);  -- parmesan

-- Recipe 15: French Onion Soup
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (57, 15, 33, 4, '', TRUE);    -- onions
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (58, 15, 47, 1, 'L', TRUE);   -- beef broth
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (59, 15, 48, 4, 'slices', TRUE); -- baguette
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (60, 15, 49, 100, 'g', TRUE); -- gruyere cheese

-- Recipe 16: Shrimp Scampi
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (61, 16, 50, 500, 'g', TRUE); -- shrimp
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (62, 16, 51, 300, 'g', TRUE); -- linguine
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (63, 16, 10, 50, 'g', TRUE);  -- butter
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (64, 16, 26, 3, 'cloves', TRUE); -- garlic

-- Recipe 17: Blueberry Muffins
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (65, 17, 9, 250, 'g', TRUE);  -- flour
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (66, 17, 52, 200, 'g', TRUE); -- blueberries
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (67, 17, 11, 100, 'g', TRUE); -- sugar
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (68, 17, 21, 150, 'ml', TRUE); -- milk

-- Recipe 18: Roasted Lamb
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (69, 18, 53, 1.5, 'kg', TRUE); -- lamb leg
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (70, 18, 54, 2, 'tbsp', TRUE); -- rosemary
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (71, 18, 26, 4, 'cloves', TRUE); -- garlic
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (72, 18, 25, 3, 'tbsp', TRUE); -- olive oil

-- Recipe 19: Pumpkin Soup
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (73, 19, 55, 1, 'kg', TRUE);  -- pumpkin
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (74, 19, 56, 200, 'ml', TRUE); -- cream
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (75, 19, 57, 800, 'ml', TRUE); -- vegetable stock
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (76, 19, 33, 1, '', TRUE);    -- onion

-- Recipe 20: Tiramisu
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (77, 20, 58, 250, 'g', TRUE); -- mascarpone
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (78, 20, 59, 200, 'g', TRUE); -- ladyfingers
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (79, 20, 60, 200, 'ml', TRUE); -- coffee
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit, is_required)
VALUES (80, 20, 61, 2, 'tbsp', TRUE); -- cocoa powder