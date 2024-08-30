import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { ProductId, ProductPreviewCardDto } from '@/05_entities/product/model/types';
import { RootState } from '@/01_app/AppStore';
import { fetchArchiveProducts, fetchFavoriteProducts, fetchMainProducts, fetchToDoProducts } from '@/04_features/product/category/api/productFilterRequests';
import { selectProductIdsInWishlist } from '@/05_entities/wishlist';
import { AddToWishlistIcon } from '@/04_features/wishlist/addToWishlist/ui/AddToWishlistIcon';
import { ProductCategory } from '@/05_entities/product/model/types';
import { useParams } from 'react-router-dom';
import { ProductFilterButtonsProfile } from '@/04_features/product/ui/ProductFilterButtonsProfile';
import { ProductCardList } from '@/03_widgets/ProductCardList/ui/ProductCardList';

interface ProfileCategoryProps {
    isMainMenu?: boolean;
}

export const ProfileCategory: React.FC<ProfileCategoryProps> = ({ isMainMenu = false }) => {
    const [searchResults, setSearchResults] = useState<ProductPreviewCardDto[]>([]);

    const handleApiResponse = (data: ProductPreviewCardDto[]) => {
        setSearchResults(data);
    };

    const { id: profileId } = useParams<{ id: string }>();
    const favoriteProductIds = useSelector(selectProductIdsInWishlist);
    const userId = useSelector((state: RootState) => state.session.userId);
    const parsedProfileId = profileId ? parseInt(profileId, 10) : null;

    let categoriesToShow: ProductCategory[];
    
    categoriesToShow = userId === parsedProfileId
        ? [ProductCategory.UserProducts, ProductCategory.ToDo, ProductCategory.Archive]
        : [ProductCategory.UserProducts, ProductCategory.Archive];

    const [currentCategory, setCurrentCategory] = useState<ProductCategory>(categoriesToShow[0]);


    const categoryFetchers: Record<ProductCategory, () => Promise<ProductPreviewCardDto[]>> = {
        [ProductCategory.Favorites]: () => fetchFavoriteProducts(favoriteProductIds),
        [ProductCategory.ToDo]: () => userId ? fetchToDoProducts(userId) : Promise.resolve([]),
        [ProductCategory.Archive]: () => parsedProfileId ? fetchArchiveProducts(parsedProfileId) : Promise.resolve([]),
        [ProductCategory.UserProducts]: () => parsedProfileId ? fetchMainProducts(parsedProfileId) : Promise.resolve([]),
    };


    useEffect(() => {
        const loadProducts = async () => {
            const fetchProducts = categoryFetchers[currentCategory];
            const loadedProducts = await fetchProducts();
            setSearchResults(loadedProducts);
        };

        loadProducts();
    }, [currentCategory, favoriteProductIds, userId]);

    const handleCategoryChange = (category: ProductCategory) => {
        setCurrentCategory(category);
    };
    console.log()
    return (
        <>

            <ProductFilterButtonsProfile
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
