import './App.css'
import {BrowserRouter, Route, Routes} from "react-router";
import {RestaurantDetail} from "./pages/RestaurantDetail.tsx";
import axios from "axios";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {Box, CssBaseline} from '@mui/material';
import LandingPage from './pages/LandingPage';
import {ThemeProvider} from '@mui/material/styles';
import appTheme from './theme/theme.ts';
import {RestaurantList} from "./pages/RestaurantList.tsx";
import {Navbar} from "./components/Navbar.tsx";
import {SecurityContextProvider} from "./context/SecurityContextProvider.tsx";
import {BasketDetail} from "./pages/BasketDetail.tsx";
import {CheckoutPage} from "./pages/CheckoutPage.tsx";
import {CheckoutSuccessPage} from "./pages/CheckoutSuccessPage.tsx";
import {OrderStatusPage} from "./pages/OrderStatusPage.tsx";
import {RouteGuard} from "./components/RouteGuard.tsx";
import {OwnerDashboardPage} from "./pages/RestaurantOwner/OwnerDashboardPage.tsx";
import {RestaurantOrdersPage} from "./pages/RestaurantOwner/RestaurantOrdersPage.tsx";

axios.defaults.baseURL = "http://127.0.0.1:8080";
const queryClient = new QueryClient();

function App() {
    return (
        <ThemeProvider theme={appTheme}>
            <CssBaseline/>
            <QueryClientProvider client={queryClient}>
                <SecurityContextProvider>
                    <BrowserRouter>
                        <Navbar/>
                        <Box sx={{
                            flex: 1,
                            overflowY: 'auto',
                            overflowX: 'hidden'
                        }}>
                            <Routes>
                                <Route path="/" element={<LandingPage/>}/>
                                <Route path="/restaurant/:id" element={<RestaurantDetail/>}/>
                                <Route path="/customer/restaurants" element={<RestaurantList/>}/>
                                <Route path="/basket" element={<BasketDetail/>}/>
                                <Route path="/checkout/:basketId" element={<CheckoutPage/>}/>
                                <Route path="/payment-success" element={<CheckoutSuccessPage/>}/>
                                <Route path="/order-status" element={<OrderStatusPage/>}/>

                                <Route path="/restaurant" element={<RouteGuard>
                                    <OwnerDashboardPage/>
                                </RouteGuard>}/>
                                <Route path="/restaurant/orders" element={<RouteGuard>
                                    <RestaurantOrdersPage/>
                                </RouteGuard>}/>
                            </Routes>
                        </Box>
                    </BrowserRouter>
                </SecurityContextProvider>
            </QueryClientProvider>
        </ThemeProvider>
    )
}

export default App