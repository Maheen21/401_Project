import axios from "./axiosInstance";
import type { DietaryRestriction } from "../types";

// Get all dietary restrictions in the system
export const fetchAllDietaryRestrictions = async (): Promise<DietaryRestriction[]> => {
  const res = await axios.get("/api/dietary-restrictions");
  return res.data;
};

// Get the dietary restrictions for the currently authenticated user
export const fetchUserRestrictions = async (): Promise<DietaryRestriction[]> => {
  const res = await axios.get("/api/dietary-restrictions/current-user");
  return res.data;
};

// Add a dietary restriction to the current user
export const addUserRestriction = async (id: number): Promise<void> => {
  await axios.post(`/api/dietary-restrictions/current-user/${id}`);
};

// Remove a dietary restriction from the current user
export const removeUserRestriction = async (id: number): Promise<void> => {
  await axios.delete(`/api/dietary-restrictions/current-user/${id}`);
};

// Check if the current user has a specific restriction (not always needed)
export const checkUserHasRestriction = async (id: number): Promise<boolean> => {
  const res = await axios.get(`/api/dietary-restrictions/has/${id}`);
  return res.data.has;
};
