import {Box, Typography} from "@mui/material";
import type {RestaurantWithMenu} from "../../model/RestaurantWithMenu";

type Props = {
    restaurant: RestaurantWithMenu;
};

export function RestaurantHero({restaurant}: Props) {
    return (
        <Box
            sx={{
                position: "relative",
                height: 300,
                backgroundImage: restaurant.picture ? `url(${restaurant.picture})` : undefined,
                backgroundSize: "cover",
                backgroundPosition: "center",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                color: "#fff",
                textShadow: "0 0 8px rgba(0,0,0,0.7)",
            }}
        >
            <Box sx={{textAlign: "center", p: 2, backgroundColor: "rgba(0,0,0,0.3)", borderRadius: 2}}>
                <Typography variant="h3" component="h1" gutterBottom>
                    {restaurant.name}
                </Typography>
                <Typography variant="h6">
                    {restaurant.cuisineType} • {restaurant.defaultTimePreparation} min
                </Typography>
                <Typography variant="body1">
                    {restaurant.address} {restaurant.number}, {restaurant.postalCode} {restaurant.city}
                </Typography>
            </Box>
        </Box>
    );
}
