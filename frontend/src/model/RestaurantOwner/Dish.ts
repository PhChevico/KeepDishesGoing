export type DishStatus = 'PUBLISHED' | 'UNPUBLISHED' | 'OUT_OF_STOCK' | 'DRAFT';

export const ALL_DISH_STATUSES: DishStatus[] = [
    'DRAFT',
    'UNPUBLISHED',
    'PUBLISHED',
    'OUT_OF_STOCK'
];

// Helper function for display formatting
export const formatDishStatus = (status: DishStatus): string => {
    return status.replace(/_/g, ' ')
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join(' ');
};

export interface DishDto {
    name: string;
    typeOfDish: TypeOfDish;
    foodTagList: FoodTag[];
    description: string;
    price: number;
    picture: string;
    dishStatus: DishStatus; // Now defined above
    id: string;
}

export interface CreateDishCommand {
    dishId: string;
    name: string;
    typeOfDish: TypeOfDish | string;
    foodTags: string[];
    description: string;
    price: number;
    picture: string;
    restaurantId: string;
    dishStatus: DishStatus | string; // Now defined above
}

export interface UpdateDishRequest {
    dishId: string; // UUID
    restaurantId: string; // UUID
    name: string;
    typeOfDish: TypeOfDish;
    foodTags: string[]; // List<String>
    description: string;
    price: number; // BigDecimal
    picture: string;
    dishStatus: DishStatus;
}


export type FoodTag =
    'LACTOSE' |
    'GLUTEN' |
    'VEGAN' |
    'VEGETARIAN' |
    'DAIRY' |
    'NUT' |
    'HALAL' |
    'MEAT' |
    'FISH' |
    'SEAFOOD' |
    'GLUTEN_FREE' |
    'SOY' |
    'EGG' |
    'SHELLFISH' |
    'PEANUT' |
    'SESAME' |
    'SUGAR_FREE' |
    'LOW_CARB' |
    'KETO' |
    'ORGANIC' |
    'SPICY' |
    'ITALIAN' |
    'ASIAN' |
    'MEDITERRANEAN' |
    'AMERICAN' |
    'FAST_FOOD' |
    'BEEF';

export const ALL_FOOD_TAGS: FoodTag[] = [
    'LACTOSE',
    'GLUTEN',
    'VEGAN',
    'VEGETARIAN',
    'DAIRY',
    'NUT',
    'HALAL',
    'MEAT',
    'FISH',
    'SEAFOOD',
    'GLUTEN_FREE',
    'SOY',
    'EGG',
    'SHELLFISH',
    'PEANUT',
    'SESAME',
    'SUGAR_FREE',
    'LOW_CARB',
    'KETO',
    'ORGANIC',
    'SPICY',
    'ITALIAN',
    'ASIAN',
    'MEDITERRANEAN',
    'AMERICAN',
    'FAST_FOOD',
    'BEEF'
];

export type TypeOfDish = 'STARTER' | 'MAIN' | 'DESSERT';

export const ALL_DISH_TYPES: TypeOfDish[] = ['STARTER', 'MAIN', 'DESSERT'];
