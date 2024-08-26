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

interface ChooseCategoryWidgetProps {
    isMainMenu?: boolean;
}

export const ChooseCategoryWidget: React.FC<ChooseCategoryWidgetProps> = ({ isMainMenu = false }) => {
    const [products, setProducts] = useState<ProductPreviewCardDto[]>([]);
    const [currentCategory, setCurrentCategory] = useState<ProductCategory>(ProductCategory.All);
    const favoriteProductIds = useSelector(selectProductIdsInWishlist);
    const userId = useSelector((state: RootState) => state.session.userId);

    const categoryFetchers: Record<ProductCategory, () => Promise<ProductPreviewCardDto[]>> = {
        [ProductCategory.All]: fetchMainProducts,
        [ProductCategory.Favorites]: () => fetchFavoriteProducts(favoriteProductIds),
        [ProductCategory.ToDo]: () => userId ? fetchToDoProducts(userId) : Promise.resolve([]),
        [ProductCategory.Archive]: () => userId ? fetchArchiveProducts(userId) : Promise.resolve([]),
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

    const categoriesToShow = isMainMenu
        ? [ProductCategory.All, ProductCategory.Favorites] 
        : Object.values(ProductCategory);

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
