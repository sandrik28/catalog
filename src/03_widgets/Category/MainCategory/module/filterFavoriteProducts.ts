import { ProductPreviewCardDto, Status } from "@/05_entities/product/model/types";

export const filterFavoriteProducts = async (productList: ProductPreviewCardDto[], wishlistListIds: number[]): Promise<ProductPreviewCardDto[]> => {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve(productList.filter(product => product.status === Status.APPROVED || 
                    product.status === Status.ARCHIVED).filter(product => wishlistListIds.includes(product.id)));
        }, 500);
    });
};
