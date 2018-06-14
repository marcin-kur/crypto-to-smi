import {SET_ERROR_MESSAGE, SET_REPORT_RECORDS} from './actionTypes';

export const runReport = (data) => {
    const esc = encodeURIComponent;
    const query = Object.keys(data)
        .map(k => esc(k) + '=' + esc(data[k]))
        .join('&');
    const url = '/api/report?' + query;
    return (dispatch) => {
        fetch(url)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(response => {
                        dispatch({type: SET_REPORT_RECORDS, records: response});
                    })
                } else {
                    response.json().then(response => {
                        dispatch({type: SET_ERROR_MESSAGE, errorMessage: response.message});
                    });
                }
            })
            .catch(error => {
                dispatch({type: SET_ERROR_MESSAGE, errorMessage: error})
            });
    }
};