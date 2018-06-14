import React from 'react';
import ReactDOM from 'react-dom'
import {Provider} from 'react-redux'
import {applyMiddleware, compose, createStore} from 'redux'
import {createBrowserHistory} from 'history'
import {routerMiddleware, connectRouter} from 'connected-react-router'
import App from './components/App';
import rootReducer from './reducers'
import thunk from 'redux-thunk'

import 'semantic-ui-css/semantic.min.css';

const history = createBrowserHistory();
const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const store = createStore(
    connectRouter(history)(rootReducer),
    {},
    composeEnhancer(
        applyMiddleware(
            routerMiddleware(history),
            thunk
        ),
    ),
);

const render = () => {
    ReactDOM.render(
        <Provider store={store}>
            <App history={history}/>
        </Provider>,
        document.getElementById('root')
    )
};

render();