import {AxiosRequestConfig} from 'axios';
import {useEffect, useState} from 'react';
import {Controller, useForm} from 'react-hook-form';
import {useHistory} from 'react-router';
import {useParams} from 'react-router-dom';
import {toast} from 'react-toastify';

import {Servidor} from 'core/types/servidor';
import {requestBackend} from 'core/utils/requests';

import './styles.css';
import {Categorias} from 'core/types/categoria';
import Select from "react-select";

type UrlParams = {
  servidorId: string;
};

const Form = () => {
  const { servidorId } = useParams<UrlParams>();
  const isEditing = servidorId !== 'create';
  const history = useHistory();
  const [selectCategorias, setSelectCategorias] = useState<Categorias[]>([]);

  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    control
  } = useForm<Servidor>();

  useEffect(() => {
    requestBackend({ url: '/categorias' }).then(response => {
      setSelectCategorias(response.data.content);
    });
  }, []);

  useEffect(() => {
    if (isEditing) {
      requestBackend({ url: `/servidores/${servidorId}` }).then(response => {
        const servidor = response.data as Servidor;
        setValue('nome', servidor.nome);
        setValue('matricula', servidor.matricula);
        setValue('lotacao', servidor.lotacao);
        setValue('categoria', servidor.categoria);
      });
    }
  }, [isEditing, servidorId, setValue]);


  const onSubmit = (formData: Servidor) => {
    const data = {
      ...formData,
    };
    const config: AxiosRequestConfig = {
      method: isEditing ? 'PUT' : 'POST',
      url: isEditing ? `/servidores/${servidorId}` : '/servidores',
      data,
      withCredentials: true,
    };

    requestBackend(config)
      .then(() => {
        toast.info('Servidor cadastrado com sucesso');
        history.push('/admin/servidor');
      })
      .catch(() => {
        toast.error('Erro ao cadastrar servidor');
      });
  };

  const handleCancel = () => {
    history.push('/admin/servidor');
  };

  return (
    <div className="servidor-crud-container">
      <div className="base-card servidor-crud-form-card">
        <h1 className="servidor-crud-form-title">DADOS DO SERVIDOR</h1>

        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="row servidor-crud-inputs-container">
            <div className="col-lg-6 servidor-crud-inputs-left-container">
              <div className="margin-bottom-30">
                <input
                    {...register('nome', {
                      required: 'Campo obrigatório',
                    })}
                    type="text"
                    className={`form-control base-input ${
                        errors.nome ? 'is-invalid' : ''
                    }`}
                    placeholder="Nome do Servidor"
                    name="nome"
                />
                {errors.nome && (
                  <div className="invalid-feedback d-block">
                    Campo obrigatório
                  </div>
                )}
              </div>
              <div className="margin-bottom-30">
                <input
                  {...register('matricula', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.matricula ? 'is-invalid' : ''
                  }`}
                  placeholder="Matricula"
                  name="matricula"
                />
                <div className="invalid-feedback d-block">
                  {errors.matricula?.message}
                </div>
              </div>
            </div>
            <div className="col-lg-6 servidor-crud-inputs-left-container">
              <div className="margin-bottom-30">
                <input
                  {...register('lotacao', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.lotacao ? 'is-invalid' : ''
                  }`}
                  placeholder="Lotação"
                  name="lotacao"
                />
                <div className="invalid-feedback d-block">
                  {errors.lotacao?.message}
                </div>
              </div>

              <div className="margin-bottom-30">
                <Controller
                    name="categoria"
                    rules={{ required: true }}
                    control={control}
                    render={({ field }) => (
                        <Select
                            {...field}
                            options={selectCategorias}
                            classNamePrefix="product-crud-select"
                            getOptionLabel={(categoria: Categorias) => categoria.descricao}
                            getOptionValue={(categoria: Categorias) =>
                                String(categoria.id)
                            }
                        />
                    )}
                />
                {errors.categoria && (
                    <div className="invalid-feedback d-block">
                      Campo obrigatório
                    </div>
                )}
              </div>
            </div>
          </div>
          <div className="servidor-crud-buttons-container">
            <button
              className="btn btn-outline-danger servidor-crud-button"
              onClick={handleCancel}
            >
              CANCELAR
            </button>
            <button className="btn btn-primary servidor-crud-button text-white">
              SALVAR
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Form;
