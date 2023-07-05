import { AxiosRequestConfig } from 'axios';
import { Roles, User } from 'core/types/user';
import { requestBackend } from 'core/utils/requests';
import { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useHistory } from 'react-router';
import { useParams } from 'react-router-dom';
import Select from 'react-select';
import { toast } from 'react-toastify';
import './styles.css';


type UrlParams = {
  userId: string;
};

const Form = () => {
  const { userId } = useParams<UrlParams>();
  const isEditing = userId !== 'create';
  const history = useHistory();
  const [selectRoles, setSelectRoles] = useState<Roles[]>([]);
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    control
  } = useForm<User>();

  useEffect(() => {
    requestBackend({ url: '/roles' }).then(response => {
      setSelectRoles(response.data.content);
    });
  }, []);

  useEffect(() => {
    if (isEditing) {
      requestBackend({ url: `/users/${userId}` }).then(response => {
        const user = response.data as User;

        setValue('firstName', user.firstName);
        setValue('lastName', user.lastName);
        setValue('email', user.email);
        setValue('roles', user.roles);
        setValue('password', user.password);
      });
    }
  }, [isEditing, userId, setValue]);

  const onSubmit = (formData: User) => {
    const data = {
      ...formData,
    };
    const config: AxiosRequestConfig = {
      method: isEditing ? 'PUT' : 'POST',
      url: isEditing ? `/users/${userId}` : '/users',
      data,
      withCredentials: true,
    };

    requestBackend(config)
      .then(() => {
        toast.info('Usuário cadastrado com sucesso');
        history.push('/admin/users');
      })
      .catch(() => {
        toast.error('Erro ao cadastrar usuário');
      });
  };

  const handleCancel = () => {
    history.push('/admin/users');
  };

  return (
    <div className="product-crud-container">
      <div className="base-card product-crud-form-card">
        <h1 className="product-crud-form-title">DADOS DO USUÁRIO</h1>

        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="row product-crud-inputs-container">
            <div className="col-lg-6 product-crud-inputs-left-container">
              <div className="margin-bottom-30">
                <input
                  {...register('firstName', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.firstName ? 'is-invalid' : ''
                  }`}
                  placeholder="Nome"
                  name="firstName"
                />
                <div className="invalid-feedback d-block">
                  {errors.firstName?.message}
                </div>
              </div>

              <div className="margin-bottom-30">
                <input
                  {...register('lastName', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.lastName ? 'is-invalid' : ''
                  }`}
                  placeholder="Sobre Nome"
                  name="lastName"
                />
                <div className="invalid-feedback d-block">
                  {errors.lastName?.message}
                </div>
              </div>
            </div>
            <div className="col-lg-6 product-crud-inputs-left-container">
              <div className="margin-bottom-30">
                <input
                  {...register('email', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.email ? 'is-invalid' : ''
                  }`}
                  placeholder="E-mail"
                  name="email"
                />
                <div className="invalid-feedback d-block">
                  {errors.email?.message}
                </div>
              </div>

              <div className="margin-bottom-30">
              <Controller
                  name="roles"
                  rules={{ required: true }}
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      options={selectRoles}
                      isMulti
                      classNamePrefix="product-crud-select"
                      getOptionLabel={(role: Roles) => role.authority}
                      getOptionValue={(role: Roles) =>
                        String(role.id)
                      }
                    />
                  )}
                />
                {errors.roles && (
                  <div className="invalid-feedback d-block">
                    Campo obrigatório
                  </div>
                )}
              </div>
            </div>
            <div className="col-lg-6 product-crud-inputs-left-container">
              <div className="margin-bottom-30">
                <input
                  {...register('password', {
                    required: 'Campo obrigatório',
                  })}
                  type="password"
                  className={`form-control base-input ${
                    errors.password ? 'is-invalid' : ''
                  }`}
                  placeholder="Senha"
                  name="password"
                />
                <div className="invalid-feedback d-block">
                  {errors.password?.message}
                </div>
              </div>              
            </div>
          </div>
          
          <div className="product-crud-buttons-container">
            <button
              className="btn btn-outline-danger product-crud-button"
              onClick={handleCancel}
            >
              CANCELAR
            </button>
            <button className="btn btn-primary product-crud-button text-white">
              SALVAR
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Form;
