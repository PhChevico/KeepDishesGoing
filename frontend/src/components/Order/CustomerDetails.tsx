import {Box, Card, CardContent, Typography} from "@mui/material";
import type {Address, ClientInfo} from "../../model/OrderDetails";

interface CustomerDetailsProps {
    clientInfo: ClientInfo;
    address: Address;
}

export function CustomerDetails({clientInfo, address}: CustomerDetailsProps) {
    return (
        <Card sx={{mb: 4, mt: 4, boxShadow: 3}}>
            <CardContent>
                <Typography variant="h5" fontWeight="bold" gutterBottom>
                    Customer & Delivery Details
                </Typography>

                <Box sx={{mt: 2, display: 'grid', gridTemplateColumns: {xs: '1fr', sm: '1fr 1fr'}, gap: 3}}>
                    {/* Customer Info */}
                    <Box>
                        <Typography variant="subtitle1" fontWeight="medium">Customer:</Typography>
                        <Typography variant="body1">{clientInfo.firstName} {clientInfo.lastName}</Typography>
                        <Typography variant="body1">{clientInfo.emailAddress}</Typography>
                    </Box>

                    {/* OrderDetails Info */}
                    <Box>
                        <Typography variant="subtitle1" fontWeight="medium">Delivery Address:</Typography>
                        <Typography variant="body1">{address.street} {address.number}</Typography>
                        <Typography variant="body1">{address.postalCode} {address.city}</Typography>
                        <Typography variant="body1">{address.country}</Typography>
                    </Box>
                </Box>
            </CardContent>
        </Card>
    );
}

