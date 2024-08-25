import { CancelButton } from '@/04_features/CancelButton'
import { SendToModeratorButton } from '@/04_features/sendToModerator'
import { LabeledField } from '@/06_shared/ui/LabeledField/LabeledField'
import css from './CreateProductForm.module.css'
import { SubmitHandler, useForm } from 'react-hook-form'

export type TCreateProductForm = {
  title: string
  category: string
  linkToWebSite: string
  description: string
  emailOfSupport: string
}

export const CreateProductForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<TCreateProductForm>()

  const onSubmit: SubmitHandler<TCreateProductForm> = async (data) => {
    // TODO: запрос
    await new Promise((resolve) => setTimeout(resolve, 2000))

    console.log(data);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <LabeledField label={'Название продукта'} register={register('title', { required: true })} />
      <LabeledField label={'Категория'} register={register('category', { required: true })} />
      <LabeledField 
        label={'Ссылка на продукт'} 
        register={register('linkToWebSite', { 
          required: true, 
        })} 
      />
      <LabeledField
        label={'Описание продукта'}
        type={'description'}
        maxLength={500}
        register={register('description', { 
          required: true, 
          // minLength: 10
        })}     
      />
      <LabeledField label={'Контакт поддержки'} register={register('emailOfSupport', { required: true })} />
      {Object.keys(errors).length > 0 && 
        <div className={css.field_error}>Все поля должны быть заполнены</div>
      }
      <div className={css.container}>
        <SendToModeratorButton disabled={isSubmitting} />
        <CancelButton />
      </div>
    </form>
  )
}