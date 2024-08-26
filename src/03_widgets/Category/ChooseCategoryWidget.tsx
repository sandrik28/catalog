import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { ProductId, ProductPreviewCardDto } from '@/05_entities/product/model/types';
import { RootState } from '@/01_app/AppStore';
import { fetchArchiveProducts, fetchFavoriteProducts, fetchMainProducts, fetchToDoProducts } from '@/04_features/product/category/api/productFilterRequests';
import { ProductCardList } from '../ProductCardList/ui/ProductCardList';
import { selectProductIdsInWishlist } from '@/05_entities/wishlist';
import { AddToWishlistIcon } from '@/04_features/wishlist/addToWishlist/ui/AddToWishlistIcon';
import { ProductCategory } from '@/05_entities/product/model/types';
import { ProductFilterButtons } from '@/04_features/product/ui/ProductFilterButtons';
import { useParams } from 'react-router-dom';

interface ChooseCategoryWidgetProps {
    isMainMenu?: boolean;
}

export const ChooseCategoryWidget: React.FC<ChooseCategoryWidgetProps> = ({ isMainMenu = false }) => {
    const { id: profileId } = useParams<{ id: string }>();
    const favoriteProductIds = useSelector(selectProductIdsInWishlist);
    const userId = useSelector((state: RootState) => state.session.userId);
    const parsedProfileId = profileId ? parseInt(profileId, 10) : null;

    let categoriesToShow: ProductCategory[];
    if (isMainMenu) {
        categoriesToShow = [ProductCategory.All, ProductCategory.Favorites];
    } else {
        categoriesToShow = userId === parsedProfileId
            ? [ProductCategory.UserProducts, ProductCategory.ToDo, ProductCategory.Archive]
            : [ProductCategory.UserProducts, ProductCategory.Archive];
    }

    const [currentCategory, setCurrentCategory] = useState<ProductCategory>(categoriesToShow[0]);

    const [products, setProducts] = useState<ProductPreviewCardDto[]>([]);

    const categoryFetchers: Record<ProductCategory, () => Promise<ProductPreviewCardDto[]>> = {
        [ProductCategory.All]: fetchMainProducts,
        [ProductCategory.Favorites]: () => fetchFavoriteProducts(favoriteProductIds),
        [ProductCategory.ToDo]: () => userId ? fetchToDoProducts(userId) : Promise.resolve([]),
        [ProductCategory.Archive]: () => parsedProfileId ? fetchArchiveProducts(parsedProfileId) : Promise.resolve([]),
        [ProductCategory.UserProducts]: () => parsedProfileId ? fetchMainProducts(parsedProfileId) : Promise.resolve([]),
    };
    

    useEffect(() => {
        const loadProducts = async () => {
            const fetchProducts = categoryFetchers[currentCategory];
            const loadedProducts = await fetchProducts();
            setProducts(loadedProducts);
        };

        loadProducts();
    }, [currentCategory, favoriteProductIds, userId]);

    const handleCategoryChange = (category: ProductCategory) => {
        setCurrentCategory(category);
    };

    return (
        <>
            <ProductFilterButtons
                currentCategory={currentCategory}
                categories={categoriesToShow}
                onCategoryChange={handleCategoryChange}
            />
            <ProductCardList 
                products={products} 
                productCardActionsSlot={(productId: ProductId) => <AddToWishlistIcon productId={productId} />} 
            />
        </>
    );
};
