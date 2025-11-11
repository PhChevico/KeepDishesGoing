import {Box, Typography} from "@mui/material";

interface Props {
    basketId: string;
    status: string;
}

export function OrderHeader({basketId, status}: Props) {
    return (
        <Box sx={{mb: 3}}>
            <Typography variant="h4" fontWeight="bold">
                Order Status
            </Typography>
            <Typography variant="subtitle1" color="text.secondary">
                Basket ID: {basketId} | Status: {status}
            </Typography>
        </Box>
    );
}
