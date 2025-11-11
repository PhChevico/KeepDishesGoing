import {useNavigate} from 'react-router-dom';
import {Box, Button, Container, Paper, Stack, Typography} from '@mui/material';
import {Person, Restaurant} from '@mui/icons-material';

export function LandingPage() {
    const navigate = useNavigate();

    const handleRoleSelect = (role: 'customer' | 'restaurant_owner') => {
        console.log(`Selected role: ${role}`);
        navigate(role === 'customer' ? '/customer/restaurants' : '/restaurant');
    };

    return (
        <Container
            maxWidth="sm"
            disableGutters
            sx={{
                height: '100vh',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                overflow: 'hidden',
            }}
        >
            <Box
                sx={{
                    width: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    gap: 4,
                    px: 2, // slight horizontal padding for small screens
                    textAlign: 'center',
                }}
            >
                {/* Header */}
                <Box>
                    <Typography
                        variant="h3"
                        component="h1"
                        gutterBottom
                        fontWeight="bold"
                    >
                        KeepDishesGoing
                    </Typography>
                    <Typography variant="h6" color="text.secondary">
                        Choose how you want to use our platform
                    </Typography>
                </Box>

                {/* Role Selection Cards */}
                <Stack
                    direction={{xs: 'column', sm: 'row'}}
                    spacing={3}
                    width="100%"
                >
                    {/* Customer Card */}
                    <Paper
                        elevation={3}
                        sx={{
                            p: 4,
                            flex: 1,
                            textAlign: 'center',
                            cursor: 'pointer',
                            transition: 'all 0.3s ease',
                            '&:hover': {
                                boxShadow: 8,
                                transform: 'translateY(-4px)',
                            },
                        }}
                        onClick={() => handleRoleSelect('customer')}
                    >
                        <Person sx={{fontSize: 64, color: 'primary.main', mb: 2}}/>
                        <Typography variant="h5" gutterBottom fontWeight="bold">
                            Customer
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Browse restaurants, order food, and get delicious meals delivered to your door
                        </Typography>
                        <Button
                            variant="contained"
                            fullWidth
                            sx={{mt: 3}}
                            size="large"
                        >
                            Order Food
                        </Button>
                    </Paper>

                    {/* Restaurant Owner Card */}
                    <Paper
                        elevation={3}
                        sx={{
                            p: 4,
                            flex: 1,
                            textAlign: 'center',
                            cursor: 'pointer',
                            transition: 'all 0.3s ease',
                            '&:hover': {
                                boxShadow: 8,
                                transform: 'translateY(-4px)',
                            },
                        }}
                        onClick={() => handleRoleSelect('restaurant_owner')}
                    >
                        <Restaurant
                            sx={{fontSize: 64, color: 'secondary.main', mb: 2}}
                        />
                        <Typography variant="h5" gutterBottom fontWeight="bold">
                            Restaurant Owner
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Manage your restaurant, receive orders, and grow your business
                        </Typography>
                        <Button
                            variant="contained"
                            color="secondary"
                            fullWidth
                            sx={{mt: 3}}
                            size="large"
                        >
                            Manage Restaurant
                        </Button>
                    </Paper>
                </Stack>
            </Box>
        </Container>
    );
};

export default LandingPage;
