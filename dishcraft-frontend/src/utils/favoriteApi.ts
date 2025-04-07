// src/utils/favoriteApi.ts
import axios from "./axiosInstance";
import type { Recipe } from "../types";

const API_BASE = "/api/favorites";

const getAuthHeader = () => {
  const token = localStorage.getItem("jwtToken");
  return token ? { Authorization: `Bearer ${token}` } : {};
};

// 1. check if a recipe is already in favorites
export const checkFavoriteStatus = async (recipeId: number): Promise<boolean> => {
  try {
    const res = await axios.get(`${API_BASE}/check/${recipeId}`, {
      headers: getAuthHeader(),
    });
    return res.data.isFavorite === true; // true if the recipe is a favorite, false otherwise
  } catch (err) {
    console.error("Failed to check favorite status:", err);
    return false;
  }
};

// 2. add a recipe to favorites
export const addToFavorites = async (recipeId: number): Promise<boolean> => {
  try {
    await axios.post(
      API_BASE,
      { recipeId },
      {
        headers: {
          ...getAuthHeader(),
          "Content-Type": "application/json",
        },
      }
    );
    return true;
  } catch (err) {
    console.error("Failed to add favorite:", err);
    return false;
  }
};

// 3. delete a recipe from favorites
export const removeFromFavorites = async (recipeId: number): Promise<boolean> => {
  try {
    await axios.delete(`${API_BASE}/${recipeId}`, {
      headers: getAuthHeader(),
    });
    return true;
  } catch (err) {
    console.error("Failed to remove favorite:", err);
    return false;
  }
  
};

// 4. get all favorited recipes of the current user
export const fetchUserFavoriteRecipes = async (): Promise<Recipe[]> => {
    try {
      const res = await axios.get(`${API_BASE}/recipes`, {
        headers: getAuthHeader(),
      });
      return res.data;
    } catch (err) {
      console.error("Failed to fetch user favorite recipes:", err);
      return [];
    }
  };
