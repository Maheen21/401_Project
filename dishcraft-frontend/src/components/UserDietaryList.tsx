// src/components/UserDietaryList.tsx

import type { DietaryRestriction } from "../types";

interface UserDietaryListProps {
  allTags: DietaryRestriction[];
  userTags: DietaryRestriction[];
}

const UserDietaryList = ({ allTags, userTags }: UserDietaryListProps) => {
    return (
      <div className="overflow-x-auto whitespace-nowrap">
        {allTags.map((tag) => {
          const isUser = userTags.some((v) => v.id === tag.id);
          return (
            <span
              key={tag.id}
              className={`inline-block text-xs px-3 py-1 mr-2 rounded-full border font-medium tracking-wide ${
                isUser
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

export default UserDietaryList;
