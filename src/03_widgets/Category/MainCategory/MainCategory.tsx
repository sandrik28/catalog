import React, { useState, useEffect, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { ProductId, ProductMainPageCategory, ProductPreviewCardDto } from '@/05_entities/product/model/types';
import { RootState } from '@/01_app/AppStore';
import { selectProductIdsInWishlist } from '@/05_entities/wishlist';
import { AddToWishlistIcon } from '@/04_features/wishlist/addToWishlist/ui/AddToWishlistIcon/AddToWishlistIcon';
import { ProductFilterButtons } from '@/04_features/product/ui/ProductFilterButtons';
import { fetchSearchResults, InputSearch } from '@/04_features/search';
import { ProductCardList } from '@/03_widgets/ProductCardList/ui/ProductCardList';
import { filterFavoriteProducts } from './module/filterFavoriteProducts';
import { useGetAllProductsQuery } from '@/06_shared/api/api';
import { Loader } from '@/06_shared/ui/Loader/Loader';

export const MainCategoryWidget: React.FC = () => {
    const [searchResults, setSearchResults] = useState<ProductPreviewCardDto[]>([]);
    const [currentCategory, setCurrentCategory] = useState<ProductMainPageCategory>(ProductMainPageCategory.All);
    const favoriteProductIds = useSelector(selectProductIdsInWishlist);
    const userId = useSelector((state: RootState) => state.session.userId);

    const { data: allProducts = [], isLoading } = useGetAllProductsQuery();

    const handleApiResponse = useCallback((data: ProductPreviewCardDto[]) => {
        setSearchResults(data);
    }, []);

    useEffect(() => {
        const updateProducts = () => {
            if (currentCategory === ProductMainPageCategory.All) {
                setSearchResults(allProducts);
            } 
            // else if (currentCategory === ProductMainPageCategory.Favorites) {
            //     const filteredFavorites = filterFavoriteProducts(allProducts, favoriteProductIds);
            //     setSearchResults(filteredFavorites);
            // }
        };

        updateProducts();
    }, [currentCategory, allProducts, favoriteProductIds]);

    // Handle category change
    const handleCategoryChange = (category: ProductMainPageCategory) => {
        setCurrentCategory(category);
    };

    return (
        <>
            <InputSearch onApiResponse={handleApiResponse} />

            <ProductFilterButtons
                currentCategory={currentCategory}
                categories={[ProductMainPageCategory.All, ProductMainPageCategory.Favorites]}
                onCategoryChange={handleCategoryChange}
            />

            {isLoading ? (
                <Loader/>
            ) : (
                <ProductCardList
                    products={searchResults}
                    productCardActionsSlot={(productId: ProductId) => <AddToWishlistIcon productId={productId} />}
                />
            )}
        </>
    );
};
