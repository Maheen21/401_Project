import { useEffect, useState } from "react";
import { fetchUserRestrictions , 
  fetchAllDietaryRestrictions,
  addUserRestriction,
  removeUserRestriction
} from "../utils/dietaryApi";
import { fetchUserFavoriteRecipes } from "../utils/favoriteApi";
import type { DietaryRestriction, Recipe } from "../types";
import UserDietaryList from "../components/UserDietaryList";
import RecipeCard from "../components/RecipeCard";
import { useNavigate } from "react-router-dom";
import PrimaryButton from "../components/PrimaryButton";

const MyPage = () => {
  const [restrictions, setRestrictions] = useState<DietaryRestriction[]>([]);
  const [allRestrictions, setAllRestrictions] = useState<DietaryRestriction[]>([]);
  const [favorites, setFavorites] = useState<Recipe[]>([]);
  const [visibleCount, setVisibleCount] = useState(4);
  const [editMode, setEditMode] = useState(false);
  const [selectedTags, setSelectedTags] = useState<number[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
      navigate("/signin"); // 🔒 redirect to signin page if no token found in localStorage
    }
    const loadData = async () => {
      const [userDR, allDR, userFavs] = await Promise.all([
        fetchUserRestrictions(),
        fetchAllDietaryRestrictions(),
        fetchUserFavoriteRecipes(),
      ]);
      setRestrictions(userDR);
      setAllRestrictions(allDR);
      setSelectedTags(userDR.map((tag) => tag.id));
      setFavorites(userFavs);
    };

    loadData();
  }, [navigate]);

  const handleLoadMore = () => {
    setVisibleCount((prev) => prev + 4);
  };
  const handleCancel = () => {
    setSelectedTags(restrictions.map((r) => r.id));
    setEditMode(false);
  };
  const toggleTag = (id: number) => {
    setSelectedTags((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    );
  };
  const handleSave = async () => {
    const prevIds = restrictions.map((r) => r.id);
    const toAdd = selectedTags.filter((id) => !prevIds.includes(id));
    const toRemove = prevIds.filter((id) => !selectedTags.includes(id));

    try {
      for (const id of toAdd) {
        await addUserRestriction(id);
      }
      for (const id of toRemove) {
        await removeUserRestriction(id);
      }

      const updated = await fetchUserRestrictions();
      setRestrictions(updated);
      setEditMode(false);
    } catch (err) {
      console.error("Failed to update dietary restrictions", err);
    }
  };
  return (
    <div className="max-w-screen-xl mx-auto px-6 py-10">
      {/* User Info */}
        <div className="flex flex-col md:flex-row items-start gap-12 mb-12">
          {/* Profile Section */}
          <div className="flex flex-col items-center">
            <div className="w-40 h-40 bg-gray-300 rounded-full mb-4" />
            <h2 className="text-xl font-bold">MYPAGE</h2>
            <p className="text-sm text-gray-500">Your personalized hub</p>
          </div>

          {/* Dietary Restriction Section */}
        <div className="flex-1 flex md:justify-end md:items-end">
          <div className="flex-1 flex flex-col items-end justify-end text-right">
            <h3 className="text-lg font-semibold mb-2">Dietary Restrictions</h3>
            <div className="flex flex-wrap md:flex-nowrap gap-2 justify-end">
              {editMode ? (
                <div className="flex flex-wrap gap-2">
                  {allRestrictions.map((tag) => {
                    const selected = selectedTags.includes(tag.id);
                    return (
                      <button
                        key={tag.id}
                        onClick={() => toggleTag(tag.id)}
                        className={`px-3 py-1 text-xs rounded-full border transition font-medium ${
                          selected
                            ? "bg-red-500 text-white border-red-500"
                            : "bg-gray-100 text-gray-700 border-gray-300 hover:bg-gray-200"
                        }`}
                      >
                        {tag.name}
                      </button>
                    );
                  })}
                </div>
              ) : (
                <UserDietaryList
                  allTags={allRestrictions}
                  userTags={restrictions}
                />
              )}
            </div>
            {editMode ? (
              <div className="mt-2 flex gap-2 justify-end">
                <PrimaryButton onClick={handleSave}>Save</PrimaryButton>
                <PrimaryButton variant="outline" onClick={handleCancel}>
                  Cancel
                </PrimaryButton>
              </div>
            ) : (
              <div className="mt-2">
                <PrimaryButton variant="outline" onClick={() => setEditMode(true)}>
                  Edit
                </PrimaryButton>
              </div>
            )}
          </div>
        </div>
      </div>


      {/* Favorites */}
      <div>
        <h3 className="text-lg font-semibold mb-4">Favorites</h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {favorites.slice(0, visibleCount).map((recipe) => (
            <RecipeCard key={recipe.id} recipe={recipe} />
          ))}
        </div>
        {visibleCount < favorites.length && (
          <div className="mt-6 text-center">
            <button
              onClick={handleLoadMore}
              className="px-6 py-2 bg-gray-200 hover:bg-gray-300 rounded"
            >
              Load more
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default MyPage;
