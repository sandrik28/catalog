export type ProductId = number;
export type OwnerId = Brand<number, 'OwnerId'>;

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



export type ProductCardType = {
    id: number;
    title: string;
    description: string;
    category: string;
    status: Status;
}
