import { Link, useParams } from 'react-router-dom'
import css from './Profile.module.css'
import { Button } from '@/06_shared/ui/Button/Button'
import { AddNewProduct } from '@/04_features/newProduct/addNewProduct'
import { useSelector } from 'react-redux'
import { RootState } from '@/01_app/AppStore'

export const Profile = () => {
  const userId = useSelector((state: RootState) => state.session.userId);
  const { id } = useParams();

  return (
    <div className={css.root}>
      <div className={css.container}>
        <h2 className={css.profile_title}>Профиль</h2>
        {
          userId === id ?
            <Link to='edit'>
              <Button>Редактировать</Button>
            </Link> :
            null
        }
      </div>
      {
        userId === id ? <AddNewProduct/> : null
      }
    </div>
  )
}
