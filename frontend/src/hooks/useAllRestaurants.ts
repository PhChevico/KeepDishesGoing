import {useQuery} from "@tanstack/react-query";
import {getAllRestaurants} from "../services/clientService.ts";


export function useAllRestaurants() {
    return useQuery({
        queryKey: ['restaurants'],
        queryFn: getAllRestaurants,
        refetchInterval: 30_000,
    });
}
