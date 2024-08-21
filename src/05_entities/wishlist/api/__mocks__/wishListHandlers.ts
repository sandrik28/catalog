import { HttpResponse, delay, http } from 'msw';
import mockProducts from '@/06_shared/lib/server/__mocks__/products.json'; // Моки продуктов

// Заглушка для данных избранного
const mockWishlist = {
    userId: 1,
    productIds: [14, 3, 7], // Пример ID продуктов в избранном
};

export const wishlistHandlers = [
    // Обработчик GET-запроса для получения продуктов из избранного
    http.get(
        'http://localhost:3000/wishlist/products',
        async ({ request }) => {
            try {
                const { userId } = { userId: 1 };

                const products = mockProducts.filter(product =>
                    mockWishlist.productIds.includes(product.id)
                );

                await delay(500);
                return HttpResponse.json(products, { status: 200 });
            } catch {
                await delay(500);
                return HttpResponse.json('Forbidden', { status: 403 });
            }
        },
    ),


    http.patch<object, number[]>(
        'http://localhost:3000/wishlist/products',
        async ({ request }) => {
            try {

                const { userId } = { userId: 1 };

                const body = await request.json();

                mockWishlist.productIds = body;

                await delay(500);
                return HttpResponse.json({}, { status: 200 });
            } catch {
                await delay(500);
                return HttpResponse.json('Forbidden', { status: 403 });
            }
        },
    ),
];