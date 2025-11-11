export type CuisineType =
    'ITALIAN' | 'FRENCH' | 'JAPANESE' | 'AMERICAN' | 'INDIAN' |
    'SPANISH' | 'VEGETARIAN' | 'CHINESE' | 'MEXICAN' | 'GREEK';

export type PriceRange = 'CHEAP' | 'REGULAR' | 'EXPENSIVE';

export const ALL_CUISINE_TYPES: CuisineType[] = [
    'ITALIAN', 'FRENCH', 'JAPANESE', 'AMERICAN', 'INDIAN',
    'SPANISH', 'VEGETARIAN', 'CHINESE', 'MEXICAN', 'GREEK'
];

export const ALL_PRICE_RANGES: PriceRange[] = [
    'CHEAP', 'REGULAR', 'EXPENSIVE'
];