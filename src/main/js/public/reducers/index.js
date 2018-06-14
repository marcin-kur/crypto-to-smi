import {combineReducers} from 'redux';
import reportReducer from '../components/Subscription/reducer'

const rootReducer = combineReducers({
    report: reportReducer
});

export default rootReducer;