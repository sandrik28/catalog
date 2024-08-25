import { Input } from '../Input/Input'
import css from './LabeledField.module.css'

type Props = {
  label: string
} & React.ComponentProps<typeof Input>;

export const LabeledField = ({label, register, type, maxLength}: Props) => {
  return (
    <div className={css.root}>
      <label className={css.label_name}>{label}</label>
      <Input
        register={register}
        type={type}
        maxLength={maxLength}
      />
    </div>
  )
}