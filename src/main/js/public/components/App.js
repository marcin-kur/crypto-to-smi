import React from 'react';
import Report from './Subscription/VisibleReport'
import {Tab, Menu, Label} from 'semantic-ui-react'
import {Loader} from 'semantic-ui-react'

class App extends React.Component {
    render() {
        return (
            <div>
                <Report/>
            </div>
        );
    }
}

export default App;