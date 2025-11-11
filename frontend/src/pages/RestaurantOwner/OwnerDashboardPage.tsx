import {Box, CircularProgress, Container, Typography} from "@mui/material";
import {useOwnerRestaurant} from '../../hooks/useOwnerRestaurant'; // Assuming hook path
import {RestaurantDisplay} from '../../components/RestaurantOwner/RestaurantDisplay';
import {RestaurantCreationForm} from '../../components/RestaurantOwner/RestaurantCreationForm';
import {DishCreationForm} from "../../components/RestaurantOwner/DishCreationForm";
import DishListComponent from "../../components/RestaurantOwner/DishListComponent";
import {DishUpdateFormModal} from "../../components/RestaurantOwner/DishUpdateFormModal";
import {AcceptChangesButton} from "../../components/RestaurantOwner/AcceptChangesButton"; // 👈 IMPORTED the new component
import type {DishDto} from "../../model/RestaurantOwner/Dish";
import {useState} from "react";

export function OwnerDashboardPage() {
    const {
        restaurant,
        dishes,
        isLoading,
        error,
        isSubmitting,
        handleCreateRestaurant,
        handleCreateDish,
        handleUpdateDish,
        handleChangeDishStatus,
        handleAcceptAllChanges, // 👈 DESTRUCTURED the new handler
        menuId,
    } = useOwnerRestaurant();

    const [editingDish, setEditingDish] = useState<DishDto | null>(null);

    const handleStartEdit = (dish: DishDto) => {
        const dishId = (dish as any).id || (dish as any).dishId;

        if (!dishId) {
            console.error("ERROR: Attempted to open edit modal for dish missing ID. Cannot proceed.", dish);
            return;
        }

        const compliantDish: DishDto = {
            ...dish,
            id: dishId, // Set the 'id' field to the ID we found
        };

        setEditingDish(compliantDish);
    };

    const handleCloseEdit = () => {
        setEditingDish(null);
    };

    const handleUpdateSuccess = (updatedDish: DishDto) => {
        if (handleUpdateDish) {
            handleUpdateDish(updatedDish);
        } else {
            console.warn("handleUpdateDish function not available in hook.");
        }
        handleCloseEdit();
    };


    if (isLoading && !restaurant) {
        return (
            <Container sx={{mt: 8, textAlign: 'center'}}>
                <CircularProgress sx={{mb: 2}}/>
                <Typography variant="h6">Checking your restaurant status...</Typography>
            </Container>
        );
    }

    if (error && !restaurant && !isSubmitting) {
        return (
            <Container sx={{mt: 8}}>
                <Typography color="error" variant="h6">
                    🚨 Failed to load dashboard: {error}
                </Typography>
            </Container>
        );
    }

    return (
        <Container maxWidth="md" sx={{py: 4}}>
            <Typography variant="h3" fontWeight="bold" gutterBottom>
                Restaurant Owner Dashboard
            </Typography>

            {restaurant ? (
                <Box>
                    <RestaurantDisplay restaurant={restaurant}/>

                    <Typography variant="h4" fontWeight="bold" sx={{mt: 6, mb: 2}}>
                        Manage Menu
                    </Typography>

                    <DishCreationForm
                        onCreate={handleCreateDish}
                        isSubmitting={isSubmitting} // Use the unified submission state
                        error={error} // Use the unified error state
                    />

                    <Box sx={{mt: 4}}>
                        <DishListComponent
                            masterDishes={dishes} // Pass the master list of all dishes from the hook
                            restaurantId={restaurant.id} // Pass the required restaurant ID
                            onEdit={handleStartEdit} // Pass the edit handler
                            onStatusChange={handleChangeDishStatus}
                            hookError={error}
                            isGlobalLoading={isSubmitting}
                            menuId={menuId}
                        />
                    </Box>

                    <AcceptChangesButton
                        onAccept={handleAcceptAllChanges}
                        isSubmitting={isSubmitting}
                    />

                    {editingDish && (
                        <DishUpdateFormModal
                            dish={editingDish}
                            open={!!editingDish}
                            onClose={handleCloseEdit}
                            onUpdateSuccess={handleUpdateSuccess}
                            restaurantId={restaurant.id}
                        />
                    )}

                </Box>
            ) : (
                <Box>
                    <Typography variant="h5" sx={{mt: 4}}>
                        It looks like you don't have a restaurant registered yet.
                    </Typography>
                    <RestaurantCreationForm
                        onCreate={handleCreateRestaurant}
                        isSubmitting={isSubmitting} // Use the unified submission state
                        error={error}
                    />
                </Box>
            )}
        </Container>
    );
}