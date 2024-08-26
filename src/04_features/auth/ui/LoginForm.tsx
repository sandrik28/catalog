import { SubmitHandler, useForm } from 'react-hook-form';
import css from './LoginForm.module.css'
import { Input } from '@/06_shared/ui/Input/Input';
import { Button } from '@/06_shared/ui/Button/Button';
import { useNavigate } from 'react-router-dom';

type FormFields = {
  email: string
  password: string
}

export function LoginForm(){
  const navigate = useNavigate();
  const {
    register, 
    handleSubmit,
    setError,
    formState: {errors, isSubmitting},
  } = useForm<FormFields>();

  // TODO: запрос + записать в стор
  const onSubmit: SubmitHandler<FormFields> = async (data) => {
    try {
      await new Promise((resolve) => setTimeout(resolve, 1000))
      // throw new Error()
      console.log(data)
      navigate('/')
    } catch (error) {
      setError('root', {
        message: 'Не можем найти вас среди участников клуба'
      })
    }
  }

  return (
    <form className={css.form_login} onSubmit={handleSubmit(onSubmit)}>
      <h1>Вход</h1>
      <div className={css.container}>
          <label className={css.label}>Логин</label>
          <Input 
            // type="email" 
            register={register('email', { required: 'Логин не может быть пустым' })}
          />
          {errors.email && <span className={css.error_message}>
            {errors.email.message}
          </span>}
          <label className={css.label}>Пароль</label>
          <Input 
            type="password" 
            register={register('password', { required: 'Пароль не может быть пустым' })}
          />
          {errors.password && <span className={css.error_message}>
            {errors.password.message}
          </span>}
      </div>
      <Button disabled={isSubmitting} type={'submit'}>
        {isSubmitting ? 'Загрузка' : 'Войти в систему'}
      </Button>
      {errors.root && <span className={css.error_message}>{errors.root.message}</span>}
    </form>
  );
};

