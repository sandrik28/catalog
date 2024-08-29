import { ChooseCategoryWidget } from '@/03_widgets/Category/ChooseCategoryWidget'
import { AddNewProduct } from '@/04_features/newProduct/addNewProduct'
import { InputSearch } from '@/04_features/search'
import { useState } from 'react'
import { useForm } from 'react-hook-form';
import css from './Page.module.css'


export function MainPage() {
    const { register } = useForm();
    const [searchResults, setSearchResults] = useState(null);

    const handleApiResponse = (data: any) => {
        setSearchResults(data);
    };

    return (
        <main>
            <div className={css.container}>
                <h1>Цифровые продукты</h1>
                <AddNewProduct />
            </div>
            <InputSearch
                register={register('searchInput')}
                onApiResponse={handleApiResponse}
            />
            <AddNewProduct />


            <ChooseCategoryWidget isMainMenu={true} />
        </main>
    )
}
