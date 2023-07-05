import { AxiosRequestConfig } from 'axios';
import { User } from 'core/types/user';
import { requestBackend } from 'core/utils/requests';
import { Link } from 'react-router-dom';
import './style.css';

type Props = {
  user: User;
  onDelete: Function;
};

const Card = ({ user, onDelete }: Props) => {
  const handleDelete = (userId: number) => {
    if (!window.confirm('Tem certeza que deseja deletar')) {
      return;
    }
    const config: AxiosRequestConfig = {
      method: 'DELETE',
      url: `/users/${userId}`,
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
            {user.firstName} {user.lastName} - {user.email}
          </span>
        </div>
      </div>
      <div className="crud-card-buttons-container">
        <button
          onClick={() => handleDelete(user.id)}
          className="btn btn-outline-danger crud-card-button crud-card-button-first btn-sm"
        >
          EXCLUIR
        </button>
        <Link to={`/admin/users/${user.id}`}>
          <button className="btn btn-outline-secondary crud-card-button btn-sm">
            EDITAR
          </button>
        </Link>
      </div>
    </div>
  );
};

export default Card;
