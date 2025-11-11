import {Alert, Box, CircularProgress, Container, Typography} from "@mui/material";
import {useEffect, useState} from "react";
import {useBasket, useUpdateBasket} from "../hooks/useBasket";
import {BasketItem} from "../components/Basket/BasketItem";
import {BasketSummary} from "../components/Basket/BasketSummary";
import {EmptyBasket} from "../components/Basket/EmptyBasket";

export function BasketDetail() {
    const [basketId, setBasketId] = useState<string | undefined>();

    useEffect(() => {
        const restaurantId = localStorage.getItem("current_restaurant_id");
        if (restaurantId) {
            const storedBasketId = localStorage.getItem(`basket_${restaurantId}`);
            setBasketId(storedBasketId ?? undefined);
        }
    }, []);

    const {basket, isLoading, isError, refreshBasket} = useBasket(basketId);

    const {updateBasket, status: updateStatus} = useUpdateBasket();

    const handleUpdateQuantity = async (dishId: string, quantity: number, type: "add" | "remove") => {
        if (!basketId || !basket) return;

        const item = basket.items.find((i) => i.dishId === dishId);
        if (!item) return;

        const dishName = type === "add" ? item.name : undefined;

        try {
            await updateBasket({
                basketId,
                dishId,
                dishName, // Pass the name only for 'add'
                quantity,
                type
            });
            await refreshBasket();
        } catch (error) {
            console.error("Failed to update basket:", error);
        }
    };

    const handleRemoveItem = async (dishId: string) => {
        if (!basketId || !basket) return;

        const item = basket.items.find((i) => i.dishId === dishId);
        if (!item) return;

        try {
            await updateBasket({
                basketId,
                dishId,
                quantity: item.quantity,
                type: "remove",
            });
            await refreshBasket();
        } catch (error) {
            console.error("Failed to remove item:", error);
        }
    };

    // Loading state
    if (isLoading) {
        return (
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    minHeight: "60vh",
                }}
            >
                <CircularProgress size={60}/>
            </Box>
        );
    }

    // Error state
    if (isError) {
        return (
            <Container maxWidth="md" sx={{mt: 4}}>
                <Alert severity="error">Failed to load basket. Please try again later.</Alert>
            </Container>
        );
    }

    // Empty basket
    if (!basket || !basket.items || basket.items.length === 0) {
        return <EmptyBasket/>;
    }

    // Use totalPrice from basket response
    const total = basket.totalPrice ?? 0;
    const itemCount = basket.items.reduce((sum, item) => sum + item.quantity, 0);

    const isUpdating = updateStatus.isPending;

    return (
        <Container maxWidth="lg" sx={{py: 4}}>
            <Typography variant="h4" fontWeight="bold" gutterBottom>
                Your Basket
            </Typography>
            <Typography variant="body1" color="text.secondary" sx={{mb: 4}}>
                Review your items and proceed to checkout
            </Typography>

            <Box sx={{display: "flex", gap: 3, flexDirection: {xs: "column", md: "row"}}}>
                {/* Basket Items */}
                <Box sx={{flex: {xs: "1", md: "2"}}}>
                    {basket.items.map((item) => (
                        <BasketItem
                            key={item.dishId}
                            item={item}
                            onUpdateQuantity={handleUpdateQuantity}
                            onRemove={handleRemoveItem}
                            disabled={isUpdating}
                        />
                    ))}
                </Box>

                {/* Summary */}
                <Box sx={{flex: "1"}}>
                    <BasketSummary
                        total={total}
                        itemCount={itemCount}
                        disabled={isUpdating}
                        basketId={basketId}
                    />
                </Box>
            </Box>
        </Container>
    );
}