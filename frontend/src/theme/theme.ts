import {alpha, createTheme} from "@mui/material";

// ----- Color palettes -----
const brand = {
    50: '#f0f9ff',
    100: '#e0f2fe',
    200: '#bae6fd',
    300: '#7dd3fc',
    400: '#38bdf8',
    500: '#0ea5e9',
    600: '#0284c7',
    700: '#0369a1',
    800: '#075985',
    900: '#0c4a6e',
};

const gray = {
    50: '#f9fafb',
    100: '#f3f4f6',
    200: '#e5e7eb',
    300: '#d1d5db',
    400: '#9ca3af',
    500: '#6b7280',
    600: '#4b5563',
    700: '#374151',
    800: '#1f2937',
    900: '#111827',
};

// ----- Create MUI theme -----
const appTheme = createTheme({
    palette: {
        mode: 'light', // you can change to 'dark' if preferred
        primary: {
            light: brand[200],
            main: brand[500],
            dark: brand[700],
            contrastText: brand[50],
        },
        secondary: {
            light: '#ff7961',
            main: '#dc004e',
            dark: '#ba000d',
            contrastText: '#ffffff',
        },
        divider: alpha(gray[300], 0.4),
        background: {
            default: 'hsl(0, 0%, 99%)',
            paper: gray[50],
        },
        text: {
            primary: brand[900],
            secondary: gray[600],
        },
    },
    typography: {
        fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',
    },
});

export default appTheme;
