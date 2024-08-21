import { Link } from 'react-router-dom';
import type { ProductCardType } from '../../model/types';
import css from './ProductCard.module.css';
import { ReactNode } from 'react';

type Props = {
    product: ProductCardType;
    actionSlot?: ReactNode;
    info?: ReactNode;
};

export const ProductCard = (props: Props) => {
    const { product, actionSlot, info } = props;

    return (
        <Link to={`/product/${product.id}`} className={css.root}>
            <div className={css.root}>
                <div className={css.productHeader}>
                    <h3 className="text_product_title">{product.title}</h3>
                    {actionSlot}
                </div>
                <div className={css.productDescriptionSlot}>
                    <h3 className="text_product_description">
                        {product.description}
                    </h3>
                </div>
                <div className={css.productCategorySlot}>
                    <span>{product.category}</span>
                </div>
                <div className={css.productInfoSlot}>
                    {info}
                </div>
            </div>
        </Link>
    );
};