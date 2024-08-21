export type ProductId = Brand<number, 'ProductId'>;
export type OwnerId = Brand<number, 'OwnerId'>;

export type ProductDto = {
    ownerId: OwnerId;
    id: ProductId;
    title: string;
    type: string;
    linkToWebSite: string;
    description: string;
    category: string;
    status: string;
};


export type ProductCardType = {
    id: number;
    title: string;
    description: string;
    category: string;
};