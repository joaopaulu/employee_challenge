import { Servidor } from 'core/types/servidor';
import './style.css';

type Props = {
  servidor: Servidor;
};

const ServidorCard = ({ servidor }: Props) => {
  return (
    <div className="base-card servidor-card">
      <div className="card-bottom-container">
        <h6>
          Nome: {servidor.nome}
        </h6>
          <span>
              Matricula: {servidor.matricula}
          </span>
      </div>
    </div>
  );
};

export default ServidorCard;
