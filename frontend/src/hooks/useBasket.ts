import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import * as basketService from "../services/clientService.ts";
import {addItem, getOrderDetails, removeItem} from "../services/clientService.ts";
import type {Basket} from "../model/Basket";
import type {OrderDetails} from "../model/OrderDetails.ts";

function clearStaleBasketId(staleBasketId: string) {
    let wasCleared = false;

    for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);

        if (key && key.startsWith("basket_") && localStorage.getItem(key) === staleBasketId) {
            localStorage.removeItem(key);
            console.warn(`Cleared stale basket ID: ${staleBasketId} from localStorage key: ${key}`);
            wasCleared = true;

            const currentRestaurantId = localStorage.getItem("current_restaurant_id");
            if (currentRestaurantId && key.includes(currentRestaurantId)) {
                localStorage.removeItem("current_restaurant_id");
            }
            break;
        }
    }

    if (!wasCleared && localStorage.getItem("anonymous_id") === staleBasketId) {
        localStorage.removeItem("anonymous_id");
        console.warn(`Cleared stale anonymous ID: ${staleBasketId}`);
    }
}


export function useBasket(basketId?: string) {
    const queryClient = useQueryClient();

    const {data: basket, isLoading, isError, refetch} = useQuery<Basket | null>({
        queryKey: ["basket", basketId],
        queryFn: async () => {
            if (!basketId) return null;
            try {
                return await basketService.getBasket(basketId);
            } catch (err: any) {
                const status = err.response?.status;

                if (status === 401 || status === 404) {
                    console.error(`Basket fetch failed with status ${status} for ID: ${basketId}.`);

                    clearStaleBasketId(basketId);

                    return null;
                }

                throw err;
            }
        },
        enabled: !!basketId,
        retry: 1,
    });

    const updateBasketMutation = useMutation<
        Basket,
        unknown,
        { basketId: string; dishId: string; dishName?: string; quantity: number; type: "add" | "remove" }
    >({
        mutationFn: ({basketId, dishId, dishName, quantity, type}) => {
            if (type === "add") {
                if (!dishName) {
                    throw new Error("dishName is required to add a dish");
                }
                return addItem(basketId, dishId, dishName, quantity);
            } else {
                return removeItem(basketId, dishId, quantity);
            }
        },
        onSuccess: (basket, variables) => {
            queryClient.setQueryData(["basket", variables.basketId], basket);
        },
    });

    return {
        basket,
        isLoading,
        isError,
        refreshBasket: refetch,
        updateBasket: updateBasketMutation.mutateAsync,
        basketStatus: updateBasketMutation,
    };
}

// export function useCreateBasket() {
//     const queryClient = useQueryClient();
//
//     const {mutateAsync: createBasket, isPending, isError, error} = useMutation<Basket, unknown, string>({
//         mutationFn: (restaurantId: string) => basketService.createBasket(restaurantId),
//         onSuccess: (basket) => {
//             queryClient.setQueryData(["basket", basket.basketId], basket);
//         },
//     });
//
//     return {createBasket, isPending, isError, error};
// }


export function useUpdateBasket() {
    const queryClient = useQueryClient();

    const updateBasketMutation = useMutation<Basket, unknown, {
        basketId: string,
        dishId: string,
        dishName?: string,
        quantity: number,
        type: "add" | "remove"
    }>({
        mutationFn: ({basketId, dishId, dishName, quantity, type}) => {
            if (type === "add") {
                if (!dishName) {
                    throw new Error("dishName is required to add a dish");
                }
                return addItem(basketId, dishId, dishName, quantity);
            } else {
                return removeItem(basketId, dishId, quantity);
            }
        },
        onSuccess: (basket, variables) => {
            queryClient.setQueryData(["basket", variables.basketId], basket);
        },
    });

    return {
        updateBasket: updateBasketMutation.mutateAsync,
        status: updateBasketMutation,
    };
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

export function useCheckoutBasket() {
    const queryClient = useQueryClient();

    const {mutateAsync: checkout, isPending, isError, error} = useMutation<Basket, unknown, {
        basketId: string;
        request: CheckoutBasketRequest
    }>({
        mutationFn: ({basketId, request}) => basketService.checkoutBasket(basketId, request),
        onSuccess: (basket, variables) => {
            queryClient.setQueryData(["basket", variables.basketId], basket);
        },
    });

    return {checkout, isPending, isError, error};
}


export function useCreatePayment() {
    const {mutateAsync: createPayment, isPending, isError, error} = useMutation<string, unknown, string>({
        mutationFn: (basketId: string) => basketService.createPayment(basketId),
    });

    return {createPayment, isPending, isError, error};
}

export function useConfirmOrder() {
    const {mutateAsync: confirmOrder, isPending, isError, error} = useMutation<any, unknown, string>({
        mutationFn: (orderId: string) => basketService.confirmOrder(orderId),
    });

    return {confirmOrder, isPending, isError, error};
}

export const useOrderDetails = (basketId: string | null) => {
    return useQuery<OrderDetails>({
        queryKey: ["orderDetails", basketId],
        queryFn: () => getOrderDetails(basketId!),
        enabled: !!basketId,
    });
};