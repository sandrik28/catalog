import { CancelButton } from '@/04_features/CancelButton'
import { SendToModeratorButton } from '@/04_features/sendToModerator'
import { LabeledField } from '@/06_shared/ui/LabeledField/LabeledField'
import css from './CreateProductForm.module.css'

export const CreateProductForm = () => {

  return (
    <form>
      <LabeledField label={'Название продукта'}/>
      <LabeledField label={'Категория'}/>
      <LabeledField label={'Ссылка на продукт'}/>
      <LabeledField 
        label={'Описание продукта'} 
        type={'description'}
        maxLength={500}
      />
      <LabeledField label={'Контакт поддержки'}/>
      <div className={css.container}>
        <SendToModeratorButton/>
        <CancelButton/>  
      </div>
    </form>
  )
}