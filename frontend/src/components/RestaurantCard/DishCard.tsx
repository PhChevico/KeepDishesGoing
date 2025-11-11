import {Box, Button, Card, CardContent, CardMedia, Typography} from "@mui/material";
import type {Dish} from "../../model/Dish";
import type {DishStatus} from "../../model/RestaurantOwner/Dish.ts";

type Props = {
    dish: Dish;
    onAddToCart: (dish: Dish) => void;
    disabled?: boolean;
    dishStatus: DishStatus;
};

export function DishCard({dish, onAddToCart, disabled, dishStatus}: Props) {

    const isAvailable = dishStatus === 'PUBLISHED';

    const buttonText = isAvailable
        ? 'Add to Cart'
        : dishStatus === 'OUT_OF_STOCK'
            ? 'Out of Stock'
            : dishStatus === 'UNPUBLISHED'
                ? 'Unavailable'
                : 'Not Available';

    const finalDisabled = disabled || !isAvailable;

    const buttonSx = isAvailable
        ? {} // Default primary color
        : {
            bgcolor: 'grey.300',
            color: 'text.secondary',
            '&:hover': {bgcolor: 'grey.400'}
        };

    return (
        <Card sx={{width: 300, borderRadius: 3, boxShadow: 2}}>
            {dish.picture && <CardMedia component="img" height="140" image={dish.picture} alt={dish.name}/>}
            <CardContent>
                <Typography variant="h6">{dish.name}</Typography>
                <Typography variant="body2" color="text.secondary">
                    {dish.description}
                </Typography>
                <Typography variant="subtitle1" sx={{mt: 1}}>
                    ${dish.price.toFixed(2)}
                </Typography>

                <Box sx={{mt: 2}}>
                    <Button
                        variant="contained"
                        color={isAvailable ? 'primary' : 'inherit'}
                        fullWidth
                        onClick={() => onAddToCart(dish)}
                        disabled={finalDisabled}
                        sx={buttonSx}
                    >
                        {buttonText}
                    </Button>
                </Box>
            </CardContent>
        </Card>
    );
}
