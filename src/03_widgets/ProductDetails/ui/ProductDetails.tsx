import { IProductDetails } from '@/05_entities/product/model/types'
import css from './ProductDetails.module.css'
import { Link } from 'react-router-dom'

type Props = {
  product: IProductDetails
}

export const ProductDetails = ({ product }: Props) => {
  return (
    <>
      <h1>{product.title}</h1>
      <div className={css.content}>
        <div className={css.info}>
          <h2>Владелец продукта</h2>
          <Link to={`/profile/${product.ownerId}`}>{product.nameOfOwner}</Link>
        </div>
        <div className={css.info}>
          <h2>Ссылка на продукт</h2>
          <Link 
            to={`/profile/${product.ownerId}`} 
            className={css.text}
          >
            {product.linkToWebSite}
          </Link>
        </div>
        <div className={css.info}>
          <h2>Описание проекта</h2>
          <div>
            <p className={css.description}>{product.description}</p>
          </div>
        </div>
        <div className={css.info}>
          <h2>Контакт поддержки</h2>
          <a 
            href={`mailto:${product.emailOfSupport}`} 
            className={css.text}
          >
            {product.emailOfSupport}
          </a>
        </div>
      </div>
    </>
  )
}