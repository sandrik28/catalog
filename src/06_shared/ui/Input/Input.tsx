import css from './Input.module.css'
import cn from 'classnames'

type Props = {
  onChange?: () => void
  maxLength?: number
  value?: string
  type?: 'default' | 'description'; 
}

export const Input = ({ onChange, maxLength = 50, value, type = 'default' }: Props) => {
  return type === 'description' ? (
    <textarea
      onChange={onChange}
      maxLength={maxLength}
      className={cn(css.root, css.textArea_text)}
      value={value}
    />
  ) : (
    <input
      onChange={onChange}
      maxLength={maxLength}
      className={cn(css.root, css.input_text)}
      value={value}
    />
  );
}