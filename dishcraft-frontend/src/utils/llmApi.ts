// src/utils/llmApi.ts

import axios from "axios";
import type { RecipeGenerationRequest, GeneratedRecipe } from "../types";

// LLM backend URL
const LLM_BASE_URL = process.env.REACT_APP_LLM_BASE_URL; // will be replaced with env variable

export const startRecipeGeneration = async (
  input: RecipeGenerationRequest
): Promise<GeneratedRecipe | null> => {
  try {
    const response = await axios.post(`${LLM_BASE_URL}/start`, {
      input: JSON.stringify(input, null, 2),
    });

    const rawText = response.data.response;

    // JSON-like parsing json-like string into a usable object
    const match = rawText.match(/\{[\s\S]*\}/);
    if (!match) throw new Error("Invalid response format");

    const parsed: GeneratedRecipe = JSON.parse(match[0]);
    return parsed;
  } catch (err) {
    console.error("LLM generation failed:", err);
    return null;
  }
};
