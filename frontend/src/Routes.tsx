import Footer from 'components/Footer';
import Navbar from 'components/NavBar';
import history from 'core/utils/history';
import Admin from 'pages/Admin';
import Auth from 'pages/Admin/Auth';
import { Redirect, Route, Router, Switch } from 'react-router-dom';

const Routes = () => {
  return (
    <>
      <Router history={history}>
        <Navbar />
        <Switch>
          <Redirect from="/employee-admin" to="/admin/auth/login" exact />
          <Redirect from="/admin/auth" to="/admin/auth/login" exact />
          <Route path="/admin/auth">
            <Auth />
          </Route>
          <Redirect from="/admin" to="/admin/categoria" exact />
          <Route path="/admin">
            <Admin />
          </Route>
          <Redirect from="/" to="/admin/categoria" exact />
          <Route path="/" exact>
            <Admin />
          </Route>
        </Switch>
        <Footer />
      </Router>
    </>
  );
};

export default Routes;
