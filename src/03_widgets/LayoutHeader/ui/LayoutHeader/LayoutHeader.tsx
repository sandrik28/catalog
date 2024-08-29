import React from 'react'
import css from './LayoutHeader.module.css'
import SiteName from '../SiteName/SiteName'
import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { RootState } from '@/01_app/AppStore'
import { Icon} from '@/06_shared/ui/Icon/Icon';



export function LayoutHeader() {
    const userId = useSelector((state: RootState) => state.session.userId);

    return (
        <header className={css.root}>
            <Link to="/">
                <div className={css.left}>
                    <Icon type = "teamlogo" className={css.logo}/>
                    <SiteName />
                </div>
            </Link>
            {
                userId &&
                <Link to={`/profile/${userId}`}>
                    <div className={css.right}>
                        <span>Личный кабинет</span>
                        <Icon type = "headerUser" className={css.icon}/>
                    </div>
                </Link>
            }
        </header>
    )
}
