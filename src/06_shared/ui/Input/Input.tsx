import { UseFormRegisterReturn } from 'react-hook-form'
import css from './Input.module.css'
import cn from 'classnames'

type Props = {
  maxLength?: number
  value?: string
  type?: 'default' | 'description'
  register: UseFormRegisterReturn
}

export const Input = ({ maxLength = 50, value, type = 'default', register }: Props) => {
  return type === 'description' ? (
    <textarea
      {...register}
      maxLength={maxLength}
      className={cn(css.root, css.textArea_text)}
      value={value}
    />
  ) : (
    <input
      {...register}
      maxLength={maxLength}
      className={cn(css.root, css.input_text)}
      value={value}
    />
  );
}