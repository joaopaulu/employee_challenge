import { hasAnyRoles, isAuthenticated, Role } from 'core/utils/auth';
import { Redirect, Route } from 'react-router-dom';

type Props = {
  children: React.ReactNode;
  path: string;
  roles?: Role[];
};

const PrivateRoute = ({ children, path, roles = [] }: Props) => {
  return (
    <Route
      path={path}
      render={({ location }) =>
        !isAuthenticated() ? (
          <Redirect
            to={{
              pathname: '/admin/auth/login',
              state: { from: location },
            }}
          />
        ) : !hasAnyRoles(roles) ? (
          <Redirect to="/admin/categoria" />
        ) : (
          children
        )
      }
    />
  );
};

export default PrivateRoute;
