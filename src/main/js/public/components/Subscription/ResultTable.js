import React from 'react'
import {Table} from 'semantic-ui-react'

class ResultTable extends React.Component {
    render() {
        const rows = this.props.records.map((record, index) =>
            <Table.Row key={index}>
                <Table.Cell>{record.startDateTime}</Table.Cell>
                <Table.Cell>{record.endDateTime}</Table.Cell>
                <Table.Cell>{record.exchangeRate}</Table.Cell>
                <Table.Cell>{record.cryptocurrencyAverage}</Table.Cell>
                <Table.Cell>{record.stockIndexAverage}</Table.Cell>
                <Table.Cell>{record.calculatedValue}</Table.Cell>
            </Table.Row>);

        return (
            <Table celled>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell>Start Date</Table.HeaderCell>
                        <Table.HeaderCell>End Date</Table.HeaderCell>
                        <Table.HeaderCell>Exchange Rate (USD/PLN)</Table.HeaderCell>
                        <Table.HeaderCell>Cryptocurrency Value (USD)</Table.HeaderCell>
                        <Table.HeaderCell>Stock Index Value (PLN)</Table.HeaderCell>
                        <Table.HeaderCell>Cryptocurrency to Stock Index</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                    {rows}
                </Table.Body>
            </Table>
        );
    }
}

export default ResultTable;