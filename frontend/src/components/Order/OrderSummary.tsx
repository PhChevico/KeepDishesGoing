import {Box, Card, CardContent, Divider, Typography} from "@mui/material";
import type {BasketItem} from "../../model/BasketItem";

interface OrderSummaryProps {
    items: BasketItem[];
    total: number;
}

export function OrderSummary({items, total}: OrderSummaryProps) {
    const itemCount = items.reduce((sum, item) => sum + item.quantity, 0);

    const formatCurrency = (amount: number) => `€${amount.toFixed(2)}`;

    return (
        <Card sx={{boxShadow: 4, bgcolor: 'grey.50'}}>
            <CardContent>
                <Typography variant="h5" fontWeight="bold" gutterBottom color="primary">
                    Order Summary
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{mb: 2}}>
                    {itemCount} {itemCount === 1 ? 'Item' : 'Items'}
                </Typography>
                <Divider sx={{mb: 2}}/>

                {/* 🚀 FINAL TOTAL Row - Now uses the 'total' prop directly */}
                <Box sx={{display: 'flex', justifyContent: 'space-between'}}>
                    <Typography variant="h6" fontWeight="bold">
                        Total:
                    </Typography>
                    <Typography variant="h6" fontWeight="bold" color="primary">
                        {formatCurrency(total)}
                    </Typography>
                </Box>
            </CardContent>
        </Card>
    );
}
