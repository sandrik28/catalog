import { ProductPreviewCardDto, Status } from '@/05_entities/product/model/types';
import { productsMockCards } from '@/06_shared/lib/server';

const mockProducts: ProductPreviewCardDto[] = productsMockCards.map(product => {
    return {
        ...product,
        status: product.status as Status,
        timeOfLastApproval: new Date(product.timeOfLastApproval),
    };
});


export const fetchMainProducts = async (): Promise<ProductPreviewCardDto[]> => {
    const activeMocks = mockProducts.filter(product => product.status === Status.APPROVED)
    return new Promise(resolve => {
        setTimeout(() => {
            resolve(activeMocks);
        }, 500);
    });
};

export const fetchFavoriteProducts = async (ids: number[]): Promise<ProductPreviewCardDto[]> => {
    const activeMocks = mockProducts.filter(product => product.status === Status.APPROVED)
    return new Promise(resolve => {
        setTimeout(() => {
            resolve(activeMocks.filter(product => ids.includes(product.id)));
        }, 500);
    });
};

export const fetchToDoProducts = async (userId: number): Promise<ProductPreviewCardDto[]> => {
    const toDoMocks = mockProducts.filter(product => product.status === Status.MODERATION_DENIED && product.ownerId === userId)
    return new Promise(resolve => {
        setTimeout(() => {
            resolve(toDoMocks);
        }, 500);
    });
};

export const fetchArchiveProducts = async (userId: number): Promise<ProductPreviewCardDto[]> => {
    const archiveMocks = mockProducts.filter(product => product.status === Status.ARCHIVED && product.ownerId === userId)
    return new Promise(resolve => {
        setTimeout(() => {
            resolve(archiveMocks);
        }, 500);
    });
};
