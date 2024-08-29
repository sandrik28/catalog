import { ChooseCategoryWidget } from '@/03_widgets/Category/ChooseCategoryWidget'
import { AddNewProduct } from '@/04_features/newProduct/addNewProduct'
import { InputSearch } from '@/04_features/search'
import { useState } from 'react'
import { useForm } from 'react-hook-form';
import css from './Page.module.css'
import { ProductPreviewCardDto } from '@/05_entities/product/model/types';



export function MainPage() {
    const { register } = useForm();
    const [searchResults, setSearchResults] = useState<ProductPreviewCardDto[]>([]);

    const handleApiResponse = (data: ProductPreviewCardDto[]) => {
        setSearchResults(data);
    };

    return (
        <main>
            <div className={css.container}>
                <h1>Цифровые продукты</h1>
                <AddNewProduct />
            </div>
            
            <InputSearch
                onApiResponse={handleApiResponse}
            />

            <ChooseCategoryWidget isMainMenu={true} />
        </main>
    )
}
