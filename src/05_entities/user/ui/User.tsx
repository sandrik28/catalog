import { UserType } from "../model/types";
import css from './User.module.css';

type Props = {
  user: UserType;
};

export const User = ({user} : Props) => {
  return (
    <div className={css.root}>
      <h1>{`${user.lastName} ${user.firstName}`}</h1>
      <p>Связаться: <a href={`mailto:${user.email}`}> {user.email}</a></p>
    </div> 
  )
};