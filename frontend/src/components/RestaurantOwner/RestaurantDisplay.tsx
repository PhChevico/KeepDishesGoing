// src/components/RestaurantDisplay.tsx

import {Box, Card, CardContent, Typography} from "@mui/material";
import type {RestaurantDto} from "../../model/RestaurantOwner/Restaurant";

interface RestaurantDisplayProps {
    restaurant: RestaurantDto;
}

export function RestaurantDisplay({restaurant}: RestaurantDisplayProps) {
    return (
        <Card sx={{mb: 4, mt: 4, boxShadow: 6}}>
            <CardContent>
                <Typography variant="h4" fontWeight="bold" gutterBottom color="primary">
                    Welcome, {restaurant.name}! 🍽️
                </Typography>

                <Box sx={{mt: 3, display: 'grid', gridTemplateColumns: {xs: '1fr', sm: '1fr 1fr'}, gap: 4}}>
                    {/* General Info */}
                    <Box>
                        <Typography variant="subtitle1" fontWeight="medium">General Information:</Typography>
                        <Typography variant="body1">Email: {restaurant.email}</Typography>
                        <Typography variant="body1">Cuisine: {restaurant.cuisineType}</Typography>
                        <Typography variant="body1">Price Range: {restaurant.priceRange}</Typography>
                    </Box>

                    {/* Operational Details */}
                    <Box>
                        <Typography variant="body1">Default Prep
                            Time: {restaurant.defaultTimePreparation} min</Typography>
                        {/* Address displayed similarly to your CustomerDetails component */}
                        <Typography
                            variant="body1">Address: {restaurant.street} {restaurant.number}, {restaurant.postalCode} {restaurant.city}</Typography>
                        <Typography variant="body1">Country: {restaurant.country}</Typography>
                    </Box>
                </Box>
            </CardContent>
        </Card>
    );
}