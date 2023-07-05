import { AxiosRequestConfig } from 'axios';
import ServidorFilter, { ServidorFilterData } from 'components/ServidorFilter';
import Pagination from 'components/Pagination';
import { Servidor } from 'core/types/servidor';
import { SpringPage } from 'core/types/vendor/spring';
import { requestBackend } from 'core/utils/requests';
import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import ServidorCrudCard from '../ServidorCard';
import './styles.css';

type ControlComponentsData = {
  activePage: number;
  filterData: ServidorFilterData;
};

const List = () => {
  const [page, setPage] = useState<SpringPage<Servidor>>();

  const [controlComponentsData, setControlComponentsData] =
    useState<ControlComponentsData>({
      activePage: 0,
      filterData: { nome: '', matricula: '' },
    });

  const handlePageChange = (pageNumber: number) => {
    setControlComponentsData({
      activePage: pageNumber,
      filterData: controlComponentsData.filterData,
    });
  };

  const handleSubmitFilter = (data: ServidorFilterData) => {
    setControlComponentsData({ activePage: 0, filterData: data });
  };

  const getServidor = useCallback(() => {
    const config: AxiosRequestConfig = {
      method: 'GET',
      url: '/servidores',
      params: {
        page: controlComponentsData.activePage,
        size: 6,
        nome: controlComponentsData.filterData.nome,
        matricula: controlComponentsData.filterData.matricula,
      },
    };

    console.log(getServidor);

    requestBackend(config).then(response => {
      setPage(response.data);
    });
  }, [controlComponentsData]);

  useEffect(() => {
    getServidor();
  }, [getServidor]);

  return (
    <div className="servidor-crud-container">
      <div className="servidor-crud-bar-container">
        <Link to="/admin/servidor/create">
          <button className="btn btn-primary text-white btn-crud-add">
            ADICIONAR
          </button>
        </Link>
        <ServidorFilter onSubmitFilter={handleSubmitFilter} />
      </div>
      <div className="row">
        {page?.content.map(servidor => (
          <div key={servidor.id} className="col-sm-6 col-md-12">
            <ServidorCrudCard servidor={servidor} onDelete={getServidor} />
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
