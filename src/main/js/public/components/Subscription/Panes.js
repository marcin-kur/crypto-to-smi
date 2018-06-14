import React from 'react';
import Content from './VisibleReport';
import { Tab, Menu, Label, Icon } from 'semantic-ui-react'
import { Loader } from 'semantic-ui-react'

class Panes extends React.Component {

    constructor (props) {
        super(props);
        this.state = {
            sources: [{id:1},{id:2},{id:3},{id:4},{id:5}],
            id:6
        };
        this.addNewSource = this.addNewSource.bind(this);
    }

    addNewSource() {
        console.log("click");
        let sources = this.state.sources;
        let id = this.state.id + 1;
        sources.push({id:id});
        this.setState({sources:sources, id:id});
    }

    render() {
        const panes = this.state.sources.map(source => ({
            menuItem:(
                <Menu.Item key={source.id} onClick={this.handleClick}>
                    {source.id}'   '
                    <Icon name='remove circle' size='small'/>
                </Menu.Item>
            ),
            render: () => <Tab.Pane><Content/></Tab.Pane>
        }));

        panes.push({ menuItem: (
            <Menu.Item key='plus' onClick={this.addNewSource}>
                <Icon name='plus'/>
            </Menu.Item>
        ),
            render: () => <Tab.Pane></Tab.Pane> });

        return (
            <div>
                <Tab panes={panes} />
                <Loader active={false} inline='centered' />
            </div>
        );
    }
}

export default Panes;

// const panes = [
//   { menuItem: (
//       <Menu.Item key='messages' onClick={this.handleClick}>
//       <Icon/>
//         Messages<Label>15</Label>
//       </Menu.Item>
//     ),
//    render: () => <Tab.Pane><Content/></Tab.Pane> },
//   { menuItem: 'Tab 2', render: () => <Tab.Pane><Content/></Tab.Pane> },
//   { menuItem: (
//       <Menu.Item key='plus' onClick={this.handleClick}>
//       <Icon name='plus'/>
//       </Menu.Item>
//     ),
//    render: () => <Tab.Pane><Content/></Tab.Pane> },
//            ];