import { Route, Switch } from 'react-router-dom';
import Form from './Form';
import List from './List';

const Servidor = () => {
  return (
    <Switch>
      <Route path="/admin/servidor" exact>
        <List />
      </Route>
      <Route path="/admin/servidor/:servidorId">
        <Form />
      </Route>
    </Switch>
  );
};

export default Servidor;
