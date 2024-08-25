import { productsMock } from '@/06_shared/lib/server';
import { ProductId, ProductPreviewCardDto } from '../../model/types';

export function mockProductDtoByIds(ids: number[]): ProductPreviewCardDto[] {
  return productsMock
    .filter(product => ids.includes(product.id as ProductId))
    .map(product => ({
      ...product,
      timeOfLastApproval: new Date(product.timeOfLastApproval),
    })) as ProductPreviewCardDto[];
}