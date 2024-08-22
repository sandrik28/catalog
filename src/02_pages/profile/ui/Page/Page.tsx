import { Profile } from '@/03_widgets/Profile';
import { User } from '@/05_entities/user';
import { useModal } from '@/06_shared/lib/useModal';
import { Button } from '@/06_shared/ui/Button/Button'
import { Modal } from '@/06_shared/ui/Modal/Modal';
import React from 'react'

export function ProfilePage() {

  const { isModalOpen, modalContent, modalType, openModal } = useModal(); 

  // TODO: получить данные пользователя по id при загрузке страницы

  // пример работы попапа
  const publishProduct = async () => {
    try {
      // const response = await fetch('https://jsonplaceholder.typicode.com/todos/1');
      const response = await fetch('https://jsonplaceholder.typicode.com/todos/18765432');
      if (response.ok) {
        openModal('Успешно', 'success');
      } else {
        throw new Error('Произошла ошибка');
      }
    } catch (error) {
      openModal('Произошла ошибка', 'error');
    }
  };

  return (  
    <main>
      <Profile/>
      <User
        // TODO: берется из запроса
        user={{
          id: 0,
          name: 'Егор Росошанский',
          email: 'test@mail.com',
        }}
      />
      {isModalOpen && <Modal type={modalType}><h3>{modalContent}</h3></Modal>}
      <div>
        <Button children = {'Попап'} onClick = {publishProduct} isLoading={false} disabled={false}/>
      </div>
    </main>
  )
}
