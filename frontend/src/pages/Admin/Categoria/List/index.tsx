import { AxiosRequestConfig } from 'axios';
import Pagination from 'components/Pagination';
import { Categorias } from 'core/types/categoria';
import { SpringPage } from 'core/types/vendor/spring';
import { requestBackend } from 'core/utils/requests';
import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import CategoriaCrudCard from '../CategoriaCard';
import './styles.css';

type ControlComponentsData = {
  activePage: number;
};

const List = () => {
  const [page, setPage] = useState<SpringPage<Categorias>>();

  const [controlComponentsData, setControlComponentsData] =
    useState<ControlComponentsData>({
      activePage: 0,
    });

  const handlePageChange = (pageNumber: number) => {
    setControlComponentsData({
      activePage: pageNumber,
    });
  };

  const getCategorias = useCallback(() => {
    const config: AxiosRequestConfig = {
      method: 'GET',
      url: '/categorias',
      params: {
        page: controlComponentsData.activePage,
        size: 6
      },
    };

    requestBackend(config).then(response => {
      setPage(response.data);
    });
  }, [controlComponentsData]);

  useEffect(() => {
    getCategorias();
  }, [getCategorias]);

  return (
    <div className="categoria-crud-container">
      <div className="categoria-crud-bar-container">
        <Link to="/admin/categoria/create">
          <button className="btn btn-primary text-white btn-crud-add">
            ADICIONAR
          </button>
        </Link>
      </div>
      <div className="row">
        {page?.content.map(categoria => (
          <div key={categoria.id} className="col-sm-6 col-md-12">
            <CategoriaCrudCard categoria={categoria} onDelete={getCategorias} />
          </div>
        ))}
      </div>
      <Pagination
        forcePage={page?.number}
        pageCount={page ? page.totalPages : 0}
        range={3}
        onChange={handlePageChange}
      />
    </div>
  );
};

export default List;
