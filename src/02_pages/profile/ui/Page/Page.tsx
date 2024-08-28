import React from 'react'
import { Profile } from '@/03_widgets/Profile';
import { User } from '@/05_entities/user';
import { ChooseCategoryWidget } from '@/03_widgets/Category/ChooseCategoryWidget';

export function ProfilePage() {

  // TODO: получить данные пользователя по id при загрузке страницы

  return (
    <main>
      <Profile />
      <User
        // TODO: берется из запроса
        user={{
          id: 0,
          name: 'Егор Росошанский',
          email: 'test@mail.com',
        }}
      />
      <ChooseCategoryWidget />
    </main>
  )
}