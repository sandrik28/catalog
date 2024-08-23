import { Input } from '../Input/Input'
import css from './LabeledField.module.css'

type Props = {
  label: string
}

export const LabeledField = ({label} : Props) => {
  return (
    <div className={css.root}>
      <label className={css.label_name}>{label}</label>
      <Input/>
    </div>
  )
}