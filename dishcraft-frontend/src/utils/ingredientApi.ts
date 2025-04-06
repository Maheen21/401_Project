import axios from "./axiosInstance";
import type { Ingredient, DietaryRestriction } from "../types";
import { normalizeIngredient } from "./normalize";

export const fetchAllIngredients = async (): Promise<Ingredient[]> => {
  const res = await axios.get("/api/ingredients/all");
  return res.data.map(normalizeIngredient).filter(Boolean) as Ingredient[];
};

export const fetchFilteredIngredients = async (
  restrictionIds: number[]
): Promise<Ingredient[]> => {
  const params = new URLSearchParams();
  restrictionIds.forEach((id) =>
    params.append("dietaryRestrictionIds", id.toString())
  );

  const res = await axios.get(`/api/ingredients/filter?${params.toString()}`);
  return res.data.map(normalizeIngredient).filter(Boolean) as Ingredient[];
};

export const fetchDietaryRestrictions = async (): Promise<DietaryRestriction[]> => {
  const res = await axios.get("/api/dietary-restrictions");
  return res.data;
};
