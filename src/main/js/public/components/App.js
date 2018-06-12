import React, { Component } from 'react';
const App = React.createClass({

  render() {
    return (
      <h1>Hello, {this.props.name}!</h1>
    )
  }
});

export default App;