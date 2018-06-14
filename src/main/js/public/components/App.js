import React from 'react';
import Report from './Subscription/VisibleReport'
import {Segment} from 'semantic-ui-react'

class App extends React.Component {
    render() {
        return (
            <Segment>
                <Report/>
            </Segment>
        );
    }
}

export default App;