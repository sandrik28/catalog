import { ChooseCategoryWidget } from '@/03_widgets/Category/ChooseCategoryWidget'
import { AddNewProduct } from '@/04_features/newProduct/addNewProduct'
import { InputSearch } from '@/04_features/search'
import React, { useState } from 'react'
import { useForm } from 'react-hook-form';

export function MainPage() {
    const { register } = useForm();
    const [searchResults, setSearchResults] = useState(null);

    const handleApiResponse = (data: any) => {
        setSearchResults(data);
    };

    return (
        <main>
            <InputSearch
                register={register('searchInput')}
                onApiResponse={handleApiResponse}
            />
            <AddNewProduct />

            
            <ChooseCategoryWidget isMainMenu={true} />
        </main>
    )
}
