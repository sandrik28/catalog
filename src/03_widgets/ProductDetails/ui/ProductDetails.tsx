import { IProductDetails } from '@/05_entities/product/model/types'
import css from './ProductDetails.module.css'
import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { StatusMessage } from './StatusMessage/StatusMessage'
import { Roles } from '@/05_entities/user/api/types'
import { AdminButtons } from '@/04_features/adminButtons'
import { UserButtons } from './UserButtons/UserButtons'

type Props = {
  product: IProductDetails
}

export const ProductDetails = ({ product }: Props) => {
  const userId = useSelector((state: RootState) => state.session.userId);
  const userRole = useSelector((state: RootState) => state.session.role);
  const isOwner = product.ownerId === userId;

  return (
    <>
      {userRole === Roles.ROLE_USER && <StatusMessage status={product.status}/>}
      <h1>{product.title}</h1>
      <div className={css.content}>
        <div className={css.info}>
          <h2>Владелец продукта</h2>
          <Link to={`/profile/${product.ownerId}`}>{product.nameOfOwner}</Link>
        </div>
        <div className={css.info}>
          <h2>Ссылка на продукт</h2>
          <Link 
            to={product.linkToWebSite}
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
      {userRole === Roles.ROLE_USER ? 
        <UserButtons isOwner={isOwner} status={product.status}/> : 
        <AdminButtons status={product.status}/>
      }
    </>
  )
}