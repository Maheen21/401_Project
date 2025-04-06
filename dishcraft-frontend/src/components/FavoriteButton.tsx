import { useState } from "react";
import { Star, StarOff } from "lucide-react";

interface FavoriteButtonProps {
  isFavorite: boolean;
  onToggle: () => void;
}

const FavoriteButton = ({ isFavorite, onToggle }: FavoriteButtonProps) => {
  return (
    <button
      onClick={onToggle}
      className="text-yellow-400 hover:scale-110 transition-transform"
      title={isFavorite ? "Remove from favorites" : "Add to favorites"}
    >
      {isFavorite ? <Star fill="currentColor" /> : <StarOff />}
    </button>
  );
};

export default FavoriteButton;