import { LayoutHeader } from '@/03_widgets/LayoutHeader'
import { Provider } from 'react-redux'
import { BrowserRouter as Router } from 'react-router-dom';
import '@/06_shared/ui/base.css'
import store from './AppStore'
import { Button } from '@/06_shared/ui/Button/Button';

export function AppEnter() {
    const newPostButtonHandler = () => {

    } 

    return (
        <Provider store={store}>
            <Router>
                <LayoutHeader />
                <Button children = {'Подтвердить'} onClick = {newPostButtonHandler} isLoading={false} disabled={false}/>
            </Router>
        </Provider>
    )
}
