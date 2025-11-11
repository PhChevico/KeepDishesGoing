import type {BasketItem} from "./BasketItem";
import type {BasketStatus} from "./BasketStatus";

export type Basket = {
    basketId: string;
    restaurantId: string;
    items: BasketItem[];
    totalPrice: number;
    basketStatus: BasketStatus;
    message?: string;
};
