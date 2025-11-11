import {Alert, Box, CircularProgress, Container, Typography} from "@mui/material";
import {useOwnerRestaurant, useRestaurantOrders} from "../../hooks/useOwnerRestaurant";
import {OrderList} from "../../components/RestaurantOwner/OrderList";


export function RestaurantOrdersPage() {
    const {restaurant} = useOwnerRestaurant(); // Use this hook to get the owner's restaurant
    const restaurantId = restaurant?.id; // Extract the ID

    const {data: orders, isLoading, isError, error} = useRestaurantOrders(restaurantId);

    if (!restaurantId) {
        if (!restaurant) {
            return (
                <Box sx={{p: 4, textAlign: 'center'}}>
                    <CircularProgress/>
                    <Typography variant="body1" sx={{mt: 2}}>Verifying restaurant ownership...</Typography>
                </Box>
            );
        }
        return <Alert severity="warning">Restaurant ID is not available. Please ensure your restaurant is
            created.</Alert>;
    }

    if (isLoading) {
        return (
            <Box sx={{p: 4, textAlign: 'center'}}>
                <CircularProgress/>
                <Typography variant="body1" sx={{mt: 2}}>Loading orders...</Typography>
            </Box>
        );
    }

    if (isError) {
        return <Alert severity="error">Failed to load orders: {error?.message}</Alert>;
    }

    return (
        <Container maxWidth="lg" sx={{mt: 4, mb: 4}}>
            <Typography variant="h4" gutterBottom component="h1">
                Restaurant Orders 📦
            </Typography>

            {orders && orders.length > 0 ? (
                <OrderList orders={orders}/>
            ) : (
                <Alert severity="info" sx={{mt: 3}}>
                    No pending or recent orders found for your restaurant.
                </Alert>
            )}
        </Container>
    );
}