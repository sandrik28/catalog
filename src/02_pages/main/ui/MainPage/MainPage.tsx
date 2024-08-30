import { AddNewProduct } from '@/04_features/newProduct/addNewProduct'
import css from './Page.module.css'
import { MainCategoryWidget } from '@/03_widgets/Category/MainCategory/MainCategory'
import { NotificationDto, NotificationMessage } from '@/05_entities/notification/model/types';
import { NotificationCard } from '@/05_entities/notification/ui/NotificationCard';

  
export function MainPage() {
    return (
        
        <main>
            <div className={css.container}>
                <h1>Цифровые продукты</h1>
                <AddNewProduct />
            </div>
            <MainCategoryWidget />
        </main>
    )
}
