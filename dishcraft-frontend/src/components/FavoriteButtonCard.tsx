import FavoriteButton from "./FavoriteButton";

interface FavoriteButtonCardProps {
  initial?: boolean;
  onToggle?: (isFavorite: boolean) => void;
}

const FavoriteButtonCard = ({
  initial = false,
  onToggle,
}: FavoriteButtonCardProps) => {
  return (
    <div
      className="w-full p-4 rounded-md border bg-white hover:bg-gray-50 flex justify-between items-center cursor-pointer transition"
    >
      <span className="text-sm font-medium text-gray-800">
        {initial ? "Remove from favorites" : "Add to favorites"}
      </span>
      <FavoriteButton initial={initial} onToggle={onToggle} />
    </div>
  );
};

export default FavoriteButtonCard;
