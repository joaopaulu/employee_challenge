import PrivateRoute from 'components/PrivateRoute';
import {Switch} from 'react-router-dom';
import NavbarAdmin from './components/NavbarAdmin';
import './styles.css';
import User from './User';
import Categoria from "./Categoria";
import Servidor from "./Servidor";

const Admin = () => {
    return (
        <div className="admin-container">
            <NavbarAdmin/>
            <div className="admin-content">
                <Switch>
                    <PrivateRoute path="/admin/servidor">
                        <Servidor/>
                    </PrivateRoute>
                    <PrivateRoute path="/admin/categoria">
                        <Categoria/>
                    </PrivateRoute>
                    <PrivateRoute path="/admin/users">
                        <User/>
                    </PrivateRoute>
                </Switch>
            </div>
        </div>
    );
};

export default Admin;
