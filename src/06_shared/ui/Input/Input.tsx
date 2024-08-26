import { UseFormRegisterReturn } from 'react-hook-form'
import css from './Input.module.css'
import cn from 'classnames'
import { InputHTMLAttributes } from 'react'

type Props = {
  maxLength?: number
  value?: string
  inputStyle?: 'default' | 'description'
  register: UseFormRegisterReturn
  type?: InputHTMLAttributes<HTMLInputElement>['type']
}

export const Input = ({ maxLength = 50, value, inputStyle = 'default', register, type }: Props) => {
  return inputStyle === 'description' ? (
    <textarea
      {...register}
      maxLength={maxLength}
      className={cn(css.root, css.textArea_text)}
      value={value}
    />
  ) : (
    <input
      {...register}
      type={type}
      maxLength={maxLength}
      className={cn(css.root, css.input_text)}
      value={value}
    />
  );
}