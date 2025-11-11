// src/components/RestaurantCreationForm.tsx

import React, {useState} from 'react';
import type {SelectChangeEvent} from "@mui/material";
import {
    Box,
    Button,
    Card,
    CardContent,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography,
} from "@mui/material";

import type {RestaurantDto} from "../../model/RestaurantOwner/Restaurant";
import type {CuisineType, PriceRange} from "../../model/RestaurantOwner/Enums";
import {ALL_CUISINE_TYPES, ALL_PRICE_RANGES} from "../../model/RestaurantOwner/Enums";


// --- Component Types ---

// FIX: Change Omit to use RestaurantDto directly, as 'picture' is now required by the backend.
type CreateRestaurantCommand = RestaurantDto;

interface RestaurantCreationFormProps {
    onCreate: (data: CreateRestaurantCommand) => Promise<RestaurantDto | null>;
    isSubmitting: boolean;
    error: string | null;
}

const initialFormData: CreateRestaurantCommand = {
    name: '',
    email: '',
    street: '',
    number: 0,
    postalCode: 0,
    city: '',
    country: '',
    cuisineType: ALL_CUISINE_TYPES[0] as CuisineType,
    defaultTimePreparation: 30,
    priceRange: ALL_PRICE_RANGES[0] as PriceRange,
    picture: '', // FIX: Add the required 'picture' field with an initial empty string value
    id: ''
};

// --- Helper Functions ---

const formatLabel = (value: string) =>
    value.replace(/_/g, ' ')
        .toLowerCase()
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');


// --- Main Component ---

export function RestaurantCreationForm({onCreate, isSubmitting, error}: RestaurantCreationFormProps) {
    const [formData, setFormData] = useState<CreateRestaurantCommand>(initialFormData);

    /**
     * Handles changes for TextField inputs (string and number types).
     */
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value, type} = e.target;

        // Convert input value to number if the type is 'number'
        const processedValue = type === 'number' ? Number(value) : value;

        setFormData(prev => ({
            ...prev,
            [name as string]: processedValue,
        }));
    };

    /**
     * Handles changes for Select components (CuisineType and PriceRange).
     */
    const handleSelectChange = (e: SelectChangeEvent<CuisineType | PriceRange>) => {
        const {name, value} = e.target;

        if (!name) return;

        setFormData(prev => ({
            ...prev,
            [name]: value as any,
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // FIX: Add validation for the new 'picture' field
        if (formData.name && formData.email && formData.picture && !isSubmitting) {
            onCreate(formData);
        }
    };

    return (
        <Card component="form" onSubmit={handleSubmit} sx={{mb: 4, mt: 4, boxShadow: 6}}>
            <CardContent>
                <Typography variant="h5" fontWeight="bold" gutterBottom>
                    Create Your Restaurant Profile 📝
                </Typography>
                {error && <Typography color="error" sx={{mb: 2}}>{error}</Typography>}

                <Box sx={{display: 'grid', gap: 2, mt: 3, gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))'}}>

                    {/* General TextFields */}
                    <TextField label="Restaurant Name" name="name" value={formData.name} onChange={handleChange}
                               required fullWidth/>
                    <TextField label="Contact Email" name="email" type="email" value={formData.email}
                               onChange={handleChange} required fullWidth/>

                    {/* NEW FIELD: Picture URL (Added to address the backend requirement) */}
                    <TextField
                        label="Picture URL"
                        name="picture"
                        value={formData.picture}
                        onChange={handleChange}
                        required
                        fullWidth
                        sx={{gridColumn: 'span 2'}} // Spanning two columns for visual emphasis
                    />

                    {/* Address Fields */}
                    <TextField label="Street" name="street" value={formData.street} onChange={handleChange} required
                               fullWidth/>
                    <TextField label="Number" name="number" type="number" value={formData.number || ''}
                               onChange={handleChange} required fullWidth
                               inputProps={{inputMode: 'numeric', pattern: '[0-9]*'}}/>
                    <TextField label="Postal Code" name="postalCode" type="number" value={formData.postalCode || ''}
                               onChange={handleChange} required fullWidth
                               inputProps={{inputMode: 'numeric', pattern: '[0-9]*'}}/>
                    <TextField label="City" name="city" value={formData.city} onChange={handleChange} required
                               fullWidth/>
                    <TextField label="Country" name="country" value={formData.country} onChange={handleChange} required
                               fullWidth/>

                    {/* Cuisine Type Select */}
                    <FormControl fullWidth required>
                        <InputLabel id="cuisine-label">Cuisine Type</InputLabel>
                        <Select
                            labelId="cuisine-label"
                            name="cuisineType"
                            value={formData.cuisineType}
                            onChange={handleSelectChange as (e: SelectChangeEvent<string>) => void}
                            label="Cuisine Type"
                        >
                            {ALL_CUISINE_TYPES.map(type => (
                                <MenuItem key={type} value={type}>
                                    {formatLabel(type)}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <TextField label="Default Preparation Time (min)" name="defaultTimePreparation" type="number"
                               value={formData.defaultTimePreparation || ''} onChange={handleChange} required fullWidth
                               inputProps={{inputMode: 'numeric', pattern: '[0-9]*'}}/>

                    {/* Price Range Select */}
                    <FormControl fullWidth required>
                        <InputLabel id="price-label">Price Range</InputLabel>
                        <Select
                            labelId="price-label"
                            name="priceRange"
                            value={formData.priceRange}
                            onChange={handleSelectChange as (e: SelectChangeEvent<string>) => void}
                            label="Price Range"
                        >
                            {ALL_PRICE_RANGES.map(range => (
                                <MenuItem key={range} value={range}>
                                    {range === 'CHEAP' ? '€' : range === 'REGULAR' ? '€€' : '€€€'}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Box>

                <Box sx={{mt: 4, textAlign: 'right'}}>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? 'Submitting...' : 'Create Restaurant'}
                    </Button>
                </Box>
            </CardContent>
        </Card>
    );
}