import { Categorias } from 'core/types/categoria';
import './style.css';

type Props = {
  categoria: Categorias;
};

const CategoriaCard = ({ categoria }: Props) => {
  return (
    <div className="base-card product-card">
      <div className="card-bottom-container">
        <h6>
          {categoria.descricao}
        </h6>
      </div>
    </div>
  );
};

export default CategoriaCard;
