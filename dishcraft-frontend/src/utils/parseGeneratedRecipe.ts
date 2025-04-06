// src/utils/parseGeneratedRecipe.ts

import type { GeneratedRecipe } from "../types";

/**
 * Tries to extract and parse a JSON-like LLM response into a usable recipe object.
 */
export const parseGeneratedRecipe = (raw: string): GeneratedRecipe | null => {
  try {
    const jsonStart = raw.indexOf("{");
    const jsonEnd = raw.lastIndexOf("}");

    if (jsonStart === -1 || jsonEnd === -1) {
      throw new Error("No valid JSON block found in the response.");
    }

    const jsonString = raw.substring(jsonStart, jsonEnd + 1);
    const parsed = JSON.parse(jsonString);

    // Basic validation
    if (
      typeof parsed.recipe !== "string" ||
      !Array.isArray(parsed.steps) ||
      typeof parsed.missingIngredients !== "object"
    ) {
      throw new Error("Parsed object is missing expected fields.");
    }

    return parsed as GeneratedRecipe;
  } catch (err) {
    console.error("❌ Failed to parse LLM response:", err);
    return null;
  }
};
