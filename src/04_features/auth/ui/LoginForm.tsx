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
  } = useForm<FormFields>({
    mode: 'onBlur'
  });

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
          <label className={css.label} htmlFor='login'>Логин</label>
          <Input
            // type="email" 
            id='login'
            register={register('email', { required: 'Логин не может быть пустым' })}
          />
          <div className={css.error_block}>
            {errors.email && <span className={css.error_message}>
              {errors.email.message}
            </span>}
          </div>
          <label htmlFor='password' className={css.label}>Пароль</label>
          <Input 
            id='password'
            type='password' 
            register={register('password', { required: 'Пароль не может быть пустым' })}
          />
          <div className={css.error_block}>
            {errors.password && <span className={css.error_message}>
              {errors.password.message}
            </span>}
          </div>
      </div>
      <Button isLoading={isSubmitting} type={'submit'}>
        Войти в систему
      </Button>
      <div className={css.error_block}>
        {errors.root && <span className={css.error_message}>{errors.root.message}</span>}
      </div>
    </form>
  );
};

