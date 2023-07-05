import {AxiosRequestConfig} from 'axios';
import {useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {useHistory} from 'react-router';
import {useParams} from 'react-router-dom';
import {toast} from 'react-toastify';

import {Categorias} from 'core/types/categoria';
import {requestBackend} from 'core/utils/requests';

import './styles.css';

type UrlParams = {
  categoriaId: string;
};

const Form = () => {
  const { categoriaId } = useParams<UrlParams>();
  const isEditing = categoriaId !== 'create';
  const history = useHistory();

  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
  } = useForm<Categorias>();

  useEffect(() => {
    if (isEditing) {
      requestBackend({ url: `/categorias/${categoriaId}` }).then(response => {
        const categoria = response.data as Categorias;
        setValue('descricao', categoria.descricao);
      });
    }
  }, [isEditing, categoriaId, setValue]);

  const onSubmit = (formData: Categorias) => {
    const data = {
      ...formData,
    };
    const config: AxiosRequestConfig = {
      method: isEditing ? 'PUT' : 'POST',
      url: isEditing ? `/categorias/${categoriaId}` : '/categorias',
      data,
      withCredentials: true,
    };

    requestBackend(config)
      .then(() => {
        toast.info('Categoria cadastrado com sucesso');
        history.push('/admin/categoria');
      })
      .catch(() => {
        toast.error('Erro ao cadastrar categoria');
      });
  };

  const handleCancel = () => {
    history.push('/admin/categoria');
  };

  return (
    <div className="categoria-crud-container">
      <div className="base-card categoria-crud-form-card">
        <h1 className="categoria-crud-form-title">DADOS DA CATEGORIA</h1>

        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="row categoria-crud-inputs-container">   

              <div className="margin-bottom-30">
                <input
                  {...register('descricao', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.descricao ? 'is-invalid' : ''
                  }`}
                  placeholder="Categoria"
                  name="descricao"
                />
                <div className="invalid-feedback d-block">
                  {errors.descricao?.message}
                </div>
              </div>
            </div>
           
          <div className="categoria-crud-buttons-container">
            <button
              className="btn btn-outline-danger categoria-crud-button"
              onClick={handleCancel}
            >
              CANCELAR
            </button>
            <button className="btn btn-primary categoria-crud-button text-white">
              SALVAR
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Form;
