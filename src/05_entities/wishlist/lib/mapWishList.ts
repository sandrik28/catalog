import { ProductPreviewCardDto } from '@/05_entities/product/model/types';
import type { WishlistDto } from '../api/types';
import { mapProduct, ProductCardType } from '@/05_entities/product';

export function mapWishlist(dto: WishlistDto): ProductCardType[] {
    return dto.map(product => mapProduct(product));
}
