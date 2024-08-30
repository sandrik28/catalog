import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

// Определяем типы для продуктов и пользователей
type Product = {
    id: number;
    ownerId: number;
    nameOfOwner: string;
    title: string;
    status: 'ON_MODERATION' | 'APPROVED' | 'ARCHIVED' | 'MODERATION_DENIED';
    emailOFSupport: string;
    linkToWebSite: string;
    description: string;
    category: string;
    timeOfLastApproval: string;
};

type User = {
    id: number;
    name: string;
    email: string;
    idOfFollowedProductsList: number[];
    role: 'ROLE_ADMIN' | 'ROLE_USER';
};

export const api = createApi({
    reducerPath: 'api',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080' }),
    endpoints: (builder) => ({
        // Получение всех продуктов
        getAllProducts: builder.query<Product[], void>({
            query: () => '/products/all_approved',
        }),
        // Получение продукта по ID
        getProductById: builder.query<Product, number>({
            query: (id) => `/products/${id}`,
        }),
        // Добавление нового продукта
        addProduct: builder.mutation<Product, Partial<Product>>({
            query: (product) => ({
                url: '/products/add',
                method: 'POST',
                body: product,
            }),
        }),
        // Редактирование продукта
        editProduct: builder.mutation<Product, Partial<Product>>({
            query: (product) => ({
                url: '/products/edit',
                method: 'PUT',
                body: product,
            }),
        }),
        // Удаление пользователя по ID
        deleteUserById: builder.mutation<void, number>({
            query: (id) => ({
                url: `/users/delete/${id}`,
                method: 'DELETE',
            }),
        }),
        // Получение пользователя по ID
        getUserById: builder.query<User, number>({
            query: (id) => `/users/${id}`,
        }),
        // Добавление нового пользователя
        addUser: builder.mutation<User, Partial<User>>({
            query: (user) => ({
                url: '/users/add',
                method: 'POST',
                body: user,
            }),
        }),
    }),
});

// Экспортируем хуки для использования в компонентах
export const {
    useGetAllProductsQuery,
    useGetProductByIdQuery,
    useAddProductMutation,
    useEditProductMutation,
    useDeleteUserByIdMutation,
    useGetUserByIdQuery,
    useAddUserMutation,
} = api;
