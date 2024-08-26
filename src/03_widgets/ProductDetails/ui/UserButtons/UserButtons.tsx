import { DeleteProductButton } from '@/04_features/deleteProduct'
import { SendToModeratorButton } from '@/04_features/sendToModerator'
import { Status } from '@/05_entities/product/model/types'
import { Button } from '@/06_shared/ui/Button/Button'
import { EditProductButton } from '@/06_shared/ui/EditProductButton/EditProductButton'
import css from './UserButtons.module.css'

type Props = {
  isOwner: boolean
  status: Status
}

export const UserButtons = ({isOwner, status} : Props) => {
  // Если ты не владелец продукта
  // TODO: запрос
  if (!isOwner) {
    if (status === Status.APPROVED) {
      return (
        <Button>В избранное</Button>
      )
    }
    return null
  }

  // Если ты владелец продукта
  switch (status) {
    case Status.APPROVED:
      return (
        <div className={css.container}>
          <EditProductButton/>
          <DeleteProductButton onClick={() => console.log('удаление продукта')}/>
        </div>
      )
    case Status.ARCHIVED:
      return (
        <div className={css.container}>
          <div className={css.container}>
            <SendToModeratorButton/>
            <EditProductButton/>
          </div>
          <DeleteProductButton onClick={() => console.log('удаление продукта')}/>
        </div>
      )
    case Status.MODERATION_DENIED:
      return (
        <EditProductButton/>
      )
    default:
      return null;
  }
}