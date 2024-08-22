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
                <Link to={`/product/${product.id}`} className={css.link}>
        <div className={css.productCard}>
                <div className={css.productHeader}>
                    <h3 className="text_product_title">{product.title}</h3>
                    {actionSlot}
                </div>
                <div className={css.productDescriptionSlot}>
                    <p className="text_product_description">
                        {product.description}
                    </p>
                </div>
                <div className={css.productCategorySlot}>
                    <span className="product_card_category_span">{product.category}</span>
                </div>
                <div className={css.productInfoSlot}>
                    {info}
                </div>
            </div>
        </Link>
    );
};