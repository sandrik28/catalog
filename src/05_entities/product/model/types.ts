export type ProductId = number;
export type ownerId = Brand<number, 'ownerId'>;

export interface ProductDto {
    id: number;
    ownerId: number;
    nameOfOwner: string;
    title: string;
    status: Status;
    emailOfSupport: string;
    linkToWebSite: string;
    description: string;
    category: string;
    timeOfLastApproval: Date; 
}

export enum Status {
    ON_MODERATION = 'ON_MODERATION',
    APPROVED = 'APPROVED',
    ARCHIVED = 'ARCHIVED',
    MODERATION_DENIED = 'MODERATION_DENIED',
}


export enum ProductMainPageCategory {
    All = 'Все',
    Favorites = 'Отслеживаемые',
}
export enum ProductCategory {
    All = 'Все',
    Favorites = 'Отслеживаемые',
    ToDo = 'Ждут действий',
    Archive = 'Архив',
    UserProducts = 'Активные',
}

export interface ProductPreviewCardDto {
    id: number;
    ownerId: number;
    title: string;
    nameOfOwner: string;
    description: string;
    category: string;
    status: Status;
    timeOfLastApproval: Date;
}

export interface IProductDetails {
    id: number;
    ownerId: number;
    nameOfOwner: string;
    title: string;
    emailOfSupport: string;
    linkToWebSite: string;
    description: string;
    status: Status;
}
