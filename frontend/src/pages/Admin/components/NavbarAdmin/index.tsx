import { NavLink } from 'react-router-dom';
import './styles.css';

const Navbar = () => {
  return (
    <nav className="admin-nav-container">
      <ul className="admin-nav-items-container">
        <li>
          <NavLink to="/admin/servidor" className="admin-nav-item">
            <p>Servidores</p>
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/categoria" className="admin-nav-item">
            <p>Categorias</p>
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/users" className="admin-nav-item">
            <p>Usuários</p>
          </NavLink>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
