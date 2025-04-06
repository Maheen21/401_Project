import type { GeneratedRecipe } from "../types";

const GeneratedRecipeSection = ({ result }: { result: GeneratedRecipe }) => (
  <div className="bg-gray-100 p-6 rounded shadow mt-6 space-y-4">
    <h3 className="text-2xl font-bold">{result.recipe}</h3>

    {Object.keys(result.missingIngredients).length > 0 && (
      <div>
        <h4 className="font-semibold text-red-600">Missing & Substitutions:</h4>
        <ul className="list-disc ml-5 text-sm">
          {Object.entries(result.missingIngredients).map(([k, v]) => (
            <li key={k}>
              {k} → <strong>{v}</strong>
            </li>
          ))}
        </ul>
      </div>
    )}

    <div>
      <h4 className="font-semibold">Steps:</h4>
      <ol className="list-decimal ml-5 space-y-1 text-sm">
        {result.steps.map((step, i) => (
          <li key={i}>{step}</li>
        ))}
      </ol>
    </div>
  </div>
);

export default GeneratedRecipeSection;