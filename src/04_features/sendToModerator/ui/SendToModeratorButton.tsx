import { Button } from "@/06_shared/ui/Button/Button"

export const SendToModeratorButton = () => {
  // TODO: пока все поля не заполнены, кнопка disabled
  // TODO: по клику отправка запроса => открытие попапа
  
  return (
    <Button
      children={'Опубликовать'}
      disabled={true}
      type='submit'
    />
  )
}