import { Button } from "@/06_shared/ui/Button/Button"

type Props = {
  disabled?: boolean
}

export const SendToModeratorButton = ({disabled} : Props) => {

  // TODO: по клику отправка запроса => открытие попапа
  
  return (
    <Button disabled={disabled} type='submit'>
      { disabled ? 'Загрузка' : 'Опубликовать' }
    </Button>
  )
}