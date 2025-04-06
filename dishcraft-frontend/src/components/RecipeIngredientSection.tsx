import IngredientCard from "./IngredientCard";
import type { Ingredient } from "../types";

interface Props {
  ingredients: Ingredient[];
  selectedIds: number[];
  onToggle: (id: number) => void;
}

const RecipeIngredientSection = ({ ingredients, selectedIds, onToggle }: Props) => (
  <section>
    <h2 className="text-xl font-semibold mb-4">Ingredients</h2>
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      {ingredients.map((ing) => (
        <IngredientCard
          key={ing.id}
          label={ing.label}
          description={ing.description}
          tags={ing.tags}
          violations={ing.violations}
          rank={ing.rank}
          selected={selectedIds.includes(ing.id)}
          onClick={() => onToggle(ing.id)}
        />
      ))}
    </div>
  </section>
);

export default RecipeIngredientSection;