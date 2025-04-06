import type { Dispatch, SetStateAction } from "react";
import type { DietaryRestriction } from "../types"; // We'll alias the tag type here

interface ExploreFilterBarProps {
  sortKey: "name" | "time";
  setSortKey: Dispatch<SetStateAction<"name" | "time">>;
  excludedTags: string[];
  setExcludedTags: Dispatch<SetStateAction<string[]>>;
  tagOptions: DietaryRestriction[];
}

const ExploreFilterBar = ({
  sortKey,
  setSortKey,
  excludedTags,
  setExcludedTags,
  tagOptions,
}: ExploreFilterBarProps) => {
  const handleTagToggle = (tagName: string) => {
    setExcludedTags((prev) =>
      prev.includes(tagName) ? prev.filter((t) => t !== tagName) : [...prev, tagName]
    );
  };

  const handleReset = () => {
    setSortKey("name");
    setExcludedTags([]);
  };

  return (
    <div className="flex flex-col gap-4 mb-6">
      {/* Sort dropdown */}
      <div>
        <label className="mr-2 font-medium">Sort:</label>
        <select
          className="border rounded px-3 py-1"
          value={sortKey}
          onChange={(e) => setSortKey(e.target.value as "name" | "time")}
        >
          <option value="name">By Name (A-Z)</option>
          <option value="time">By Cooking Time (Low → High)</option>
        </select>
      </div>

      {/* Multi-select tag filter */}
      <div className="flex flex-wrap items-center gap-2">
        <span className="font-medium mr-2">My dietary restrictions:</span>
        {tagOptions.map((tag) => (
          <button
            key={tag.id}
            onClick={() => handleTagToggle(tag.name)}
            className={`px-3 py-1 rounded border text-sm transition-all
              ${
                excludedTags.includes(tag.name)
                  ? "bg-red-500 text-white border-red-500"
                  : "bg-white text-gray-700 border-gray-300 hover:bg-gray-100"
              }`}
          >
            {tag.name}
          </button>
        ))}
        <button
          onClick={handleReset}
          className="ml-2 text-sm text-blue-600 underline"
        >
          Reset
        </button>
      </div>
    </div>
  );
};

export default ExploreFilterBar;
