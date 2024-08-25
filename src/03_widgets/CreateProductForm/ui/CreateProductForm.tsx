import { CancelButton } from '@/04_features/CancelButton'
import { SendToModeratorButton } from '@/04_features/sendToModerator'
import { LabeledField } from '@/06_shared/ui/LabeledField/LabeledField'
import css from './CreateProductForm.module.css'
import { SubmitHandler, useForm } from 'react-hook-form'
import { useModal } from '@/06_shared/lib/useModal'
import { Modal } from '@/06_shared/ui/Modal/Modal'

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

  const { isModalOpen, modalContent, modalType, openModal } = useModal() 

  const onSubmit: SubmitHandler<TCreateProductForm> = async (data) => {
    // TODO: запрос
    try {
      // const response = await fetch('https://jsonplaceholder.typicode.com/todos/1')
      const response = await fetch('https://jsonplaceholder.typicode.com/todos/18765432')
      console.log(response)
      if (response.ok) {
        openModal('Успешно', 'success')
      } else {
        throw new Error()
      }
    } catch(error) {
      openModal('Произошла ошибка', 'error')
    }

    console.log(data)
  };

  return (
    <>
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
      {isModalOpen && <Modal type={modalType}><h3>{modalContent}</h3></Modal>}
    </>
  )
}