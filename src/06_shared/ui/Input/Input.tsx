import css from './Input.module.css'

type Props = {
  onChange?: () => void
  maxLength?: number
  value?: string
}

export const Input = ({ onChange, maxLength = 50, value }: Props) => {
  return (
    <input
      onChange={onChange}
      maxLength={maxLength}
      className={css.root}
      value={value}
    />
  )
}