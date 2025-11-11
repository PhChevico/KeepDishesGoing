import {Box, Card, CardContent, IconButton, Typography} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import DeleteIcon from "@mui/icons-material/Delete";
import type {BasketItem as BasketItemType} from "../../model/BasketItem";

interface BasketItemProps {
    item: BasketItemType;
    onUpdateQuantity: (dishId: string, quantity: number, type: "add" | "remove") => Promise<void>;
    onRemove: (dishId: string) => Promise<void>;
    disabled?: boolean;
}

export function BasketItem({item, onUpdateQuantity, onRemove, disabled}: BasketItemProps) {
    // Safety checks
    if (!item || !item.dishId) {
        console.error("Invalid basket item:", item);
        return null;
    }

    const handleIncrease = () => onUpdateQuantity(item.dishId, 1, "add");
    const handleDecrease = () => {
        if (item.quantity > 1) {
            onUpdateQuantity(item.dishId, 1, "remove");
        } else {
            onRemove(item.dishId);
        }
    };
    const handleRemove = () => onRemove(item.dishId);

    const price = item.priceAtAddition ?? 0;  // Changed
    const quantity = item.quantity ?? 0;
    const totalPrice = price * quantity;

    return (
        <Card sx={{mb: 2, boxShadow: 2}}>
            <CardContent>
                <Box sx={{display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                    {/* Dish Info */}
                    <Box sx={{flex: 1}}>
                        <Typography variant="h6" fontWeight="bold">
                            {item.name || "Unknown Dish"}
                        </Typography>
                        <Typography variant="body1" color="primary" sx={{mt: 1}}>
                            €{price.toFixed(2)} each
                        </Typography>
                    </Box>

                    {/* Quantity Controls */}
                    <Box sx={{display: "flex", alignItems: "center", gap: 2}}>
                        <Box sx={{display: "flex", alignItems: "center", gap: 1}}>
                            <IconButton
                                size="small"
                                onClick={handleDecrease}
                                disabled={disabled}
                                color="primary"
                                sx={{
                                    border: "1px solid",
                                    borderColor: "primary.main",
                                }}
                            >
                                <RemoveIcon fontSize="small"/>
                            </IconButton>

                            <Typography variant="h6" sx={{minWidth: 30, textAlign: "center"}}>
                                {quantity}
                            </Typography>

                            <IconButton
                                size="small"
                                onClick={handleIncrease}
                                disabled={disabled}
                                color="primary"
                                sx={{
                                    border: "1px solid",
                                    borderColor: "primary.main",
                                }}
                            >
                                <AddIcon fontSize="small"/>
                            </IconButton>
                        </Box>

                        {/* Price */}
                        <Typography
                            variant="h6"
                            fontWeight="bold"
                            sx={{minWidth: 80, textAlign: "right"}}
                        >
                            €{totalPrice.toFixed(2)}
                        </Typography>

                        {/* Delete Button */}
                        <IconButton
                            onClick={handleRemove}
                            disabled={disabled}
                            color="error"
                            sx={{ml: 1}}
                        >
                            <DeleteIcon/>
                        </IconButton>
                    </Box>
                </Box>
            </CardContent>
        </Card>
    );
}