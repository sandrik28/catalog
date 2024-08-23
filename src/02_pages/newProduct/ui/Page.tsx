import { LabeledField } from "@/06_shared/ui/LabeledField/LabeledFieled"

export const NewProduct = () => {
  return (
    <main>
      <p>Страница создания продукта</p>
      <LabeledField label={'Название продукта'}/>
      <LabeledField label={'Категория'}/>
    </main>
  )
}