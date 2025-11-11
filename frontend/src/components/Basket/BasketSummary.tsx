import {Box, Button, Card, CardContent, Divider, Typography} from "@mui/material";
import ShoppingCartCheckoutIcon from "@mui/icons-material/ShoppingCartCheckout";
import {useNavigate} from "react-router-dom";

interface BasketSummaryProps {
    total: number;
    disabled?: boolean;
    itemCount: number;
    basketId?: string;
}

export function BasketSummary({
                                  total,
                                  disabled,
                                  itemCount,
                                  basketId,
                              }: BasketSummaryProps) {
    const navigate = useNavigate();

    const handleCheckout = () => {
        if (basketId) {
            navigate(`/checkout/${basketId}`);
        }
    };

    return (
        <Card sx={{boxShadow: 3, position: "sticky", top: 80}}>
            <CardContent>
                <Typography variant="h5" fontWeight="bold" gutterBottom>
                    Order Summary
                </Typography>

                <Divider sx={{my: 2}}/>

                <Box sx={{display: "flex", justifyContent: "space-between", mb: 1}}>
                    <Typography variant="body1">Items ({itemCount})</Typography>
                </Box>

                <Divider sx={{my: 2}}/>

                <Box sx={{display: "flex", justifyContent: "space-between", mb: 3}}>
                    <Typography variant="h6" fontWeight="bold">
                        Total
                    </Typography>
                    <Typography variant="h6" fontWeight="bold" color="primary">
                        €{total.toFixed(2)}
                    </Typography>
                </Box>

                <Button
                    variant="contained"
                    fullWidth
                    size="large"
                    onClick={handleCheckout}
                    disabled={disabled || itemCount === 0}
                    startIcon={<ShoppingCartCheckoutIcon/>}
                    sx={{
                        py: 1.5,
                        textTransform: "none",
                        fontSize: "1.1rem",
                    }}
                >
                    Proceed to Checkout
                </Button>
            </CardContent>
        </Card>
    );
}