// components/RestaurantCard.tsx
import {Card, CardActionArea, CardContent, CardMedia, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";
import type {Restaurant} from "../../model/Restaurant.ts";

type Props = {
    restaurantDetails: Restaurant;
};

export function RestaurantCard({restaurantDetails}: Props) {
    const {restaurantId, name, cuisineType, street, number, postalCode, city, priceRange} = restaurantDetails;

    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/restaurant/${restaurantId}`);
    };

    return (
        <Card sx={{borderRadius: 3, boxShadow: 2}}>
            <CardActionArea onClick={handleClick}>
                <CardMedia
                    component="img"
                    height="160"
                    image={restaurantDetails.picture}
                    alt={name}
                />
                <CardContent>
                    <Typography variant="h6" gutterBottom>
                        {name}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {cuisineType} • {priceRange}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {street} {number}, {postalCode} {city}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}
