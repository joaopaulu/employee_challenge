import { ReactComponent as SearchIcon } from 'core/assets/images/search-icon.svg';
import { useForm } from 'react-hook-form';

import './styles.css';

export type ServidorFilterData = {
  nome: string;
  matricula: string;
};

type Props = {
  onSubmitFilter: (data: ServidorFilterData) => void;
};

const ServidorFilter = ({ onSubmitFilter }: Props) => {
  const { register, handleSubmit, setValue } = useForm<ServidorFilterData>();

  const onSubmit = (formData: ServidorFilterData) => {
    onSubmitFilter(formData);
  };

  const handleFormClear = () => {
    setValue('nome', '');
    setValue('matricula', '');
  };

  return (
    <div className="base-card servidor-filter-container">
      <form onSubmit={handleSubmit(onSubmit)} className="servidor-filter-form">
        <div className="servidor-filter-name-container">
          <input
            {...register('nome')}
            type="text"
            className="form-control"
            placeholder="Servidor"
            name="nome"
          />
          <input
            {...register('matricula')}
            type="text"
            className="form-control"
            placeholder="Matricula"
            name="matricula"
          />
          <button className="servidor-filter-search-icon">
            <SearchIcon />
          </button>
        </div>
        <div className="servidor-filter-bottom-container">
          <button
            onClick={handleFormClear}
            className="btn btn-outline-secondary btn-servidor-filter-clear"
          >
            LIMPAR FILTRO
          </button>
        </div>
      </form>
    </div>
  );
};

export default ServidorFilter;
