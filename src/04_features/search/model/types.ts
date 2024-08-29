import { ProductPreviewCardDto } from '@/05_entities/product/model/types';
import { InputHTMLAttributes } from 'react';
import { UseFormRegisterReturn } from 'react-hook-form';

export type InputSearchProps = {
    maxLength?: number;
    value?: string;
    type?: InputHTMLAttributes<HTMLInputElement>['type'];
    onApiResponse: (data: ProductPreviewCardDto[]) => void;

};
