import {Box, Divider, Typography} from "@mui/material";
import type {BasketItem} from "../../model/BasketItem";

interface Props {
    items: BasketItem[];
}

export function OrderItemsList({items}: Props) {
    return (
        <Box>
            {items.map((item) => (
                <Box key={item.dishId} sx={{mb: 2}}>
                    <Box sx={{display: "flex", justifyContent: "space-between"}}>
                        <Typography variant="body2">
                            {item.name} x {item.quantity}
                        </Typography>
                        <Typography variant="body2">
                            €{((item.priceAtAddition || 0) * item.quantity).toFixed(2)}
                        </Typography>
                    </Box>
                    <Divider sx={{my: 1}}/>
                </Box>
            ))}
        </Box>
    );
}
