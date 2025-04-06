import type { DietaryRestriction } from "../types";

interface RecipeDietaryInfoProps {
  allTags: DietaryRestriction[];
  violatedTags: DietaryRestriction[];
}

const RecipeDietaryInfo = ({ allTags, violatedTags }: RecipeDietaryInfoProps) => {
  return (
    <div className="mb-6 flex flex-wrap gap-2">
      {allTags.map((tag) => {
        const isViolated = violatedTags.some((v) => v.id === tag.id);
        return (
          <span
            key={tag.id}
            className={`text-xs px-3 py-1 rounded-full border font-medium tracking-wide ${
              isViolated
                ? "bg-red-500 text-white border-red-500"
                : "bg-gray-100 text-gray-700 border-gray-300"
            }`}
          >
            {tag.name}
          </span>
        );
      })}
    </div>
  );
};

export default RecipeDietaryInfo;