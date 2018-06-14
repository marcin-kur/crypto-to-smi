import React from 'react';
import {Select, Button, Grid, Loader, Segment, Header} from 'semantic-ui-react'
import ResultTable from './ResultTable'

class Report extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};

        this.changeCrypto = this.changeCrypto.bind(this);
        this.changeStockMarketIndex = this.changeStockMarketIndex.bind(this);
        this.changeStartDate = this.changeStartDate.bind(this);
        this.changeEndDate = this.changeEndDate.bind(this);
        this.changeInterval = this.changeInterval.bind(this);
        this.run = this.run.bind(this);
    }

    changeCrypto(event, data) {
        this.setState({
            crypto: data.value
        });
    };

    changeStockMarketIndex(event, data) {
        this.setState({
            stockIndex: data.value
        });
    };

    changeStartDate(event) {
        this.setState({
            startDate: event.target.value
        });
    };

    changeEndDate(event) {
        this.setState({
            endDate: event.target.value
        });
    };

    changeInterval(event, data) {
        this.setState({
            interval: data.value
        });
    };

    run() {
        this.props.run(this.state);
        console.log(this.state);
    };

    render() {
        const cryptoOptions = [
            {key: 'Bitcoin', value: 'Bitcoin', text: 'Bitcoin'},
            {key: 'Blackcoin', value: 'Blackcoin', text: 'Blackcoin'},
            {key: 'Ethereum', value: 'Ethereum', text: 'Ethereum'},
            {key: 'Monero', value: 'Monero', text: 'Monero'},
            {key: 'Reddcoin', value: 'Reddcoin', text: 'Reddcoin'}
        ];
        const stockMarketIndexOptions = [
            {key: 'WIG20', value: 'WIG20', text: 'WIG20'},
            {key: 'mWIG40', value: 'mWIG40', text: 'mWIG40'}
        ];
        const intervalOptions = [
            {key: 1, value: 1, text: 1},
            {key: 3, value: 3, text: 3},
            {key: 6, value: 6, text: 6},
            {key: 12, value: 12, text: 12},
            {key: 24, value: 24, text: 24}
        ];
        const result = !!this.props.records ? <ResultTable records={this.props.records}/> : null;
        const errorMessge = !!this.props.errorMessage ?
            (<Segment>
                <Header as='h2' textAlign='center'>
                    {this.props.errorMessage}
                </Header>
            </Segment>) : null;
        return (
            <Grid columns='equal'>
                <Grid.Row stretched>
                    <Grid.Column>
                        <Select placeholder='Select Cryptocurrency' options={cryptoOptions}
                                onChange={this.changeCrypto}/>
                    </Grid.Column>
                    <Grid.Column>
                        <Select placeholder='Select Stock Market Index' options={stockMarketIndexOptions}
                                onChange={this.changeStockMarketIndex}/>
                    </Grid.Column>
                    <Grid.Column>
                        <div className="ui calendar">
                            <div className="ui input">
                                <input type="date" placeholder="Date/Time" onChange={this.changeStartDate}/>
                            </div>
                        </div>
                    </Grid.Column>
                    <Grid.Column>
                        <div className="ui calendar">
                            <div className="ui input">
                                <input type="date" placeholder="Date/Time" onChange={this.changeEndDate}/>
                            </div>
                        </div>
                    </Grid.Column>
                    <Grid.Column>
                        <Select placeholder='Select Interval (hours)' options={intervalOptions}
                                onChange={this.changeInterval}/>
                    </Grid.Column>
                    <Grid.Column>
                        <Button positive onClick={this.run}>Run</Button>
                    </Grid.Column>x
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column>
                        {result}
                        {errorMessge}
                        <Loader/>
                    </Grid.Column>
                </Grid.Row>
            </Grid>
        );
    }
}

export default Report;