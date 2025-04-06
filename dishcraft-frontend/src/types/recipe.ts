export type Recipe = {
    id: number;
    name: string;
    description: string;
    cookingTime: number;
    imageUrl: string;
    dietaryRestrictions: { id: number; name: string }[];
  };