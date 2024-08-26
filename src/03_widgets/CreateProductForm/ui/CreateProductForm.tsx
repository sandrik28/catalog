import { CancelButton } from '@/04_features/CancelButton'
import { SendToModeratorButton } from '@/04_features/sendToModerator'
import { LabeledField } from '@/06_shared/ui/LabeledField/LabeledField'
import css from './CreateProductForm.module.css'
import { SubmitHandler, useForm } from 'react-hook-form'
import { useModal } from '@/06_shared/lib/useModal'
import { Modal } from '@/06_shared/ui/Modal/Modal'
import { productsMock } from '@/06_shared/lib/server'
import { ProductDto, ProductId } from '@/05_entities/product'
import { Status } from '@/05_entities/product/model/types'
import { DeleteProductButton } from '@/04_features/deleteProduct'
import { deleteProductRequest } from '@/04_features/deleteProduct/category/api/deleteProductRequest'

export type TCreateProductForm = {
  title: string
  category: string
  linkToWebSite: string
  description: string
  emailOfSupport: string
}

const defaultFormValues = {
  title: '',
  category: '',
  linkToWebSite: '',
  description: '',
  emailOfSupport: '',
}

type Props = {
  productId?: string | undefined
}

export const CreateProductForm = ({productId} : Props) => {
  const { isModalOpen, modalContent, modalType, openModal } = useModal();
  const numericId = Number(productId)

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting, isDirty, dirtyFields, isLoading },
  } = useForm<TCreateProductForm>({
    defaultValues: async () => {
      if (productId) {
        try {
          return await getProductById(numericId)
        } catch (error) {
          openModal('Произошла ошибка при загрузке данных', 'error')
          return defaultFormValues
        }
      } else {
        return defaultFormValues
      }
    }
  })

  // моковый запрос получения данных продукта
  const getProductById = (productId: ProductId): Promise<ProductDto> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const product = productsMock.find(product => product.id === productId)
        if (product) {
          const productWithCorrectTypes: ProductDto = {
            ...product,
            timeOfLastApproval: new Date(product.timeOfLastApproval),
            status: product.status as Status, 
          };
          resolve(productWithCorrectTypes);
        } else {
          reject(new Error('Продукт не найден'))
        }
      }, 2000)
    })
  }

  const onSubmit: SubmitHandler<TCreateProductForm> = async (data) => {
    if (!isDirty) {
      return
    }
    
    // TODO: запрос 
    try {
      // const response = await fetch('https://jsonplaceholder.typicode.com/todos/1')
      const response = await fetch('https://jsonplaceholder.typicode.com/todos/18765432')
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

  const handleDelete = async () => {
    try {
      await deleteProductRequest(numericId)
      openModal('Продукт успешно удалён', 'success')
    } catch (error) {
      openModal('Произошла ошибка при удалении продукта', 'error')
    }
  }

  return (
    <>
      {
        isLoading ? <h1>Загрузка</h1> : 
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
          {productId && Object.keys(dirtyFields).length === 0 && (
            <div className={css.field_error}>Продукт не был изменен</div>
          )}
          <div className={css.container}>
            {productId ? (
              <>
              <div className={css.container}>
                <SendToModeratorButton disabled={isSubmitting} />
                <CancelButton />
              </div>
              <DeleteProductButton onClick={handleDelete}/>
              </>
            ) : (
              <>
                <SendToModeratorButton disabled={isSubmitting} />
                <CancelButton />
              </>
            )}
          </div>
        </form>
      }
      {isModalOpen && <Modal type={modalType}><h3>{modalContent}</h3></Modal>}
    </>
  )
}