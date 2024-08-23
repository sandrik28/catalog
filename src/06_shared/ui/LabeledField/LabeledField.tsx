import { Input } from '../Input/Input'
import css from './LabeledField.module.css'


type Props = {
  label: string
} & React.ComponentProps<typeof Input>;

export const LabeledField = (props: Props) => {
  return (
    <div className={css.root}>
      <label className={css.label_name}>{props.label}</label>
      <Input
        type={props.type}
        maxLength={props.maxLength}
      />
    </div>
  )
}