import React, {useEffect, useState} from 'react';
import {Box, Button, FormControl, InputLabel, MenuItem, Modal, Select, TextField, Typography} from "@mui/material";
import type {SelectChangeEvent} from "@mui/material/Select";
import type {DishDto, DishStatus, TypeOfDish, UpdateDishRequest} from '../../model/RestaurantOwner/Dish.ts';
import {updateDish} from '../../services/restaurantService.ts';


const ALL_DISH_TYPES: TypeOfDish[] = ['STARTER', 'MAIN', 'DESSERT'];

interface DishUpdateFormModalProps {
    dish: DishDto;
    open: boolean;
    onClose: () => void;
    onUpdateSuccess: (updatedDish: DishDto) => void;
    restaurantId: string;
}

const style = {
    position: 'absolute' as const,
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 500,
    bgcolor: 'background.paper',
    borderRadius: '12px',
    boxShadow: 24,
    p: 4,
};

export function DishUpdateFormModal({dish, open, onClose, onUpdateSuccess, restaurantId}: DishUpdateFormModalProps) {
    const [formData, setFormData] = useState<Omit<UpdateDishRequest, 'dishId' | 'restaurantId'>>({
        name: dish.name,
        typeOfDish: dish.typeOfDish,
        foodTags: dish.foodTagList as string[], // Cast to string[] is safe for generic array type
        description: dish.description,
        price: dish.price,
        picture: dish.picture,
        dishStatus: dish.dishStatus,
    });
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (dish) {
            setFormData({
                name: dish.name,
                typeOfDish: dish.typeOfDish,
                foodTags: dish.foodTagList as string[],
                description: dish.description,
                price: dish.price,
                picture: dish.picture,
                dishStatus: dish.dishStatus,
            });
            setError(null);
        }
    }, [dish]);


    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target;

        if (name === 'price') {
            const priceValue = parseFloat(value);
            setFormData(prev => ({
                ...prev,
                [name!]: isNaN(priceValue) ? prev.price : priceValue,
            }));
        } else if (name === 'foodTags') {
            setFormData(prev => ({
                ...prev,
                foodTags: value.split(',').map(tag => tag.trim()).filter(tag => tag.length > 0)
            }));
        } else {
            setFormData(prev => ({
                ...prev,
                [name!]: value,
            }));
        }
    };

    const handleSelectChange = (e: SelectChangeEvent<DishStatus | TypeOfDish>) => {
        const {name, value} = e.target;

        setFormData(prev => ({
            ...prev,
            [name!]: value as any,
        }));
    };


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);

        // CRITICAL FIX 1: Validate dish.id before proceeding
        if (!dish.id) {
            setError("Error: Dish ID is missing. Cannot save changes.");
            setIsLoading(false);
            // Log error to console for debugging
            console.error("Attempted to submit update with a missing dish ID.", dish);
            return;
        }

        try {
            const processedFoodTags = formData.foodTags.map(tag =>
                tag.trim().toUpperCase().replace(/ /g, '_')
            ).filter(tag => tag.length > 0);


            const updateCommand: UpdateDishRequest = {
                dishId: dish.id, // Now guaranteed to be non-null
                restaurantId: restaurantId,
                name: formData.name,
                typeOfDish: formData.typeOfDish as TypeOfDish,
                foodTags: processedFoodTags,
                description: formData.description,
                price: Number(formData.price), // Ensure price is always a number
                picture: formData.picture,
                dishStatus: formData.dishStatus as DishStatus,
            };

            const updatedDish = await updateDish(updateCommand);

            onUpdateSuccess(updatedDish);
            onClose();
        } catch (err) {
            console.error("Dish update failed:", err);
            const errorMessage = (err as Error).message.includes("40")
                ? "Server rejected the update. Check data format or permissions."
                : "Failed to update dish. Please check your network or try again.";

            setError(errorMessage);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Modal
            open={open}
            onClose={onClose}
            aria-labelledby="dish-update-modal-title"
        >
            <Box sx={style} component="form" onSubmit={handleSubmit}>
                <Typography id="dish-update-modal-title" variant="h5" component="h2" gutterBottom>
                    Edit Dish: {dish.name}
                </Typography>

                {error && <Typography color="error" sx={{mb: 2}}>{error}</Typography>}

                <TextField
                    fullWidth
                    label="Dish Name"
                    name="name"
                    value={formData.name}
                    onChange={handleInputChange}
                    margin="normal"
                    required
                />
                <TextField
                    fullWidth
                    label="Description"
                    name="description"
                    value={formData.description}
                    onChange={handleInputChange}
                    margin="normal"
                    multiline
                    rows={2}
                />
                <TextField
                    fullWidth
                    label="Price (€)"
                    name="price"
                    type="number"
                    value={formData.price}
                    onChange={handleInputChange}
                    margin="normal"
                    required
                    inputProps={{step: "0.01"}}
                />
                <TextField
                    fullWidth
                    label="Picture URL"
                    name="picture"
                    value={formData.picture}
                    onChange={handleInputChange}
                    margin="normal"
                />
                <TextField
                    fullWidth
                    label="Food Tags (comma-separated)"
                    name="foodTags"
                    // Display tags nicely for editing
                    value={Array.isArray(formData.foodTags) ? formData.foodTags.join(', ').toLowerCase().replace(/_/g, ' ') : ''}
                    onChange={handleInputChange}
                    margin="normal"
                    helperText="e.g., vegan, gluten free, spicy"
                />

                <FormControl fullWidth margin="normal">
                    <InputLabel id="dish-type-label">Type of Dish</InputLabel>
                    <Select
                        labelId="dish-type-label"
                        name="typeOfDish"
                        value={formData.typeOfDish}
                        label="Type of Dish"
                        onChange={handleSelectChange as any}
                        required
                    >
                        {/* Use the actual ALL_DISH_TYPES constant */}
                        {ALL_DISH_TYPES.map(type => (
                            <MenuItem key={type} value={type}>{type.replace(/_/g, ' ')}</MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <Box sx={{mt: 3, display: 'flex', justifyContent: 'flex-end', gap: 2}}>
                    <Button onClick={onClose} color="error" disabled={isLoading}>
                        Cancel
                    </Button>
                    <Button type="submit" variant="contained" color="primary" disabled={isLoading}>
                        {isLoading ? 'Updating...' : 'Save Changes'}
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};