import {useContext} from 'react';
import SecurityContext from '../context/SecurityContext';

function useSecurityContext() {
    const context = useContext(SecurityContext);
    if (context === undefined) {
        throw new Error('useSecurityContext must be used within a SecurityContextProvider');
    }
    return context;
}

export default useSecurityContext;