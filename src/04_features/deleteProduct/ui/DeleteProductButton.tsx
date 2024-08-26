import { Button } from "@/06_shared/ui/Button/Button"
import { useState } from "react"

type Props = {
  onClick: () => void 
}

export const DeleteProductButton = ({onClick} : Props) => {
  const [isDisabled, setIsDisabled] = useState<boolean>(false)

  const handleClick = () => {
    setIsDisabled(true)
    onClick()
  }

  return (
    <Button disabled={isDisabled} onClick={handleClick}>
      { isDisabled ? 'Загрузка' : 'Удалить' }
    </Button>
  )
}