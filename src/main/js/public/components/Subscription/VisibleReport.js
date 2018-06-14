import React from 'react';
import {connect} from 'react-redux';
import Report from './Report';
import {runReport} from './actions';

function mapStateToProps(state) {
    return {
        records: state.report.records,
        errorMessage: state.report.errorMessage
    };
}

function mapDispatchToProps(dispatch) {
    return {
        run: (data) => dispatch(runReport(data)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Report)