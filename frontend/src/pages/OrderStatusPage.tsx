import {Alert, Box, CircularProgress, Container} from "@mui/material";
import {useSearchParams} from "react-router-dom";
import {useOrderDetails} from "../hooks/useBasket";
import {CustomerDetails, OrderHeader} from "../components/Order";
import {OrderDetailsSummary} from "../components/Order/OrderDetailsSummary";


export function OrderStatusPage() {
    const [searchParams] = useSearchParams();
    const basketId = searchParams.get("basketId");

    const {data: order, isLoading, isError} = useOrderDetails(basketId);

    if (!basketId) {
        return (
            <Container maxWidth="sm" sx={{mt: 5}}>
                <Alert severity="error">Missing basketId in URL</Alert>
            </Container>
        );
    }

    if (isLoading) {
        return (
            <Box sx={{display: "flex", justifyContent: "center", alignItems: "center", minHeight: "60vh"}}>
                <CircularProgress size={60}/>
            </Box>
        );
    }

    if (isError || !order) {
        return (
            <Container maxWidth="sm" sx={{mt: 5}}>
                <Alert severity="error">Failed to load order status</Alert>
            </Container>
        );
    }

    return (
        <Container maxWidth="md" sx={{py: 4}}>
            <OrderHeader basketId={order.basketId} status={order.basketStatus}/>

            {/* Display Restaurant Message if it exists */}
            {order.message && (
                <Alert
                    severity={order.basketStatus === 'REFUSED' ? "error" : "info"}
                    sx={{mb: 3}}
                >
                    **Message from Restaurant:** {order.message}
                </Alert>
            )}

            {/* Customer Details Component */}
            {order.clientInfo && order.deliveryAddress && (
                <CustomerDetails
                    clientInfo={order.clientInfo}
                    address={order.deliveryAddress}
                />
            )}

            {/* Order Summary Component */}
            <OrderDetailsSummary items={order.items} total={order.totalPrice}/>
        </Container>
    );
}