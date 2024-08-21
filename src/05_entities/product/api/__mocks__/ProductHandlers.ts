import { HttpResponse, http } from 'msw'
import productsMock from '@/06_shared/lib/server/__mocks__/products.json'

export const productsHandlers = [
  http.get('http://localhost:3000/products', async ({ request }) => {
    const url = new URL(request.url)
    const productIds = url.searchParams.getAll('id').map(Number) 

    const products = productsMock.filter(product => productIds.includes(product.id))

    return HttpResponse.json(products, { status: 200 })
  }),
]
