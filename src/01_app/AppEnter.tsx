import { LayoutHeader } from '@/03_widgets/LayoutHeader'
import { Provider} from 'react-redux'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import '@/06_shared/ui/base.css'
import store from './AppStore'
import { ProfilePage } from '@/02_pages/profile/ui/Page/Page';
import { ProductFormPage } from '@/02_pages/newProduct';
import { ProductPage } from '@/02_pages/product/ui/Page/Page';
import { MainPage } from '@/02_pages/main';
import { HomePage } from '@/02_pages/catalog/ui/HomePage';
import { LoginPage } from '@/02_pages/login';



export function AppEnter() {
    

    return (
        <Provider store={store}>
            <Router>
                <LayoutHeader />
                <Routes>
                    <Route path='/' element={<MainPage />} />
                    <Route path='/login' element={<LoginPage />} />
                    <Route path='/profile/:id' element={<ProfilePage />} />
                    {/* <Route path='/profile/:id' element={<ProfilePageTest />} /> */}
                    <Route path='/product/:id' element={<ProductPage />} />
                    <Route path='/product/:id/edit' element={<ProductFormPage />} />
                    <Route path='/addNewProduct' element={<ProductFormPage />} />
                    <Route path='/catalog/all' element={<HomePage />}/>
                </Routes>
            </Router>
        </Provider>
    )
}
