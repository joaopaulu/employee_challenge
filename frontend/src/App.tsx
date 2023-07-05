import {AuthContext, AuthContextData} from 'AuthContext';
import {useState} from 'react';
import Routes from 'Routes';
import './app.css';
import './core/assets/styles/custom.scss';
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer} from 'react-toastify';

const App = () => {
    const [authContextData, setAuthContextData] = useState<AuthContextData>({
        authenticated: false,
    });
    return (
        <AuthContext.Provider value={{authContextData, setAuthContextData}}>
            <Routes/>
            <ToastContainer/>
        </AuthContext.Provider>
    );
};

export default App;
