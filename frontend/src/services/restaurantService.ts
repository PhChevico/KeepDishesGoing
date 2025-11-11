import axios from 'axios';
import type {CreateDishCommand, DishDto, UpdateDishRequest} from '../model/RestaurantOwner/Dish';
import type {RestaurantDto} from '../model/RestaurantOwner/Restaurant';
import type {CreateFoodMenuCommand, FoodMenuDto} from "../model/RestaurantOwner/FoodMenu";
import type {OrderDto} from "../model/RestaurantOwner/OrderDetails.ts";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL

const DISH_OWNER_API_URL = `${BACKEND_URL}/api/restaurant_owner/dishes`;

const MENU_OWNER_API_URL = `${BACKEND_URL}/api/restaurant_owner/food_menu`;

const RESTAURANT_OWNER_API_URL = `${BACKEND_URL}/api/ownerRestaurants`;


export const fetchAllDishes = async (restaurantId: string): Promise<DishDto[]> => {
    const response = await axios.get(DISH_OWNER_API_URL, {
        params: {
            restaurantId: restaurantId,
        }
    });
    return response.data;
};

export const createDish = async (dishData: CreateDishCommand): Promise<DishDto> => {
    const response = await axios.post(DISH_OWNER_API_URL, dishData);
    return response.data;
};

export const updateDish = async (dishData: UpdateDishRequest): Promise<DishDto> => {
    const response = await axios.patch(DISH_OWNER_API_URL, dishData);
    return response.data;
};

export const fetchMenuDishes = async (restaurantId: string): Promise<DishDto[]> => {
    const response = await axios.get(MENU_OWNER_API_URL, {
        params: {
            restaurantId: restaurantId,
        }
    });
    return response.data;
};


export const fetchOwnerRestaurant = async (): Promise<RestaurantDto | null> => {
    try {
        const response = await axios.get(RESTAURANT_OWNER_API_URL);
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.response?.status === 404) {
            return null;
        }
        throw error;
    }
};

export const createRestaurant = async (restaurantData: RestaurantDto): Promise<RestaurantDto> => {
    const response = await axios.post(RESTAURANT_OWNER_API_URL, restaurantData);
    return response.data;
};


export const createFoodMenu = async (command: CreateFoodMenuCommand): Promise<FoodMenuDto> => {
    const response = await axios.post<FoodMenuDto>(MENU_OWNER_API_URL, command);
    return response.data;
};

export const fetchRestaurantMenu = async (restaurantId: string): Promise<FoodMenuDto | null> => {
    try {
        const response = await axios.get<FoodMenuDto>(`${MENU_OWNER_API_URL}/${restaurantId}`);
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.response?.status === 404) {
            return null;
        }
        throw error;
    }
};


export const publishDish = async (dishId: string, menuId: string): Promise<DishDto> => {
    const response = await axios.post(`${DISH_OWNER_API_URL}/${dishId}/publish-to-menu/${menuId}`);
    return response.data;
};

export const unpublishDish = async (dishId: string): Promise<DishDto> => {
    const response = await axios.patch(`${DISH_OWNER_API_URL}/${dishId}/unpublish-from-menu`);
    return response.data;
};

export const markDishOutOfStock = async (dishId: string): Promise<DishDto> => {
    const response = await axios.patch(`${DISH_OWNER_API_URL}/${dishId}/mark-dish-out-of-stock`);
    return response.data;
};

export const markDishInStock = async (dishId: string): Promise<DishDto> => {
    const response = await axios.patch(`${DISH_OWNER_API_URL}/${dishId}/mark-dish-in-stock`);
    return response.data;
};

export const acceptAllChanges = async (): Promise<DishDto[]> => {
    const response = await axios.post(`${DISH_OWNER_API_URL}/apply-pending-changes`);
    return response.data;
};

export async function getRestaurantOrders(restaurantId: string): Promise<OrderDto[]> {
    const {data} = await axios.get(`${RESTAURANT_OWNER_API_URL}/${restaurantId}/orders`);

    return data.map((order: any) => ({
        orderId: order.orderId,
        deliveryAddress: order.deliveryAddress,
        restaurantId: order.restaurantId,
        totalPrice: order.totalPrice ?? 0,
        orderLines: (order.orderLines ?? []).map((line: any) => ({
            dishId: line.dishId?.dishId ?? '',
            dishName: line.dishName ?? '(Unknown Dish)',
            price: line.priceAtAddition ?? line.price ?? 0,
            quantity: line.quantity ?? 1
        })),
        status: order.status ?? 'RECEIVED'
    }));
}

export async function acceptOrder(orderId: string): Promise<void> {
    await axios.post(`${RESTAURANT_OWNER_API_URL}/orders/${orderId}/accept`);
}

export async function refuseOrder(orderId: string, message: string): Promise<void> {
    await axios.post(`${RESTAURANT_OWNER_API_URL}/orders/${orderId}/refuse`, message, {
        headers: {
            'Content-Type': 'text/plain'
        }
    });
}

export async function markOrderReadyForPickup(orderId: string): Promise<void> {
    await axios.post(`${RESTAURANT_OWNER_API_URL}/${orderId}/ready-for-pickup`);
}