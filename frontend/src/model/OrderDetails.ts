import type {BasketItem} from "./BasketItem";
import type {BasketStatus} from "./BasketStatus";

export type Address = {
    street: string;
    number: number;
    postalCode: number;
    city: string;
    country: string;
};

export type ClientInfo = {
    firstName: string;
    lastName: string;
    emailAddress: string;
};

export type OrderDetails = {
    basketId: string;
    restaurantId: string;
    items: BasketItem[];
    totalPrice: number;
    basketStatus: BasketStatus;
    message?: string;
    anonymousId: string;
    paymentSessionId: string;

    // The enriched data
    clientInfo: ClientInfo;
    deliveryAddress: Address;
};
