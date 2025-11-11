import axios from "axios";
import type {Basket} from "../model/Basket";
import type {Restaurant} from "../model/Restaurant.ts";
import type {RestaurantWithMenu} from "../model/RestaurantWithMenu.ts";
import type {OrderDetails} from "../model/OrderDetails.ts";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL


export async function getBasket(basketId: string): Promise<Basket> {
    const {data} = await axios.get(`${BACKEND_URL}/api/basket/${basketId}`);
    console.log(data)
    return data;
}

export async function getAllRestaurants(): Promise<Restaurant[]> {
    const {data} = await axios.get(`${BACKEND_URL}/api/restaurants`);
    return data;
}

export async function getBasketForRestaurant(restaurantId: string): Promise<Basket | null> {
    try {
        const {data} = await axios.get(`${BACKEND_URL}/api/basket/restaurant/${restaurantId}`);
        return data;
    } catch (err: any) {
        if (err?.response?.status === 404) return null;
        throw err;
    }
}

let ANONYMOUS_ID: string | null = null;

export function getOrCreateAnonymousId(): string {
    if (!ANONYMOUS_ID) {
        let storedId = localStorage.getItem("anonymous_id");
        if (!storedId) {
            // This is a common way to generate client-side UUIDs
            storedId = crypto.randomUUID();
            localStorage.setItem("anonymous_id", storedId);
        }
        ANONYMOUS_ID = storedId;
    }
    return ANONYMOUS_ID;
}


export async function createBasket(restaurantId: string): Promise<Basket> {
    const anonymousId = getOrCreateAnonymousId();
    const {data} = await axios.post(`${BACKEND_URL}/api/basket`, {
        restaurantId,
        anonymousId
    });

    setAnonymousHeader(anonymousId);

    return data;
}

export function setAnonymousHeader(anonymousId: string) {
    axios.defaults.headers.common['X-Anonymous-ID'] = anonymousId;
}


export async function addItem(basketId: string, dishId: string, dishName: string, quantity: number): Promise<Basket> {
    const {data} = await axios.post(`${BACKEND_URL}/api/basket/${basketId}/addDish`, {dishId, dishName, quantity});
    return data;
}

export async function removeItem(basketId: string, dishId: string, quantity: number): Promise<Basket> {
    const {data} = await axios.patch(`${BACKEND_URL}/api/basket/${basketId}/removeDish`, {dishId, quantity});
    return data;
}

export async function getRestaurantWithMenu(restaurantId: string): Promise<RestaurantWithMenu> {
    const {data} = await axios.get(`${BACKEND_URL}/api/restaurants/${restaurantId}/menu`);
    return data;
}

interface CheckoutBasketRequest {
    firstName: string;
    lastName: string;
    street: string;
    number: string;
    postalCode: string;
    city: string;
    country: string;
    emailAddress: string;
}

export const checkoutBasket = (basketId: string, request: CheckoutBasketRequest): Promise<Basket> => {
    return axios.post(`${BACKEND_URL}/api/basket/${basketId}/checkout`, request).then((res) => res.data);
};

export const createPayment = (basketId: string): Promise<string> => {
    return axios.post(`${BACKEND_URL}/api/payments/create?basketId=${basketId}`).then((res) => res.data);
};

export const confirmOrder = (orderId: string) => {
    const anonymousId = getOrCreateAnonymousId();

    return axios.post(
        `${BACKEND_URL}/api/order/${orderId}/confirmOrder`,
        {}, // empty body
        {
            headers: {
                'X-Anonymous-ID': anonymousId
            }
        }
    ).then(res => res.data);
};


export async function getOrderDetails(basketId: string): Promise<OrderDetails> {
    const {data} = await axios.get(`${BACKEND_URL}/api/basket/${basketId}/details`);
    return data;
}