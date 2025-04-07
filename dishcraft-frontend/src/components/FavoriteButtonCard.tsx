import { useEffect, useState } from "react";
import FavoriteButton from "./FavoriteButton";
import {
  checkFavoriteStatus,
  addToFavorites,
  removeFromFavorites,
} from "../utils/favoriteApi";

interface FavoriteButtonCardProps {
  recipeId: number;
}

const FavoriteButtonCard = ({ recipeId }: FavoriteButtonCardProps) => {
  const [isFavorite, setIsFavorite] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
      setIsLoggedIn(false);
      return; // 🛑 do not proceed
    }
    
    setIsLoggedIn(true);

    const fetchStatus = async () => {
      const status = await checkFavoriteStatus(recipeId);
      setIsFavorite(status);
    };

    fetchStatus();
  }, [recipeId]);

  const handleToggle = async () => {
    try {
      if (isFavorite) {
        await removeFromFavorites(recipeId);
      } else {
        await addToFavorites(recipeId);
      }
      setIsFavorite(!isFavorite);
    } catch (err) {
      console.error("Failed to update favorite status:", err);
    }
  };
  
  if (!isLoggedIn) return null;

  return (
    <div
      role="button"

      onClick={handleToggle}
      className="
      w-full
      max-w-full
      sm:max-w-[50%]
      lg:max-w-[25%]
      p-4 
      rounded-md 
      border 
      bg-gray-50
      hover:bg-gray-50 
      flex 
      justify-between items-center 
      cursor-pointer transition"
    >
      <span className="text-sm font-medium text-gray-800">
        {isFavorite ? "Remove from favorites" : "Add to favorites"}
      </span>

      {/* prevent nested click capture */}
      <div className="pointer-events-none">
        <FavoriteButton isFavorite={isFavorite} onToggle={() => {}} />
      </div>
    </div>
  );
};

export default FavoriteButtonCard;
