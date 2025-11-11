import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Container,
    Divider,
    TextField,
    Typography,
} from "@mui/material";
import {useNavigate, useParams} from "react-router-dom";
import {useBasket, useCheckoutBasket, useCreatePayment} from "../hooks/useBasket";
import {useState} from "react";

interface CheckoutForm {
    firstName: string;
    lastName: string;
    street: string;
    number: string;
    postalCode: string;
    city: string;
    country: string;
    emailAddress: string;
}

export function CheckoutPage() {
    const {basketId} = useParams<{ basketId: string }>();
    const navigate = useNavigate();
    const {basket, isLoading} = useBasket(basketId);
    const {checkout, isPending: checkingOut} = useCheckoutBasket();
    const {createPayment, isPending: creatingPayment} = useCreatePayment();

    const [form, setForm] = useState<CheckoutForm>({
        firstName: "",
        lastName: "",
        street: "",
        number: "",
        postalCode: "",
        city: "",
        country: "Belgium",
        emailAddress: "",
    });

    const [errors, setErrors] = useState<Partial<CheckoutForm>>({});
    const [submitError, setSubmitError] = useState<string | null>(null);

    const handleChange = (field: keyof CheckoutForm) => (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm({...form, [field]: e.target.value});
        // Clear error when user starts typing
        if (errors[field]) {
            setErrors({...errors, [field]: undefined});
        }
    };

    const validate = (): boolean => {
        const newErrors: Partial<CheckoutForm> = {};

        if (!form.firstName.trim()) newErrors.firstName = "First name is required";
        if (!form.lastName.trim()) newErrors.lastName = "Last name is required";
        if (!form.street.trim()) newErrors.street = "Street is required";
        if (!form.number.trim()) newErrors.number = "Number is required";
        if (!form.postalCode.trim()) newErrors.postalCode = "Postal code is required";
        if (!form.city.trim()) newErrors.city = "City is required";
        if (!form.country.trim()) newErrors.country = "Country is required";

        // Email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!form.emailAddress.trim()) {
            newErrors.emailAddress = "Email is required";
        } else if (!emailRegex.test(form.emailAddress)) {
            newErrors.emailAddress = "Invalid email format";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!validate() || !basketId) return;

        setSubmitError(null);

        try {
            // First, checkout the basket with delivery info
            await checkout({basketId, request: form});

            // Then, create payment and get payment URL
            const paymentUrl = await createPayment(basketId);

            // Redirect to payment provider
            window.location.href = paymentUrl;
        } catch (error: any) {
            console.error("Checkout failed:", error);
            setSubmitError(error.response?.data?.message || "Checkout failed. Please try again.");
        }
    };

    const submitting = checkingOut || creatingPayment;

    if (isLoading) {
        return (
            <Box sx={{display: "flex", justifyContent: "center", alignItems: "center", minHeight: "60vh"}}>
                <CircularProgress size={60}/>
            </Box>
        );
    }

    if (!basket || !basket.items || basket.items.length === 0) {
        return (
            <Container maxWidth="md" sx={{mt: 4}}>
                <Alert severity="warning">Your basket is empty. Please add items before checking out.</Alert>
            </Container>
        );
    }

    const total = basket.totalPrice ?? 0;

    return (
        <Container maxWidth="lg" sx={{py: 4}}>
            <Typography variant="h4" fontWeight="bold" gutterBottom>
                Checkout
            </Typography>
            <Typography variant="body1" color="text.secondary" sx={{mb: 4}}>
                Complete your order details
            </Typography>

            <Box sx={{display: "flex", gap: 3, flexDirection: {xs: "column", md: "row"}}}>
                {/* Checkout Form */}
                <Box sx={{flex: 2}}>
                    <Card sx={{boxShadow: 2}}>
                        <CardContent>
                            <Typography variant="h6" fontWeight="bold" gutterBottom>
                                Delivery Information
                            </Typography>

                            {submitError && (
                                <Alert severity="error" sx={{mb: 3}}>
                                    {submitError}
                                </Alert>
                            )}

                            <Box component="form" onSubmit={handleSubmit} sx={{mt: 2}}>
                                <Box sx={{display: "flex", gap: 2, mb: 2}}>
                                    <TextField
                                        fullWidth
                                        label="First Name"
                                        value={form.firstName}
                                        onChange={handleChange("firstName")}
                                        error={!!errors.firstName}
                                        helperText={errors.firstName}
                                        required
                                    />
                                    <TextField
                                        fullWidth
                                        label="Last Name"
                                        value={form.lastName}
                                        onChange={handleChange("lastName")}
                                        error={!!errors.lastName}
                                        helperText={errors.lastName}
                                        required
                                    />
                                </Box>

                                <TextField
                                    fullWidth
                                    label="Email"
                                    type="email"
                                    value={form.emailAddress}
                                    onChange={handleChange("emailAddress")}
                                    error={!!errors.emailAddress}
                                    helperText={errors.emailAddress}
                                    sx={{mb: 2}}
                                    required
                                />

                                <Box sx={{display: "flex", gap: 2, mb: 2}}>
                                    <TextField
                                        fullWidth
                                        label="Street"
                                        value={form.street}
                                        onChange={handleChange("street")}
                                        error={!!errors.street}
                                        helperText={errors.street}
                                        sx={{flex: 3}}
                                        required
                                    />
                                    <TextField
                                        label="Number"
                                        value={form.number}
                                        onChange={handleChange("number")}
                                        error={!!errors.number}
                                        helperText={errors.number}
                                        sx={{flex: 1}}
                                        required
                                    />
                                </Box>

                                <Box sx={{display: "flex", gap: 2, mb: 2}}>
                                    <TextField
                                        label="Postal Code"
                                        value={form.postalCode}
                                        onChange={handleChange("postalCode")}
                                        error={!!errors.postalCode}
                                        helperText={errors.postalCode}
                                        sx={{flex: 1}}
                                        required
                                    />
                                    <TextField
                                        fullWidth
                                        label="City"
                                        value={form.city}
                                        onChange={handleChange("city")}
                                        error={!!errors.city}
                                        helperText={errors.city}
                                        sx={{flex: 2}}
                                        required
                                    />
                                </Box>

                                <TextField
                                    fullWidth
                                    label="Country"
                                    value={form.country}
                                    onChange={handleChange("country")}
                                    error={!!errors.country}
                                    helperText={errors.country}
                                    sx={{mb: 3}}
                                    required
                                />

                                <Box sx={{display: "flex", gap: 2}}>
                                    <Button
                                        variant="outlined"
                                        size="large"
                                        onClick={() => navigate("/basket")}
                                        fullWidth
                                        disabled={submitting}
                                        sx={{textTransform: "none"}}
                                    >
                                        Back to Basket
                                    </Button>
                                    <Button
                                        type="submit"
                                        variant="contained"
                                        size="large"
                                        fullWidth
                                        disabled={submitting}
                                        sx={{textTransform: "none"}}
                                    >
                                        {submitting ? "Processing..." : "Proceed to Payment"}
                                    </Button>
                                </Box>
                            </Box>
                        </CardContent>
                    </Card>
                </Box>

                {/* Order Summary */}
                <Box sx={{flex: 1}}>
                    <Card sx={{boxShadow: 2, position: "sticky", top: 80}}>
                        <CardContent>
                            <Typography variant="h6" fontWeight="bold" gutterBottom>
                                Order Summary
                            </Typography>

                            <Divider sx={{my: 1}}/>

                            {basket.items.map((item) => (
                                <Box key={item.dishId} sx={{mb: 2}}>
                                    <Box sx={{display: "flex", justifyContent: "space-between"}}>
                                        <Typography variant="body2">
                                            {item.name} x {item.quantity}
                                        </Typography>
                                        <Typography variant="body2">
                                            €{((item.priceAtAddition || 0) * item.quantity).toFixed(2)}
                                        </Typography>
                                    </Box>
                                </Box>
                            ))}

                            <Divider sx={{my: 2}}/>

                            <Box sx={{display: "flex", justifyContent: "space-between"}}>
                                <Typography variant="h6" fontWeight="bold">
                                    Total
                                </Typography>
                                <Typography variant="h6" fontWeight="bold" color="primary">
                                    €{total.toFixed(2)}
                                </Typography>
                            </Box>
                        </CardContent>
                    </Card>
                </Box>
            </Box>
        </Container>
    );
}