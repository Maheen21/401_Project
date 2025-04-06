// src/components/FavoriteButton.tsx

import { useState } from "react";
import { Star, StarOff } from "lucide-react";

interface FavoriteButtonProps {
  initial?: boolean;
  onToggle?: (isFavorite: boolean) => void;
}

const FavoriteButton = ({ initial = false, onToggle }: FavoriteButtonProps) => {
  const [isFav, setIsFav] = useState(initial);

  const handleClick = () => {
    const newState = !isFav;
    setIsFav(newState);
    onToggle?.(newState);
  };

  return (
    <button
      onClick={handleClick}
      className={`text-yellow-400 hover:scale-110 transition-transform`}
      title={isFav ? "Remove from favorites" : "Add to favorites"}
    >
      {isFav ? <Star fill="currentColor" /> : <StarOff />}
    </button>
  );
};

export default FavoriteButton;
