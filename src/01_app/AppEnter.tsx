import { LayoutHeader } from '@/03_widgets/LayoutHeader'
import { Provider, useDispatch } from 'react-redux'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import '@/06_shared/ui/base.css'
import store from './AppStore'
import { ProfilePage } from '@/02_pages/profile/ui/Page/Page';
import { type ProductId } from '@/05_entities/product';
import { ProductFormPage } from '@/02_pages/newProduct';
import { AddToWishlistIcon } from '@/04_features/wishlist/addToWishlist/ui/AddToWishlistIcon';
import { ProductPage } from '@/02_pages/product/ui/Page/Page';
import { Status } from '@/05_entities/product/model/types';
import { ProductCardList } from '@/03_widgets/ProductCardList/ui/ProductCardList';
import { MainPage } from '@/02_pages/main';
import { mockProductDto } from '@/05_entities/product/api/__mocks__/mockProductDto';
import { HomePage } from '@/02_pages/catalog/ui/HomePage';



export function AppEnter() {
    
    const data = mockProductDto()

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
                    <Route path='/catalog/all' element={<HomePage />}/>
                </Routes>
            </Router>
        </Provider>
    )
}
