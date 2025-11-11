import {
    Box,
    Button,
    Chip,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from '@mui/material';
import type {OrderDto} from "../../model/RestaurantOwner/OrderDetails";
import {useAcceptOrder, useMarkOrderReadyForPickup, useRefuseOrder} from "../../hooks/useOwnerRestaurant"; // Updated hook path

type Props = {
    orders: OrderDto[];
};

export function OrderList({orders}: Props) {
    const acceptMutation = useAcceptOrder();
    const refuseMutation = useRefuseOrder();
    const readyMutation = useMarkOrderReadyForPickup();

    const REFUSAL_MESSAGE = "Restaurant is currently too busy to process this order.";

    const getStatusColor = (status: string) => {
        switch (status) {
            case 'RECEIVED':
                return 'primary';
            case 'ACCEPTED':
                return 'warning';
            case 'READY_FOR_PICK_UP':
                return 'success';
            case 'COMPLETED':
                return 'default';
            case 'REFUSED':
                return 'error';
            default:
                return 'default';
        }
    };

    const handleAccept = (orderId: string) => {
        acceptMutation.mutate(orderId);
    };

    const handleRefuse = (orderId: string) => {
        refuseMutation.mutate({orderId, message: REFUSAL_MESSAGE});
    };

    const handleReady = (orderId: string) => {
        readyMutation.mutate(orderId);
    };

    return (
        <TableContainer component={Paper} elevation={3}>
            <Table sx={{minWidth: 650}} aria-label="simple table">
                <TableHead>
                    <TableRow sx={{bgcolor: 'grey.100'}}>
                        <TableCell>Order ID</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Total</TableCell>
                        <TableCell>Address</TableCell>
                        <TableCell>Items</TableCell>
                        <TableCell align="right">Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {orders.map((order) => (
                        <TableRow key={order.orderId} hover>
                            <TableCell component="th" scope="row">
                                <Typography variant="caption" color="text.secondary">
                                    {order.orderId.substring(0, 8)}...
                                </Typography>
                            </TableCell>
                            <TableCell>
                                <Chip
                                    label={order.status}
                                    color={getStatusColor(order.status)}
                                    size="small"
                                />
                            </TableCell>
                            <TableCell>
                                €{order.totalPrice.toFixed(2)}
                            </TableCell>
                            <TableCell>
                                {order.deliveryAddress.street} {order.deliveryAddress.number}
                            </TableCell>
                            <TableCell>
                                {order.orderLines.length} Item(s)
                            </TableCell>

                            <TableCell align="right">
                                <Box sx={{display: 'flex', gap: 1, justifyContent: 'flex-end'}}>
                                    {order.status === 'RECEIVED' && (
                                        <>
                                            <Button
                                                variant="contained"
                                                color="success"
                                                size="small"
                                                onClick={() => handleAccept(order.orderId)}
                                                disabled={acceptMutation.isPending || refuseMutation.isPending}
                                            >
                                                Accept
                                            </Button>
                                            <Button
                                                variant="outlined"
                                                color="error"
                                                size="small"
                                                onClick={() => handleRefuse(order.orderId)}
                                                disabled={acceptMutation.isPending || refuseMutation.isPending}
                                            >
                                                Refuse
                                            </Button>
                                        </>
                                    )}

                                    {order.status === 'ACCEPTED' && (
                                        <Button
                                            variant="contained"
                                            color="warning"
                                            size="small"
                                            onClick={() => handleReady(order.orderId)}
                                            disabled={readyMutation.isPending}
                                        >
                                            Ready for Pickup
                                        </Button>
                                    )}
                                </Box>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}