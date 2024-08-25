import { CancelButton } from '@/04_features/CancelButton'
import { SendToModeratorButton } from '@/04_features/sendToModerator'
import { LabeledField } from '@/06_shared/ui/LabeledField/LabeledField'
import css from './CreateProductForm.module.css'
import { SubmitHandler, useForm } from 'react-hook-form'
import { useModal } from '@/06_shared/lib/useModal'
import { Modal } from '@/06_shared/ui/Modal/Modal'
import { useEffect } from 'react'
import { productsMock } from '@/06_shared/lib/server'
import { ProductDto } from '@/05_entities/product'

export type TCreateProductForm = {
  title: string
  category: string
  linkToWebSite: string
  description: string
  emailOfSupport: string
}

type Props = {
  productId?: string | undefined
}

export const CreateProductForm = ({productId} : Props) => {
  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors, isSubmitting },
  } = useForm<TCreateProductForm>()

  const { isModalOpen, modalContent, modalType, openModal } = useModal()

  // моковый запрос получения данных продукта
  const getProductById = (productId: number): Promise<ProductDto> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const product = productsMock.find(product => product.id === productId)
        if (product) {
          resolve(product);
        } else {
          reject(new Error('Продукт не найден'))
        }
      }, 2000)
    })
  }

  useEffect(() => {
    if (productId) {
      const loadProductData = async () => {
        try {
          const product = await getProductById(Number(productId))
          setValue('title', product.title)
          setValue('category', product.category)
          setValue('linkToWebSite', product.linkToWebSite)
          setValue('description', product.description)
          setValue('emailOfSupport', product.emailOfSupport)
        } catch (error) {
          openModal('Произошла ошибка при загрузке данных', 'error')
        }
      }

      loadProductData()
    }
  }, [productId, setValue, openModal]);

  const onSubmit: SubmitHandler<TCreateProductForm> = async (data) => {
    // TODO: запрос 
    try {
      // const response = await fetch('https://jsonplaceholder.typicode.com/todos/1')
      const response = await fetch('https://jsonplaceholder.typicode.com/todos/18765432')
      console.log(response)
      if (response.ok) {
        openModal('Успешно', 'success')
      } else {
        throw new Error('Произошла ошибка')
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