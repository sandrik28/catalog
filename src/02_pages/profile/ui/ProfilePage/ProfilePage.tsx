import React, { useEffect, useState } from 'react'
import { Profile } from '@/03_widgets/Profile';
import { User, UserDto } from '@/05_entities/user';
import { ProfileCategory } from '@/03_widgets/Category/ProfileCategory/ProfileCategory';
import { useNavigate, useParams } from 'react-router-dom';
import { userMock } from '@/06_shared/lib/server';
import { Roles } from '@/05_entities/user/api/types';
import { useModal } from '@/06_shared/lib/useModal';

// моковый запрос получения данных пользователя
const getUserById = (profileId: number): Promise<UserDto> => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const user = userMock.find(user => user.id === profileId)
      // if (profile) {
      if (user) {
        const userWithCorrectTypes: UserDto = {
          ...user,
          role: user.role as Roles, 
        };
        resolve(userWithCorrectTypes);
      } else {
        reject(new Error('Профиль не найден'))
      }
    }, 500)
  })
}

export function ProfilePage() {

  // TODO: получить данные пользователя по id при загрузке страницы
  const { isModalOpen, modalContent, modalType, openModal } = useModal();
  const [user, setUser] = useState<UserDto | null>();
  const userId = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        // TODO: запрос на получение продукта по айди
        const fetchedUser = await getUserById(Number(userId))
        if (fetchedUser) {
          setUser(fetchedUser)
        } else {
          openModal('Пользователь не найден', 'error')
        }
      } catch (err) {
        openModal('Произошла ошибка при загрузке данных', 'error')
      } finally {
        setLoading(false)
      }
    }

    fetchUser()
  }, [userId, openModal])


  return (
    <>
      <Profile />
      {user && 
        <User
          // TODO: берется из запроса
          // user={{
          //   id: 0,
          //   name: 'Егор Росошанский',
          //   email: 'test@mail.com',
          // }}
          user={user}
        />
      }
      <ProfileCategory />
    </>
  )
}