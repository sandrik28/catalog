import { LayoutHeader } from '@/03_widgets/LayoutHeader'
import { Provider } from 'react-redux'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import '@/06_shared/ui/base.css'
import store from './AppStore'
import { MainPage } from '@/02_pages/main';
import { ProfilePage } from '@/02_pages/profile/ui/Page/Page';
import { ProductCard, type ProductCardType, type ProductId } from '@/05_entities/product';
import { NewProduct } from '@/02_pages/newProduct';
import { AddToWishlistIcon } from '@/04_features/wishlist/addToWishlist/ui/AddToWishlistIcon';



export function AppEnter() {
    const newPostButtonHandler = () => {

    }

    
    const productCardJson: ProductCardType = {
        id: 1,
        title: 'ProductNameTest for test in test01',
        description: 'Product Description is our mission 01 Product Description is our mission 01 Product Description is our mission 01',
        category: 'itAndAllWorld'
    }


    return (
        <Provider store={store}>
            <Router>
                <LayoutHeader />
                <Routes>
                    <Route path='/' element={<MainPage />} />
                    <Route path='/profile/:id' element={<ProfilePage />} />
                    <Route path='/addNewProduct' element={<NewProduct />} />
                    <Route path='/catalog/all' element={<ProductCard actionSlot={<AddToWishlistIcon productId={productCardJson.id as ProductId} />} product={productCardJson} info={<span className="product_category">sss</span>} />} />
                </Routes>
            </Router>
        </Provider>
    )
}
