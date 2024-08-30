import React from 'react'
import { Profile } from '@/03_widgets/Profile';
import { User } from '@/05_entities/user';
import { ProfileCategory } from '@/03_widgets/Category/ProfileCategory/ProfileCategory';

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
      <ProfileCategory />
    </main>
  )
}