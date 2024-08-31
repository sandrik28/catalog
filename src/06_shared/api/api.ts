import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export type ProductApiResponse = {
    id: number;
    ownerId: number;
    nameOfOwner: string;
    title: string;
    status: 'ON_MODERATION' | 'APPROVED' | 'ARCHIVED' | 'MODERATION_DENIED';
    emailOFSupport: string;
    linkToWebSite: string;
    description: string;
    category: string;
    timeOfLastApproval: string | null;
};

export type NotificationResponse = {
    id: number;
    userId: number;
    productId: number;
    message: string;
    timestamp: string;
};

export type UserApiResponse = {
    id: number;
    name: string;
    email: string;
    idOfFollowedProductsList: number[];
    role: 'ROLE_ADMIN' | 'ROLE_USER';
};

export type AddProductBodyRequest = {
    title: string;
    emailOFSupport: string;
    linkToWebSite: string;
    description: string;
    category: string;
};

export type EditProductBodyRequest = {
    id: number;
    title: string;
    emailOFSupport: string;
    linkToWebSite: string;
    description: string;
    category: string;
};

const getBasicAuthToken = (username: string, password: string): string => {
    return `Basic ${btoa(`${username}:${password}`)}`;
};

const username = 'yourUsername';
const password = 'yourPassword';
const authToken = getBasicAuthToken(username, password);

export const api = createApi({
    reducerPath: 'api',
    baseQuery: fetchBaseQuery({
        baseUrl: 'http://localhost:8080',
        prepareHeaders: (headers) => {
            headers.set('Authorization', authToken);
            return headers;
        },
    }),
    endpoints: (builder) => ({
        getAllProducts: builder.query<ProductApiResponse[], void>({
            query: () => '/products/all_approved',
        }),
        getProductById: builder.query<ProductApiResponse, number>({
            query: (id) => `/products/${id}`,
        }),
        addProduct: builder.mutation<ProductApiResponse, AddProductBodyRequest>({
            query: (product) => ({
                url: '/products/add',
                method: 'POST',
                body: product,
            }),
        }),
        editProduct: builder.mutation<ProductApiResponse, EditProductBodyRequest>({
            query: (product) => ({
                url: '/products/edit',
                method: 'PUT',
                body: product,
            }),
        }),
        deleteUserById: builder.mutation<void, number>({
            query: (id) => ({
                url: `/users/delete/${id}`,
                method: 'DELETE',
            }),
        }),
        getUserById: builder.query<UserApiResponse, number>({
            query: (id) => `/users/${id}`,
        }),
        addUser: builder.mutation<UserApiResponse, Partial<UserApiResponse>>({
            query: (user) => ({
                url: '/users/add',
                method: 'POST',
                body: user,
            }),
        }),
        getNotifications: builder.query<NotificationResponse[], void>({
            query: () => '/notifications', 
        }),
    }),
});

export const {
    useGetAllProductsQuery,
    useGetProductByIdQuery,
    useAddProductMutation,
    useEditProductMutation,
    useDeleteUserByIdMutation,
    useGetUserByIdQuery,
    useAddUserMutation,
    useGetNotificationsQuery,  
} = api;
