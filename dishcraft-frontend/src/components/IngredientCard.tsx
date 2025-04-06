import { MouseEventHandler } from "react";
import ViolationMiniTag from "./ViolationMiniTag";

interface IngredientCardProps {
  label: string;
  description?: string;
  tags?: string[]; // e.g., ["Meat"]
  violations?: string[]; // e.g., ["Vegan", "Gluten"]
  rank?: "MAIN" | "SUB";
  disabled?: boolean;
  selected?: boolean;
  onClick?: MouseEventHandler<HTMLDivElement>;
  className?: string;
}

const IngredientCard = ({
  label,
  description = "",
  tags = [],
  violations = [],
  rank = "SUB",
  disabled = false,
  selected = false,
  onClick,
  className = "",
}: IngredientCardProps) => {
  const visibleTags = tags.slice(0, 3);
  const hiddenCount = tags.length - visibleTags.length;
  const isMain = rank === "MAIN";

  return (
    <div
      onClick={onClick}
      className={`
        w-full p-4 rounded-md border transition-all duration-200 ease-in-out cursor-pointer
        ${disabled ? "opacity-50 cursor-not-allowed" : ""}
        ${selected
          ? "bg-gray-100 border-gray-400 shadow-inner ring-1 ring-inset ring-gray-300 scale-[0.98]"
          : isMain
          ? "bg-blue-50 border-blue-200"
          : "bg-white border-gray-200 hover:bg-gray-50 shadow-sm hover:shadow-md active:scale-[0.97]"}
        ${className}
      `}
    >
      {/* Header (label + tags + violations) */}
      <div className="flex justify-between items-start mb-1 gap-2">
        {/* Name */}
        <div className="text-base font-semibold text-gray-900 whitespace-nowrap overflow-hidden text-ellipsis">
          {label}
        </div>

        {/* Tags */}
        <div className="flex flex-wrap justify-end gap-1 ml-2">
          {visibleTags.map((tag) => (
            <span
              key={tag}
              className="text-[10px] px-2 py-0.5 rounded-full bg-blue-100 text-blue-800 whitespace-nowrap"
            >
              {tag}
            </span>
          ))}
          {hiddenCount > 0 && (
            <span
              className="text-[10px] px-2 py-0.5 rounded-full bg-gray-200 text-gray-600"
              title={tags.slice(3).join(", ")}
            >
              +{hiddenCount} more
            </span>
          )}
          {violations.map((v) => (
            <ViolationMiniTag key={v} label={v} />
          ))}
        </div>
      </div>

      {/* Description */}
      {description && (
        <p className="text-[11px] text-gray-700 leading-snug">{description}</p>
      )}
    </div>
  );
};

export default IngredientCard;
