import { CreateProductForm } from "@/03_widgets/CreateProductForm"
import { useParams } from "react-router-dom";

export const ProductFormPage = () => {
  const { id } = useParams()

  // TODO: добавить лоадер

  return (
    <main>
      <CreateProductForm productId={id}/>
    </main>
  )
}