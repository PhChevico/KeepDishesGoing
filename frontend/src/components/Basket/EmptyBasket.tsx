import {Box, Button, Typography} from "@mui/material";
import ShoppingBasketIcon from "@mui/icons-material/ShoppingBasket";
import {useNavigate} from "react-router-dom";

export function EmptyBasket() {
    const navigate = useNavigate();

    return (
        <Box
            sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                minHeight: "60vh",
                textAlign: "center",
                p: 4,
            }}
        >
            <ShoppingBasketIcon
                sx={{
                    fontSize: 120,
                    color: "text.secondary",
                    mb: 3,
                    opacity: 0.5,
                }}
            />
            <Typography variant="h4" fontWeight="bold" gutterBottom>
                Your basket is empty
            </Typography>
            <Typography variant="body1" color="text.secondary" sx={{mb: 4, maxWidth: 400}}>
                Looks like you haven't added any dishes yet. Explore our restaurants and start
                ordering!
            </Typography>
            <Button
                variant="contained"
                size="large"
                onClick={() => navigate("/customer/restaurants")}
                sx={{textTransform: "none", px: 4}}
            >
                Browse Restaurants
            </Button>
        </Box>
    );
}