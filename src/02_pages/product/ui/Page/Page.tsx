import { Button } from '@/06_shared/ui/Button/Button'
import React from 'react'
import { Link } from 'react-router-dom'

export function ProductPage() {
  return (
    <main>
      <h1>Страница продукта</h1>
      <Link to='edit'>
        <Button>Редактировать</Button>
      </Link>
    </main>
  )
}
