import React from 'react';
import { ProductCategory, ProductMainPageCategory } from '@/05_entities/product/model/types';
import { ChooseCategoryButton } from '@/06_shared/ui/ChooseCategoryButton/ChooseCategoryButton';


interface ProductFilterButtonsProps {
    currentCategory: ProductCategory | ProductMainPageCategory;
    categories: ProductCategory[];
    onCategoryChange: (category: ProductCategory) => void;
}

export const ProductFilterButtons: React.FC<ProductFilterButtonsProps> = ({
    
    currentCategory,
    categories,
    onCategoryChange,
    
}) => {

    return (
        <div className="category-buttons">
            {categories.map(category => (
                <ChooseCategoryButton
                    key={category}
                    isActive={currentCategory === category}
                    onClick={() => onCategoryChange(category)}
                    label={
                        category === ProductCategory.All ? ProductCategory.All :
                        category === ProductCategory.Favorites ? ProductCategory.Favorites :
                        category === ProductCategory.ToDo ? ProductCategory.ToDo :
                        category === ProductCategory.UserProducts ? ProductCategory.UserProducts : 
                        category === ProductCategory.Archive ? ProductCategory.Archive  : ''
                    }
                />
            ))}
        </div>
    );
};
