import {Box, Card, CardContent, Divider, Typography} from "@mui/material";
import type {BasketItem} from "../../model/BasketItem";

interface OrderDetailsSummaryProps {
    items: BasketItem[];
    total: number;
}

export function OrderDetailsSummary({items, total}: OrderDetailsSummaryProps) {
    // --- Shared Logic ---
    const itemCount = items.reduce((sum, item) => sum + item.quantity, 0);
    const formatCurrency = (amount: number) => `€${amount.toFixed(2)}`;

    return (
        <Box sx={{mt: 4}}>
            <Typography variant="h6" fontWeight="bold" sx={{mb: 2}}>
                Order Items ({itemCount})
            </Typography>

            {/* 1. Itemized List (from OrderItemsList) */}
            <Box
                sx={{p: 2, border: '1px solid', borderColor: 'grey.300', borderRadius: 2, bgcolor: 'background.paper'}}>
                {items.map((item) => (
                    <Box key={item.dishId}>
                        <Box sx={{display: "flex", justifyContent: "space-between", py: 0.5}}>
                            <Typography variant="body1">
                                {item.name} <span style={{color: '#666'}}>x {item.quantity}</span>
                            </Typography>
                            <Typography variant="body1" fontWeight="medium">
                                €{((item.priceAtAddition || 0) * item.quantity).toFixed(2)}
                            </Typography>
                        </Box>
                        {/* Only add a divider if it's not the last item */}
                        {items.indexOf(item) < items.length - 1 && <Divider sx={{my: 0.5}}/>}
                    </Box>
                ))}
            </Box>

            <Divider sx={{my: 4}}/>

            {/* 2. Total Summary Card (from OrderSummary) */}
            <Card sx={{boxShadow: 4, bgcolor: 'grey.50'}}>
                <CardContent>
                    <Typography variant="h5" fontWeight="bold" gutterBottom color="primary">
                        Final Total
                    </Typography>

                    {/* Final Total Row */}
                    <Box sx={{display: 'flex', justifyContent: 'space-between', mt: 2}}>
                        <Typography variant="h6" fontWeight="bold">
                            Total:
                        </Typography>
                        <Typography variant="h6" fontWeight="bold" color="primary">
                            {formatCurrency(total)}
                        </Typography>
                    </Box>
                </CardContent>
            </Card>
        </Box>
    );
}
