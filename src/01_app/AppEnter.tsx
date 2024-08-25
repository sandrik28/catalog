import { LayoutHeader } from '@/03_widgets/LayoutHeader'
import { Provider } from 'react-redux'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import '@/06_shared/ui/base.css'
import store from './AppStore'
import { MainPage } from '@/02_pages/main';
import { ProfilePage } from '@/02_pages/profile/ui/Page/Page';
import { ProductCard, type ProductCardType, type ProductId } from '@/05_entities/product';
import { ProductFormPage } from '@/02_pages/newProduct';
import { AddToWishlistIcon } from '@/04_features/wishlist/addToWishlist/ui/AddToWishlistIcon';
import { ProductPage } from '@/02_pages/product/ui/Page/Page';
import { Status } from '@/05_entities/product/model/types';



export function AppEnter() {
    const newPostButtonHandler = () => {

    }

    
    const productCardJson: ProductCardType = {
        "id": 18,
        "title": "Сервис управления закупками ProcurePlus",
        "description": "Платформа для автоматизации процессов закупок с поддержкой тендеров и интеграцией с ERP системами.",
        "category": "Закупки",
        "status": "ON_MODERATION" as Status,
      }


    return (
        <Provider store={store}>
            <Router>
                <LayoutHeader />
                <Routes>
                    <Route path='/' element={<MainPage />} />
                    <Route path='/profile/:id' element={<ProfilePage />} />
                    <Route path='/product/:id' element={<ProductPage />} />
                    <Route path='/product/:id/edit' element={<ProductFormPage />} />
                    <Route path='/addNewProduct' element={<ProductFormPage />} />
                    <Route path='/catalog/all' element={<ProductCard actionSlot={<AddToWishlistIcon productId={productCardJson.id as ProductId} />} product={productCardJson} info={<span className="product_category">sss</span>} />} />
                </Routes>
            </Router>
        </Provider>
    )
}
