import {Alert, Box, Button, CircularProgress, Container, Typography} from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import {useNavigate, useSearchParams} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import {useConfirmOrder} from "../hooks/useBasket.ts";

export function CheckoutSuccessPage() {
    const navigate = useNavigate();
    const [searchParams, setSearchParams] = useSearchParams();
    const basketId = searchParams.get("basketId");
    const confirmed = searchParams.get("confirmed"); // Check if already confirmed

    const {confirmOrder, isPending} = useConfirmOrder();

    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(confirmed === "true"); // Initialize from URL

    const hasConfirmed = useRef(false);

    useEffect(() => {
        if (confirmed === "true") {
            console.log("Order already confirmed (from URL parameter)");
            return;
        }

        if (hasConfirmed.current) {
            return;
        }

        if (!basketId) {
            setError("Missing basketId in URL.");
            return;
        }

        hasConfirmed.current = true;

        const confirm = async () => {
            try {
                await confirmOrder(basketId);
                setSuccess(true);
                setError(null);

                setSearchParams({basketId, confirmed: "true"});

                const restaurantId = localStorage.getItem("current_restaurant_id");
                if (restaurantId) {
                    localStorage.removeItem(`basket_${restaurantId}`);
                }

            } catch (err: any) {
                console.error("Order confirmation failed:", err);

                const errorMessage = err.response?.data?.message || err.message;

                if (errorMessage && errorMessage.includes("already been ordered")) {
                    console.warn("Server reported basket already ordered. Treating as success.");
                    setSuccess(true);
                    setError(null);
                    setSearchParams({basketId, confirmed: "true"});
                    return;
                }

                setError(errorMessage || "Failed to confirm order.");
                hasConfirmed.current = false; // Allow retry on real errors
            }
        };

        confirm();
    }, [basketId, confirmed]); // Add confirmed to dependencies

    if (isPending && !success) {
        return (
            <Box sx={{display: "flex", justifyContent: "center", alignItems: "center", minHeight: "60vh"}}>
                <CircularProgress size={60}/>
            </Box>
        );
    }

    return (
        <Container maxWidth="sm">
            <Box
                sx={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    minHeight: "60vh",
                    textAlign: "center",
                }}
            >
                {error ? (
                    <>
                        <Alert severity="error" sx={{mb: 3}}>{error}</Alert>
                        <Button
                            variant="contained"
                            size="large"
                            onClick={() => navigate("/customer/restaurants")}
                            sx={{textTransform: "none", px: 4}}
                        >
                            Back to Restaurants
                        </Button>
                    </>
                ) : success ? (
                    <>
                        <CheckCircleIcon sx={{fontSize: 100, color: "success.main", mb: 3}}/>
                        <Typography variant="h3" fontWeight="bold" gutterBottom>
                            Order Placed!
                        </Typography>
                        <Typography variant="body1" color="text.secondary" sx={{mb: 4, maxWidth: 400}}>
                            Thank you for your order. You will receive a confirmation email shortly with your order
                            details.
                        </Typography>
                        <Button
                            variant="contained"
                            size="large"
                            onClick={() => navigate(`/order-status?basketId=${basketId}`)}
                            sx={{textTransform: "none", px: 4}}
                        >
                            Track Your Order
                        </Button>
                    </>
                ) : null}
            </Box>
        </Container>
    );
}