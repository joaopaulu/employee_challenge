import { Route, Switch } from 'react-router-dom';
import Login from './Login';
import './styles.css';

const Auth = () => {
  return (
    <div className="auth-container">
      <div className="auth-form-container">
        <Switch>
          <Route path="/admin/auth/login">
            <Login />
          </Route>
        </Switch>
      </div>
    </div>
  );
};

export default Auth;
