import ViolationTag from "./ViolationTag";

interface RecipeCardProps {
  title: string;
  time: string;
  description: string;
  imageUrl: string;
  tags: string[];
}

const RecipeCard = ({ title, time, description, imageUrl, tags }: RecipeCardProps) => {
  return (
    <div className="border rounded-lg p-4 flex flex-col gap-3 w-full bg-white shadow-sm hover:shadow-md hover:scale-[1.02] transition-transform duration-200 cursor-pointer">
      <img
        src={imageUrl}
        alt={title}
        className="h-40 w-full object-cover rounded-md"
      />
      <div className="flex flex-col gap-1">
        <h2 className="font-semibold text-lg">{title}</h2>
        <p className="text-sm text-gray-500">{time}</p>
        <p className="text-sm text-gray-600">{description}</p>
      </div>
      {tags.length > 0 && (
        <div className="flex flex-wrap gap-2 pt-2">
          {tags.map((tag, idx) => (
            <ViolationTag key={idx} label={tag} />
          ))}
        </div>
      )}
    </div>
  );
};

export default RecipeCard;
