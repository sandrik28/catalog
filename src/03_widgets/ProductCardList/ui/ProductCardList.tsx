import { ProductCard } from '@/05_entities/product'
import css from './ProductsCards.module.css'
import { ProductId, ProductPreviewCardDto, Status } from "@/05_entities/product/model/types"
import { Roles } from "@/05_entities/user/api/types"
import { ReactNode, useCallback } from "react"
import { useSelector } from "react-redux"



type ProductCardListType = {
    products: ProductPreviewCardDto[]
    productCardActionsSlot?: (productId: ProductId) => ReactNode
}





export function ProductCardList(props: ProductCardListType) {
    console.log(props)
    const { products } = props
    const userId = useSelector((state: RootState) => state.session.userId);
    const userRole = useSelector((state: RootState) => state.session.role);

    const getActionSlot = useCallback(
        (product: ProductPreviewCardDto) => {
            if (props.productCardActionsSlot) {
                if ((userId !== product.ownerId) && (userRole === Roles.ROLE_USER)) {
                    return props.productCardActionsSlot(product.id);
                }
            }
            return null;
        },
        [props.productCardActionsSlot, userId, userRole]
    );

    const getInfoSlot = useCallback(
        (product: ProductPreviewCardDto) => {
            if (product.status === Status.MODERATION_DENIED) {
                return (
                    <span className={css.productCardInfoSpan}>
                        Требует изменений
                    </span>
                );
            }
            return null;
        },
        []
    );

    
    return (
        <div className={css.productListWrapper}>
            {products.map(product => (
                <ProductCard
                    key={product.id}
                    product={product}
                    actionSlot={getActionSlot(product)}
                    info={getInfoSlot(product)}
                />
            ))}
        </div>
    )
}
