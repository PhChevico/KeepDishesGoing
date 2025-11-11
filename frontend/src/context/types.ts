import type {Basket} from "../model/Basket";

export type BasketContextType = {
    basket: Basket | null;
    addItem: (dishId: string, quantity: number) => Promise<void>;
    removeItem: (dishId: string, quantity: number) => Promise<void>;
    clearBasket: () => void;
    refreshBasket: () => Promise<void>;
    createBasket: (restaurantId: string) => Promise<void>;
};
