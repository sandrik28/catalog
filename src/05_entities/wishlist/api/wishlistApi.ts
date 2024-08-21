import mockProducts from '@/06_shared/lib/server/__mocks__/products.json';
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type { ProductCardType } from '@/05_entities/product';
import type { WishlistDto } from './types';
import type { ProductDto } from '@/05_entities/product/@x/wishlist';
import { mapWishlist } from '../lib/mapWishList';

let mockWishlist: WishlistDto = mockProducts.filter((product: ProductDto) =>
    [14, 3, 7].includes(product.id)
);

export const wishlistApi = createApi({
    reducerPath: 'wishlistApi',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:3000' }),
    endpoints: (build) => ({
        wishlistProducts: build.query<ProductCardType[], void>({
            query: () => `/wishlist/products`,
            transformResponse: () => mapWishlist(mockWishlist),
        }),
        addToWishlist: build.mutation<void, number[]>({
            query: (productsInWishlistIds) => ({
                url: `/wishlist/products`,
                method: 'PATCH',
                body: productsInWishlistIds,
            }),
            async onQueryStarted(productsInWishlistIds, { queryFulfilled }) {
                try {
                    mockWishlist = mockProducts.filter((product: ProductDto) =>
                        productsInWishlistIds.includes(product.id)
                    );
                    await queryFulfilled;
                } catch (error) {
                    console.error('Error updating wishlist:', error);
                }
            },
        }),
    }),
});

export const { useWishlistProductsQuery, useAddToWishlistMutation } = wishlistApi;
