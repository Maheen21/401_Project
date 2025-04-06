// src/utils/recipeApi.ts
import axios from "./axiosInstance";
import type { Recipe, DietaryRestriction } from "../types";

export const getAllRecipes = async (): Promise<Recipe[]> => {
  const res = await axios.get("/api/recipes/all");
  return res.data;
};

export const getFilteredRecipes = async (
  dietaryRestrictionIds: number[]
): Promise<Recipe[]> => {
  const params = new URLSearchParams();
  dietaryRestrictionIds.forEach((id) =>
    params.append("dietaryRestrictionIds", id.toString())
  );

  const res = await axios.get(`/api/recipes/filter/dietary?${params.toString()}`);
  return res.data;
};

export const getAllDietaryRestrictions = async (): Promise<DietaryRestriction[]> => {
  const res = await axios.get("/api/dietary-restrictions");
  return res.data;
};
