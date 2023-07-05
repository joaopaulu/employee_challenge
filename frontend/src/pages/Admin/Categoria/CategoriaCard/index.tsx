import {AxiosRequestConfig} from 'axios';
import {requestBackend} from 'core/utils/requests';
import {Link} from 'react-router-dom';

import './style.css';
import {Categorias} from 'core/types/categoria';

type Props = {
    categoria: Categorias;
    onDelete: Function;
};

const CategoriaCrudCard = ({categoria, onDelete}: Props) => {
    const handleDelete = (categoriaId: number) => {
        if (!window.confirm('Tem certeza que deseja deletar')) {
            return;
        }
        const config: AxiosRequestConfig = {
            method: 'DELETE',
            url: `/categorias/${categoriaId}`,
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
              <strong> {categoria.descricao}</strong>
          </span>
                </div>
            </div>
            <div className="crud-card-buttons-container">
                <button
                    onClick={() => handleDelete(categoria.id)}
                    className="btn btn-outline-danger crud-card-button crud-card-button-first"
                >
                    EXCLUIR
                </button>
                <Link to={`/admin/categoria/${categoria.id}`}>
                    <button className="btn btn-outline-secondary crud-card-button">
                        EDITAR
                    </button>
                </Link>
            </div>
        </div>
    );
};

export default CategoriaCrudCard;
