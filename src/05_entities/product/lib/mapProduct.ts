import type { ProductCardType, ProductPreviewCardDto } from "../model/types";

export function mapProduct(dto: ProductPreviewCardDto): ProductCardType {
    return {
        id: dto.id as number,        
        title: dto.title,
        description: dto.description,   
        category: dto.category,
        status: dto.status, 
    };
}