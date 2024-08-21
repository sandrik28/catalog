import type { ProductDto } from "../api/types";
import type { ProductCardType } from "../model/types";

export function mapProduct(dto: ProductDto): ProductCardType {
    return {
        id: dto.id as number,        
        title: dto.title,
        description: dto.description,   
        category: dto.category,
    };
}