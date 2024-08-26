import { getProductById } from '@/03_widgets/CreateProductForm/ui/CreateProductForm';
import { ProductDetails } from '@/03_widgets/ProductDetails';
import { IProductDetails } from '@/05_entities/product/model/types';
import { useModal } from '@/06_shared/lib/useModal';
import { Button } from '@/06_shared/ui/Button/Button'
import { Modal } from '@/06_shared/ui/Modal/Modal';
import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'

export const ProductPage = () => {
  const { isModalOpen, modalContent, modalType, openModal } = useModal();
  const { id: profileId } = useParams<{ id: string }>();
  const [product, setProduct] = useState<IProductDetails | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  // TODO: доавить лоадер

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        // TODO: запрос на получение продукта по айди
        const fetchedProduct = await getProductById(Number(profileId))
        if (fetchedProduct) {
          setProduct(fetchedProduct)
        } else {
          openModal('Продукт не найден', 'error')
        }
      } catch (err) {
        openModal('Произошла ошибка при загрузке данных', 'error')
      } finally {
        setLoading(false)
      }
    }

    fetchProduct()
  }, [profileId, openModal])

  if (loading) {
    return <main>Загрузка</main>
  }

  return (
    <main>
      {product && <ProductDetails product={product} />}
      <Link to='edit'>
        <Button>Редактировать</Button>
      </Link>
      {isModalOpen && <Modal type={modalType}><h3>{modalContent}</h3></Modal>}
    </main>
  )
}
