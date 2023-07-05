import {AxiosRequestConfig} from 'axios';
import {Servidor} from 'core/types/servidor';
import {requestBackend} from 'core/utils/requests';
import {Link} from 'react-router-dom';

import './style.css';

type Props = {
  servidor: Servidor;
  onDelete: Function;
};

const ServidorCrudCard = ({ servidor, onDelete }: Props) => {
  const handleDelete = (servidorId: number) => {
    if (!window.confirm('Tem certeza que deseja deletar')) {
      return;
    }
    const config: AxiosRequestConfig = {
      method: 'DELETE',
      url: `/servidores/${servidorId}`,
      withCredentials: true,
    };
    requestBackend(config).then(() => {
      onDelete();
    });
  };

  return (
    <div className="base-card crud-card">
      <div className="crud-card-description">
        <div className="crud-card-bottom-container">
          <span>
            Nome:<strong> {servidor.nome}</strong> -{' '}
          </span>
          <span>
            Matricula:<strong> {servidor.matricula}</strong> -{' '}
          </span>
          <span>
            Lotacao:<strong> {servidor.lotacao}</strong> -{' '}
          </span>
          <span>
            Categoria:<strong> {servidor.categoria.descricao}</strong>
          </span>
        </div>
      </div>
      <div className="crud-card-buttons-container">
        <button
          onClick={() => handleDelete(servidor.id)}
          className="btn btn-outline-danger crud-card-button crud-card-button-first"
        >
          EXCLUIR
        </button>
        <Link to={`/admin/servidor/${servidor.id}`}>
          <button className="btn btn-outline-secondary crud-card-button">
            EDITAR
          </button>
        </Link>
      </div>
    </div>
  );
};

export default ServidorCrudCard;
