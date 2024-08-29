import { ChooseCategoryWidget } from '@/03_widgets/Category/ChooseCategoryWidget'
import { AddNewProduct } from '@/04_features/newProduct/addNewProduct'
import css from './Page.module.css'
import React from 'react'

export function MainPage() {
  return (
    <main>
      <div className={css.container}>
        <h1>Цифровые продукты</h1>
        <AddNewProduct/>
      </div>
      <ChooseCategoryWidget isMainMenu={true} />
    </main>
  )
}
