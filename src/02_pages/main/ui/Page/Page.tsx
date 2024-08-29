import { ChooseCategoryWidget } from '@/03_widgets/Category/ChooseCategoryWidget'
import { AddNewProduct } from '@/04_features/newProduct/addNewProduct'
import React from 'react'

export function MainPage() {
  return (
    <main>
      <AddNewProduct/>
      <ChooseCategoryWidget isMainMenu={true} />
    </main>
  )
}
