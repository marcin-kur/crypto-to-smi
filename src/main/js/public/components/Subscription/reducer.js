import {SET_ERROR_MESSAGE, SET_REPORT_RECORDS} from './actionTypes';

const initialState = {};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_REPORT_RECORDS: {
            return {
                records: action.records
            }
        }
        case SET_ERROR_MESSAGE: {
            return {
                errorMessage: action.errorMessage
            }
        }
        default:
            return state;
    }
};

export default reducer;