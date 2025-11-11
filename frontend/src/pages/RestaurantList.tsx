import {Box, CircularProgress, Container, Typography} from "@mui/material";
import {useAllRestaurants} from "../hooks/useAllRestaurants";
import {RestaurantCard} from "../components/RestaurantCard";

export function RestaurantList() {
    const {data: restaurants, isLoading, isError} = useAllRestaurants();

    if (isLoading) {
        return (
            <Container sx={{textAlign: "center", mt: 10}}>
                <CircularProgress/>
            </Container>
        );
    }

    if (isError) {
        return (
            <Container sx={{textAlign: "center", mt: 10}}>
                <Typography color="error" variant="h6">
                    Error loading restaurants.
                </Typography>
            </Container>
        );
    }

    return (
        <Container sx={{mt: 4}}>
            <Typography variant="h4" gutterBottom>
                Restaurants
            </Typography>

            <Box
                sx={{
                    display: "grid",
                    gridTemplateColumns: {
                        xs: "1fr",
                        sm: "1fr 1fr",
                        md: "1fr 1fr 1fr",
                    },
                    gap: 2,
                }}
            >
                {restaurants?.map((r) => (
                    <RestaurantCard
                        key={r.name}
                        restaurantDetails={r}
                    />
                ))}

            </Box>
        </Container>
    );
}
