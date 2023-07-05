import { AxiosRequestConfig } from 'axios';
import Pagination from 'components/Pagination';
import { User } from 'core/types/user';
import { SpringPage } from 'core/types/vendor/spring';
import { requestBackend } from 'core/utils/requests';
import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Card from '../Card';
import './styles.css';

type ControlComponentsData = {
  activePage: number;
};

const List = () => {
  const [page, setPage] = useState<SpringPage<User>>();

  const [controlComponentsData, setControlComponentsData] =
    useState<ControlComponentsData>({ activePage: 0 });

  const handlePageChange = (pageNumber: number) => {
    setControlComponentsData({
      activePage: pageNumber,
    });
  };

  const getUsers = useCallback(() => {
    const config: AxiosRequestConfig = {
      method: 'GET',
      url: '/users',
      params: {
        page: controlComponentsData.activePage,
        size: 6,
      },
    };

    requestBackend(config).then(response => {
      setPage(response.data);
    });
  }, [controlComponentsData]);

  useEffect(() => {
    getUsers();
  }, [getUsers]);

  return (
    <div className="product-crud-container">
      <div className="product-crud-bar-container">
        <Link to="/admin/users/create">
          <button className="btn btn-primary text-white btn-crud-add">
            ADICIONAR
          </button>
        </Link>
      </div>
      <div className="row">
        {page?.content.map(user => (
          <div key={user.id} className="col-sm-6 col-md-12">
            <Card user={user} onDelete={getUsers} />
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
