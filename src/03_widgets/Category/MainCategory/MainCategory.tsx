import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { ProductId, ProductMainPageCategory, ProductPreviewCardDto } from '@/05_entities/product/model/types';
import { RootState } from '@/01_app/AppStore';
import { selectProductIdsInWishlist } from '@/05_entities/wishlist';
import { AddToWishlistIcon } from '@/04_features/wishlist/addToWishlist/ui/AddToWishlistIcon/AddToWishlistIcon';
import { ProductFilterButtons } from '@/04_features/product/ui/ProductFilterButtons';
import { fetchSearchResults, InputSearch } from '@/04_features/search';
import { ProductCardList } from '@/03_widgets/ProductCardList/ui/ProductCardList';
import { filterFavoriteProducts } from './module/filterFavoriteProducts';


export const MainCategoryWidget: React.FC = () => {
    const [searchResults, setSearchResults] = useState<ProductPreviewCardDto[]>([]);
    const [currentCategory, setCurrentCategory] = useState<ProductMainPageCategory>(ProductMainPageCategory.All);
    const favoriteProductIds = useSelector(selectProductIdsInWishlist);
    const userId = useSelector((state: RootState) => state.session.userId);

    const handleApiResponse = (data: ProductPreviewCardDto[]) => {
        setSearchResults(data);
    };


    const categoriesToShow = [ProductMainPageCategory.All, ProductMainPageCategory.Favorites]
    const categoryFetchers: Record<ProductMainPageCategory, () => Promise<ProductPreviewCardDto[]>> = {
        [ProductMainPageCategory.All]: () => fetchSearchResults(),
        [ProductMainPageCategory.Favorites]: () => filterFavoriteProducts(searchResults, favoriteProductIds),
    };


    const handleCategoryChange = (category: ProductMainPageCategory) => {
        setCurrentCategory(category);
    };

    useEffect(() => {
        const loadProducts = async () => {
            const fetchProducts = categoryFetchers[currentCategory];
            const loadedProducts = await fetchProducts();
            setSearchResults(loadedProducts);
        };

        loadProducts();
    }, [currentCategory, favoriteProductIds, userId]);
    
    return (
        <>
            <InputSearch
                onApiResponse={handleApiResponse}
            />

            <ProductFilterButtons
                currentCategory={currentCategory}
                categories={categoriesToShow}
                onCategoryChange={handleCategoryChange}
            />

            <ProductCardList
                products={searchResults}
                productCardActionsSlot={(productId: ProductId) => <AddToWishlistIcon productId={productId} />}
            />
        </>
    );
};
