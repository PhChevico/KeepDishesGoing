import type {Address} from "../OrderDetails";


export type OrderLine = {
    dishId: string;
    dishName: string; // Assuming dishName is still part of the OrderLine
    price: number;
    quantity: number;
};

export type OrderDto = {
    orderId: string;
    deliveryAddress: Address;
    restaurantId: string;
    totalPrice: number;
    orderLines: OrderLine[];
    status: OrderStatus;
};

export type OrderStatus =
    'RECEIVED' |
    'ACCEPTED' |
    'COMPLETED' |
    'REFUSED' |
    'READY_FOR_PICK_UP'