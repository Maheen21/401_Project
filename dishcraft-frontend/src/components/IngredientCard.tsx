interface IngredientCardProps {
    label: string;
    description?: string;
    tags?: string[];
    disabled?: boolean;
    selected?: boolean;
    onClick?: () => void;
    className?: string;
  }
  
  const IngredientCard = ({
    label,
    description = "",
    tags = [],
    disabled = false,
    selected = false,
    onClick,
    className = "",
  }: IngredientCardProps) => {
    const visibleTags = tags.slice(0, 3);
    const hiddenCount = tags.length - visibleTags.length;
  
    return (
        <div
        onClick={onClick}
        className={`
            w-full max-w-[280px] p-3 rounded-md border transition-all duration-200 ease-in-out cursor-pointer
            ${disabled ? "opacity-50 cursor-not-allowed" : ""}
            ${
            selected
                ? "bg-gray-100 border-gray-400 shadow-inner ring-1 ring-inset ring-gray-300 scale-[0.98]"
                : "bg-white border-gray-200 hover:bg-gray-50 shadow-sm hover:shadow-md active:scale-[0.97]"
            }
            ${className}
        `}
        >
        {/* name + tag */}
        <div className="flex justify-between items-start mb-1">
          <div className={`font-semibold text-base ${selected ? "text-gray-700" : "text-gray-900"}`}>
            {label}
          </div>
  
          <div className="flex flex-wrap justify-end gap-1 ml-2">
            {visibleTags.map((tag) => (
              <span
                key={tag}
                className={`text-[10px] px-2 py-0.5 rounded-full whitespace-nowrap
                  ${selected
                    ? "bg-white text-gray-800"
                    : "bg-blue-100 text-blue-800"}
                `}
              >
                {tag}
              </span>
            ))}
            {hiddenCount > 0 && (
              <span
                className={`text-[10px] px-2 py-0.5 rounded-full
                  ${selected
                    ? "bg-white text-gray-600"
                    : "bg-gray-200 text-gray-600"}
                `}
                title={tags.slice(3).join(", ")}
              >
                +{hiddenCount} more
              </span>
            )}
          </div>
        </div>
  
        {/* desc */}
        {description && (
          <p className={`text-[11px] leading-snug ${selected ? "text-gray-300" : "text-gray-700"}`}>
            {description}
          </p>
        )}
      </div>
    );
  };
  
  export default IngredientCard;
  