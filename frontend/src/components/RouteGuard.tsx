import {type PropsWithChildren, useContext, useEffect} from 'react'
import SecurityContext from '../context/SecurityContext.ts'

export function RouteGuard({children}: PropsWithChildren) {
    const {isInitialised, isAuthenticated, login} = useContext(SecurityContext)

    useEffect(() => {
        if (isInitialised && !isAuthenticated()) {
            login()
        }
    }, [isAuthenticated, login])

    if (!isAuthenticated()) {
        return <div>Authenticating</div>
    }

    return children
}
