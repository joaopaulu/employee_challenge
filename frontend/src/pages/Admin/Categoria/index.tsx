import { Route, Switch } from 'react-router-dom';
import Form from './Form';
import List from './List';

const Categoria = () => {
  return (
    <Switch>
      <Route path="/admin/categoria" exact>
        <List />
      </Route>
      <Route path="/admin/categoria/:categoriaId">
        <Form />
      </Route>
    </Switch>
  );
};

export default Categoria;
