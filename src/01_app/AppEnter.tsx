import { LayoutHeader } from '@/03_widgets/LayoutHeader'
import { Provider } from 'react-redux'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import '@/06_shared/ui/base.css'
import store from './AppStore'
import { MainPage } from '@/02_pages/main';
import { ProfilePage } from '@/02_pages/profile/ui/Page/Page';

export function AppEnter() {
    const newPostButtonHandler = () => {

    }

    return (
        <Provider store={store}>
            <Router>
                <LayoutHeader />
                {/* пока сделаю так, чтобы потестить попап на странице пользователя*/}
                <Routes>
                    <Route path='/' element={<MainPage/>}/>
                    <Route path='/profile/:id' element={<ProfilePage/>}/>
                </Routes>
            </Router>
        </Provider>
    )
}
