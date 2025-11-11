import {Box, Button, CircularProgress} from '@mui/material';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';

interface AcceptChangesButtonProps {
    onAccept: () => Promise<boolean>;
    isSubmitting: boolean;
}

export function AcceptChangesButton({onAccept, isSubmitting}: AcceptChangesButtonProps) {

    const handleButtonClick = async () => {
        if (!isSubmitting) {
            await onAccept();
        }
    };

    return (
        <Box sx={{mt: 3, display: 'flex', justifyContent: 'center', p: 1, borderTop: 1, borderColor: 'divider'}}>
            <Button
                variant="contained"
                color="success"
                size="large"
                onClick={handleButtonClick}
                disabled={isSubmitting}
                startIcon={isSubmitting ? <CircularProgress size={20} color="inherit"/> : <CheckCircleOutlineIcon/>}
                sx={{
                    textTransform: 'none',
                    fontWeight: 'bold',
                    minWidth: 280,
                    fontSize: '1rem',
                }}
            >
                {isSubmitting ? 'Applying Changes...' : 'Accept All Pending Changes'}
            </Button>
        </Box>
    );
};