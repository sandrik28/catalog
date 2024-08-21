import { productsMock } from '@/06_shared/lib/server';
import type { ProductDto } from '../types';

export function mockProductDtoByIds(ids: number[]): ProductDto[] {
  return productsMock.filter(product =>
    ids.includes(product.id),
  ) as ProductDto[];
}