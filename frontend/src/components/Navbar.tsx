import {useEffect, useState} from "react";
import {
    AppBar,
    Badge,
    Box,
    Button,
    Container,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemButton,
    ListItemText,
    Toolbar,
    Typography,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import ShoppingBasketIcon from "@mui/icons-material/ShoppingBasket";
import {useLocation, useNavigate} from "react-router-dom";
import {useBasket} from "../hooks/useBasket";
import useSecurityContext from "../hooks/useSecurityContext";
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import LoginIcon from '@mui/icons-material/Login';

const allLinks = {
    PUBLIC: [
        {label: "Home", path: "/"},
        {label: "Restaurants", path: "/customer/restaurants"},
        {label: "Basket", path: "/basket"},
    ],
    OWNER: [
        {label: "Dashboard", path: "/restaurant"},
        {label: "My Orders", path: "/restaurant/orders"},
    ]
};

export function Navbar() {
    const navigate = useNavigate();
    const location = useLocation();
    const [mobileOpen, setMobileOpen] = useState(false);
    const toggleDrawer = () => setMobileOpen(!mobileOpen);

    const {isAuthenticated, login, logout} = useSecurityContext();
    const isUserLoggedIn = isAuthenticated();

    const handleNavigate = (path: string) => {
        navigate(path);
        setMobileOpen(false);
    };

    const [currentRestaurantId, setCurrentRestaurantId] = useState<string | null>(
        localStorage.getItem("current_restaurant_id")
    );

    useEffect(() => {
        const interval = setInterval(() => {
            const restaurantId = localStorage.getItem("current_restaurant_id");
            if (restaurantId !== currentRestaurantId) {
                setCurrentRestaurantId(restaurantId);
            }
        }, 500);

        return () => clearInterval(interval);
    }, [currentRestaurantId]);

    const basketId = currentRestaurantId
        ? localStorage.getItem(`basket_${currentRestaurantId}`)
        : null;

    const {basket, isLoading: basketLoading} = useBasket(basketId ?? undefined);
    const basketCount = basket?.items?.reduce((total, item) => total + item.quantity, 0) || 0;

    const baseLinks = isUserLoggedIn ? allLinks.OWNER : allLinks.PUBLIC;
    const linksToRender = baseLinks.map(link =>
        link.label === "Basket"
            ? {...link, icon: <ShoppingBasketIcon fontSize="medium"/>}
            : link
    );

    return (
        <>
            <AppBar
                position="sticky"
                sx={{
                    background: "linear-gradient(90deg, #0ea5e9 0%, #38bdf8 100%)",
                    boxShadow: 4,
                }}
            >
                <Container maxWidth={false}>
                    <Toolbar disableGutters sx={{justifyContent: "space-between"}}>
                        <Box sx={{display: "flex", alignItems: "center", gap: 1}}>
                            <IconButton
                                edge="start"
                                color="inherit"
                                aria-label="menu"
                                sx={{display: {xs: "flex", md: "none"}, mr: 1}}
                                onClick={toggleDrawer}
                            >
                                <MenuIcon/>
                            </IconButton>

                            <Typography
                                variant="h6"
                                sx={{fontWeight: "bold", cursor: "pointer", userSelect: "none"}}
                                onClick={() => navigate("/")}
                            >
                                KeepDishesGoing
                            </Typography>
                        </Box>

                        <Box sx={{flexGrow: 1}}/>

                        <Box sx={{display: {xs: "none", md: "flex"}, gap: 3}}>
                            {linksToRender.map(link => {
                                const isActive =
                                    location.pathname === link.path ||
                                    (link.path === "/restaurant" &&
                                        location.pathname.startsWith("/restaurant/"));
                                const hasIcon = "icon" in link && link.icon;

                                return (
                                    <Button
                                        key={link.path}
                                        color="inherit"
                                        onClick={() => navigate(link.path)}
                                        sx={{
                                            textTransform: "none",
                                            fontWeight: isActive ? "bold" : "normal",
                                            borderBottom: isActive
                                                ? "2px solid #fff"
                                                : "2px solid transparent",
                                            transition: "border-bottom 0.2s",
                                            display: "flex",
                                            alignItems: "center",
                                            gap: 0.5,
                                            "&:hover": {borderBottom: "2px solid #fff"},
                                        }}
                                    >
                                        {hasIcon && (
                                            <Badge
                                                badgeContent={
                                                    link.label === "Basket"
                                                        ? basketLoading
                                                            ? "..."
                                                            : basketCount
                                                        : 0
                                                }
                                                color="secondary"
                                            >
                                                {link.icon}
                                            </Badge>
                                        )}
                                        {link.label}
                                    </Button>
                                );
                            })}

                            <Button
                                color="inherit"
                                onClick={isUserLoggedIn ? logout : login}
                                sx={{textTransform: "none", fontWeight: "normal"}}
                            >
                                {isUserLoggedIn ? (
                                    <>
                                        <ExitToAppIcon sx={{mr: 0.5}}/>
                                        Logout
                                    </>
                                ) : (
                                    <>
                                        <LoginIcon sx={{mr: 0.5}}/>
                                        Login
                                    </>
                                )}
                            </Button>
                        </Box>
                    </Toolbar>
                </Container>
            </AppBar>

            <Drawer
                anchor="left"
                open={mobileOpen}
                onClose={toggleDrawer}
                sx={{display: {xs: "block", md: "none"}}}
            >
                <Box sx={{width: 250, mt: 2}}>
                    <List>
                        {linksToRender.map(link => {
                            const hasIcon = "icon" in link && link.icon;
                            return (
                                <ListItem key={link.path} disablePadding>
                                    <ListItemButton
                                        selected={location.pathname === link.path}
                                        onClick={() => handleNavigate(link.path)}
                                        sx={{
                                            mb: 1,
                                            borderRadius: 1,
                                            "&.Mui-selected": {
                                                backgroundColor: "rgba(14, 165, 233, 0.15)",
                                            },
                                        }}
                                    >
                                        {hasIcon && (
                                            <Badge
                                                badgeContent={
                                                    link.label === "Basket"
                                                        ? basketLoading
                                                            ? "..."
                                                            : basketCount
                                                        : 0
                                                }
                                                color="secondary"
                                                sx={{mr: 1}}
                                            >
                                                {link.icon}
                                            </Badge>
                                        )}
                                        <ListItemText primary={link.label}/>
                                    </ListItemButton>
                                </ListItem>
                            );
                        })}

                        <ListItem disablePadding>
                            <ListItemButton onClick={isUserLoggedIn ? logout : login}>
                                {isUserLoggedIn ? (
                                    <>
                                        <ExitToAppIcon sx={{mr: 1}}/>
                                        <ListItemText primary="Logout"/>
                                    </>
                                ) : (
                                    <>
                                        <LoginIcon sx={{mr: 1}}/>
                                        <ListItemText primary="Login"/>
                                    </>
                                )}
                            </ListItemButton>
                        </ListItem>
                    </List>
                </Box>
            </Drawer>
        </>
    );
}
