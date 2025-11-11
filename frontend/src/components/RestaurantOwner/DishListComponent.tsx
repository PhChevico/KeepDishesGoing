import {useEffect, useState} from 'react';
import {
    Alert,
    Box,
    Button,
    ButtonGroup,
    Card,
    CardContent,
    CardMedia,
    Chip,
    CircularProgress,
    Typography,
} from '@mui/material';

import type {DishDto, DishStatus} from '../../model/RestaurantOwner/Dish';
import {fetchMenuDishes} from '../../services/restaurantService';

type ViewMode = 'ALL' | 'PUBLISHED';

const getStatusAction = (currentStatus: DishStatus): {
    text: string;
    action: 'PUBLISH' | 'UNPUBLISH' | 'OUT_OF_STOCK' | 'IN_STOCK';
    color: 'primary' | 'secondary' | 'error' | 'success'
} => {
    switch (currentStatus) {
        case 'DRAFT':
        case 'UNPUBLISHED':
            return {text: 'Publish to Menu', action: 'PUBLISH', color: 'primary'};

        case 'PUBLISHED':
            return {text: 'Unpublish', action: 'UNPUBLISH', color: 'secondary'};

        case 'OUT_OF_STOCK':
            return {text: 'Mark In Stock', action: 'IN_STOCK', color: 'success'};

        default:
            return {text: 'Publish to Menu', action: 'PUBLISH', color: 'primary'};
    }
};

interface DishListComponentProps {
    masterDishes: DishDto[];
    restaurantId: string;
    onEdit: (dish: DishDto) => void;
    onStatusChange: (dishId: string, action: 'PUBLISH' | 'UNPUBLISH' | 'OUT_OF_STOCK' | 'IN_STOCK') => Promise<void>;
    hookError: string | null;
    isGlobalLoading: boolean;
    menuId: string | null;
}

