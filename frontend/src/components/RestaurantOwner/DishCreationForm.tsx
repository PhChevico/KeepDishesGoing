// src/components/RestaurantOwner/DishCreationForm.tsx

import React, {useState} from 'react';
import {
    Box,
    Button,
    Card,
    CardContent,
    Chip,
    FormControl,
    InputLabel,
    MenuItem,
    OutlinedInput,
    Select,
    TextField,
    Typography,
} from "@mui/material";
import type {SelectChangeEvent} from '@mui/material/Select';

// Combined import statement to reflect all types and constants are in the single Dish.ts file
import type {CreateDishCommand, DishDto, FoodTag, TypeOfDish} from '../../model/RestaurantOwner/Dish';
import {ALL_DISH_TYPES, ALL_FOOD_TAGS} from '../../model/RestaurantOwner/Dish';


interface DishCreationFormProps {
    onCreate: (data: CreateDishCommand) => Promise<DishDto | null>;
    isSubmitting: boolean;
    error: string | null;
}

const initialFormData: CreateDishCommand = {
    name: '',
    description: '',
    price: 0,
    picture: '',
    foodTags: [],
    typeOfDish: ALL_DISH_TYPES[0],
    restaurantId: '',
};

const formatTagName = (tag: string) =>
    tag.replace(/_/g, ' ')
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join(' ');


export function DishCreationForm({onCreate, isSubmitting, error}: DishCreationFormProps) {
    const [formData, setFormData] = useState<CreateDishCommand>(initialFormData);

    const isFormValid = (
        formData.name.trim() !== '' &&
        formData.description.trim() !== '' &&
        formData.picture.trim() !== '' &&
        formData.price > 0 && // Price must be positive
        formData.typeOfDish.trim() !== '' && // Type of Dish must be selected
        Array.isArray(formData.foodTags)
    );


    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target;

        let processedValue: string | number;

        if (name === 'price') {
            // Handle price: Parse as float and ensure it's not NaN
            processedValue = parseFloat(value);
            if (isNaN(processedValue)) processedValue = 0;
        } else {
            processedValue = value;
        }

        setFormData(prev => ({
            ...prev,
            [name]: processedValue,
        }));
    };

    // Handler for the multi-select tags field (FoodTag[])
    const handleTagChange = (event: SelectChangeEvent<FoodTag[]>) => {
        const {value} = event.target;
        const tags = typeof value === 'string' ? value.split(',') : value;

        setFormData(prev => ({
            ...prev,
            foodTags: tags as FoodTag[],
        }));
    };

    // Handler for single-select fields (TypeOfDish)
    const handleSingleSelectChange = (event: SelectChangeEvent<TypeOfDish>) => {
        const {name, value} = event.target;

        if (name) {
            setFormData(prev => ({
                ...prev,
                [name]: value as TypeOfDish,
            }));
        }
    };


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        // Use the robust local validity check
        if (isFormValid && !isSubmitting) {
            onCreate(formData).then(dish => {
                if (dish) {
                    setFormData(initialFormData); // Reset form on successful creation
                }
            });
        }
    };

    return (
        <Card component="form" onSubmit={handleSubmit} sx={{mb: 4, mt: 4, boxShadow: 3}}>
            <CardContent>
                <Typography variant="h6" fontWeight="bold" gutterBottom>
                    Add a New Dish to the Menu 🍝
                </Typography>

                {/* FIX: Error is now displayed if present, regardless of isSubmitting state */}
                {error && <Typography color="error" sx={{mb: 2}}>{error}</Typography>}

                <Box sx={{display: 'grid', gap: 2, mt: 3, gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))'}}>

                    <TextField
                        label="Dish Name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                        fullWidth
                        error={formData.name.trim() === '' && formData.name.length > 0}
                        helperText={formData.name.trim() === '' && formData.name.length > 0 ? 'Dish Name is required' : ''}
                    />

                    <TextField
                        label="Price (€)"
                        name="price"
                        type="number"
                        value={formData.price || ''}
                        onChange={handleChange}
                        required
                        fullWidth
                        inputProps={{step: "0.01", min: "0"}}
                        // Only show error if price is <= 0 AND the price in state is NOT the initial '0'.
                        error={formData.price <= 0 && String(formData.price) !== '0'}
                        helperText={formData.price <= 0 && String(formData.price) !== '0' ? 'Price must be greater than 0' : ''}
                    />

                    <TextField
                        label="Picture URL"
                        name="picture"
                        value={formData.picture}
                        onChange={handleChange}
                        required
                        fullWidth
                        error={formData.picture.trim() === '' && formData.picture.length > 0}
                        helperText={formData.picture.trim() === '' && formData.picture.length > 0 ? 'Picture URL is required' : ''}
                    />

                    {/* Type of Dish Single Select Field */}
                    <FormControl fullWidth required>
                        <InputLabel id="dish-type-label">Type of Dish</InputLabel>
                        <Select
                            labelId="dish-type-label"
                            name="typeOfDish"
                            value={formData.typeOfDish}
                            onChange={handleSingleSelectChange as (event: SelectChangeEvent<unknown>) => void}
                            label="Type of Dish"
                        >
                            {ALL_DISH_TYPES.map((type) => (
                                <MenuItem key={type} value={type}>
                                    {formatTagName(type)}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>


                    {/* Food Tags Multi-Select Field */}
                    <FormControl fullWidth sx={{gridColumn: '1 / -1'}}>
                        <InputLabel id="food-tags-label">Food Tags (Allergens, Categories, etc.)</InputLabel>
                        <Select
                            labelId="food-tags-label"
                            multiple
                            name="foodTags"
                            value={formData.foodTags}
                            onChange={handleTagChange as (event: SelectChangeEvent<unknown>) => void}
                            input={<OutlinedInput id="select-multiple-chip"
                                                  label="Food Tags (Allergens, Categories, etc.)"/>}
                            renderValue={(selected) => (
                                <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                                    {(selected as FoodTag[]).map((value) => (
                                        <Chip key={value} label={formatTagName(value)} size="small"/>
                                    ))}
                                </Box>
                            )}
                        >
                            {ALL_FOOD_TAGS.map((tag) => (
                                <MenuItem key={tag} value={tag}>
                                    {formatTagName(tag)}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <TextField
                        label="Description"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        required
                        fullWidth
                        multiline
                        rows={3}
                        sx={{gridColumn: '1 / -1'}} // Full width for description
                        error={formData.description.trim() === '' && formData.description.length > 0}
                        helperText={formData.description.trim() === '' && formData.description.length > 0 ? 'Description is required' : ''}
                    />
                </Box>

                <Box sx={{mt: 4, textAlign: 'right', position: 'relative', zIndex: 10}}>
                    <Button
                        type="submit"
                        variant="contained"
                        color="secondary"
                        // Button is disabled if submitting OR if the form data is invalid
                        disabled={isSubmitting || !isFormValid}
                    >
                        {isSubmitting ? 'Adding Dish...' : 'Add Dish'}
                    </Button>
                </Box>
            </CardContent>
        </Card>
    );
}