export function DishListComponent({
                                      masterDishes,
                                      restaurantId,
                                      onEdit,
                                      onStatusChange,
                                      hookError,
                                      isGlobalLoading,
                                      menuId
                                  }: DishListComponentProps) {
    const [viewMode, setViewMode] = useState<ViewMode>('ALL');
    const [publishedDishes, setPublishedDishes] = useState<DishDto[]>([]);
    const [isMenuLoading, setIsMenuLoading] = useState(false);
    const [isActionLoading, setIsActionLoading] = useState<string | null>(null);

    const getStatusColor = (status: DishStatus): "success" | "default" | "error" | "warning" | "primary" | "secondary" => {
        switch (status) {
            case 'PUBLISHED':
            case 'DRAFT':
            case 'UNPUBLISHED':
                return 'default';
            case 'OUT_OF_STOCK':
                return 'error';
            default:
                return 'default';
        }
    };

    const isPublishingEnabled = !!menuId;

    const loadPublishedDishes = async () => {
        // Only fetch if explicitly in PUBLISHED view AND the list is empty (or explicitly cleared)
        if (viewMode !== 'PUBLISHED' || publishedDishes.length > 0) return;

        setIsMenuLoading(true);
        try {
            const dishes = await fetchMenuDishes(restaurantId);
            setPublishedDishes(dishes);
        } catch (error) {
            console.error("Failed to load published menu dishes:", error);
            setPublishedDishes([]);
        } finally {
            setIsMenuLoading(false);
        }
    };

    const handleStatusAction = async (dishId: string, action: 'PUBLISH' | 'UNPUBLISH' | 'OUT_OF_STOCK' | 'IN_STOCK') => {
        if (isGlobalLoading) return;

        const isActionForPublish = action === 'PUBLISH';
        if (isActionForPublish && !isPublishingEnabled) {
            console.warn("Attempted to publish dish, but menu ID is missing from props. Action blocked.");
            return;
        }

        setIsActionLoading(dishId);
        try {
            await onStatusChange(dishId, action);

            if (viewMode === 'PUBLISHED') {
                setPublishedDishes([]);
            }
        } catch (error) {
            console.error(`Failed to ${action} dish with ID ${dishId}:`, error);
        } finally {
            setIsActionLoading(null);
        }
    };

    useEffect(() => {
        // This effect runs whenever viewMode or publishedDishes.length changes
        if (restaurantId && viewMode === 'PUBLISHED' && publishedDishes.length === 0) {
            loadPublishedDishes();
        }
    }, [viewMode, restaurantId, publishedDishes.length]);

    const dishesToDisplay = viewMode === 'ALL' ? masterDishes : publishedDishes;

    return (
        <Box sx={{bgcolor: 'background.paper', p: 3, borderRadius: 2, boxShadow: 3}}>
            <Typography variant="h5" fontWeight="bold" gutterBottom>
                Menu and Dish Management
            </Typography>

            <Box sx={{mb: 3, borderBottom: 1, borderColor: 'divider', pb: 2}}>
                <ButtonGroup variant="contained" sx={{boxShadow: 1}}>
                    <Button
                        onClick={() => setViewMode('ALL')}
                        variant={viewMode === 'ALL' ? 'contained' : 'outlined'}
                        sx={{textTransform: 'none', px: 3}}
                    >
                        All Dishes ({masterDishes.length})
                    </Button>
                    <Button
                        onClick={() => {
                            setPublishedDishes([]); // Clear list to force useEffect to fetch
                            setViewMode('PUBLISHED');
                        }}
                        variant={viewMode === 'PUBLISHED' ? 'contained' : 'outlined'}
                        disabled={isMenuLoading}
                        sx={{textTransform: 'none', px: 3}}
                    >
                        Published Menu ({publishedDishes.length})
                        {isMenuLoading && " (Loading...)"}
                    </Button>
                </ButtonGroup>
            </Box>

            {hookError && (
                <Alert severity="error" sx={{mb: 3}}>
                    **Action Failed:** {hookError}
                </Alert>
            )}

            {isMenuLoading && viewMode === 'PUBLISHED' ? (
                <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', py: 8}}>
                    <CircularProgress size={60}/>
                    <Typography variant="h6" sx={{ml: 2}}>
                        Loading Published Menu...
                    </Typography>
                </Box>
            ) : dishesToDisplay.length === 0 ? (
                <Box sx={{textAlign: 'center', py: 8, borderTop: 1, borderColor: 'divider'}}>
                    <Typography variant="h6" color="text.secondary">
                        {viewMode === 'ALL'
                            ? "No dishes have been created yet."
                            : "No dishes are currently published to the menu."}
                    </Typography>
                </Box>
            ) : (
                <Box sx={{display: 'flex', flexDirection: 'column', gap: 2, mt: 2}}>
                    {dishesToDisplay.map((dish, index) => {
                        const dishId = dish.id || (dish as any).dishId;

                        let statusAction = getStatusAction(dish.dishStatus);

                        // RESTRICTION LOGIC: Modify action if published and on the 'ALL' tab (removes 'Unpublish' from 'ALL')
                        if (dish.dishStatus === 'PUBLISHED' && viewMode === 'ALL') {
                            statusAction = {text: 'Mark Out of Stock', action: 'OUT_OF_STOCK', color: 'error'};
                        }

                        const isActionForPublish = statusAction.action === 'PUBLISH';

                        const isActionInProgress = isActionLoading === dishId || isGlobalLoading;

                        const isPublishingDisabled = isActionForPublish && !isPublishingEnabled;
                        const isDisabled = isActionInProgress || isPublishingDisabled;

                        return (
                            <Card
                                key={dishId || `dish-${index}`}
                                sx={{
                                    display: 'flex',
                                    boxShadow: 2,
                                    '&:hover': {boxShadow: 4},
                                    transition: 'box-shadow 0.3s',
                                }}
                            >
                                <CardMedia
                                    component="img"
                                    sx={{
                                        width: 120,
                                        height: 120,
                                        objectFit: 'cover',
                                        flexShrink: 0,
                                    }}
                                    image={(dish as any).picture || 'https://via.placeholder.com/120x120?text=Dish'}
                                    alt={dish.name}
                                />

                                <Box sx={{display: 'flex', flexGrow: 1, overflow: 'hidden'}}>
                                    <CardContent sx={{flex: 1, py: 2}}>
                                        <Box sx={{display: 'flex', alignItems: 'center', gap: 1, mb: 1}}>
                                            <Typography variant="h6" fontWeight="bold" noWrap>
                                                {dish.name}
                                            </Typography>
                                            <Typography variant="body2" color="text.secondary">
                                                ({(dish as any).typeOfDish || 'N/A'})
                                            </Typography>
                                        </Box>

                                        <Typography
                                            variant="body2"
                                            color="text.secondary"
                                            sx={{
                                                mb: 1.5,
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                display: '-webkit-box',
                                                WebkitLineClamp: 2,
                                                WebkitBoxOrient: 'vertical',
                                            }}
                                        >
                                            {dish.description || 'No detailed description available.'}
                                        </Typography>

                                        <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                                            {((dish as any).foodTagList || []).slice(0, 4).map((tag: string, i: number) => (
                                                <Chip
                                                    key={i}
                                                    label={tag.toLowerCase().replace(/_/g, ' ')}
                                                    size="small"
                                                    color="primary"
                                                    variant="outlined"
                                                    sx={{textTransform: 'capitalize'}}
                                                />
                                            ))}
                                        </Box>
                                    </CardContent>

                                    <Box
                                        sx={{
                                            display: 'flex',
                                            flexDirection: 'column',
                                            justifyContent: 'center',
                                            alignItems: 'flex-end',
                                            px: 3,
                                            minWidth: 160,
                                            gap: 1,
                                        }}
                                    >
                                        <Typography variant="h5" fontWeight="bold" color="primary">
                                            €{dish.price.toFixed(2)}
                                        </Typography>
                                        <Chip
                                            label={dish.dishStatus.replace(/_/g, ' ')}
                                            color={getStatusColor(dish.dishStatus)}
                                            size="small"
                                            sx={{
                                                fontWeight: 'bold',
                                                textTransform: 'uppercase',
                                                fontSize: '0.7rem',
                                            }}
                                        />

                                        <Box
                                            sx={{
                                                display: 'flex',
                                                flexDirection: 'row',
                                                gap: 1,
                                                mt: 0.5,
                                                width: '100%',
                                                justifyContent: 'flex-end',
                                            }}
                                        >
                                            <Button
                                                variant="outlined"
                                                size="small"
                                                color={statusAction.color}
                                                onClick={() => handleStatusAction(dishId, statusAction.action)}
                                                disabled={isDisabled}
                                                sx={{textTransform: 'none', minWidth: 120}}
                                            >
                                                {isActionInProgress && isActionLoading === dishId ? (
                                                    <CircularProgress size={16} color="inherit"/>
                                                ) : isPublishingDisabled ? (
                                                    'Menu Missing'
                                                ) : (
                                                    statusAction.text
                                                )}
                                            </Button>

                                            <Button
                                                variant="contained"
                                                size="small"
                                                onClick={() => onEdit(dish)}
                                                disabled={isActionInProgress}
                                                sx={{textTransform: 'none', minWidth: 110}}
                                            >
                                                Edit Details
                                            </Button>
                                        </Box>
                                    </Box>
                                </Box>
                            </Card>
                        );
                    })}
                </Box>
            )}
        </Box>
    );
}

export default DishListComponent;