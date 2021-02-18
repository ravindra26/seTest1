import React, { Component } from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import appStyles from './App.module.css';
import classNames from 'classnames';
import axios from 'axios';

class App extends Component {

  state = {
    cardNumber: 0,
    limit: 0,
    offset: 0,
    statData:"",
    verifyData:""
  }

  verifyCard = () => {
    axios({
      method: 'get',
      url: 'http://localhost:8080/Service1/verify/' + this.state.cardNumber,
    })
      .then(response => {
        //handle success
        let data = JSON.stringify(response.data,null,2);
        this.setState({verifyData:data});

      })
      .catch(function (response) {
        //handle error
        console.log(response);
      });
  }

  setCardNumber = (event) => {
    this.setState({ cardNumber: event.target.value });
    console.log(event.target.value);
  }

  setLimit = (event) => {
    this.setState({ limit: event.target.value })
  }

  setOffset = (event) => {
    this.setState({ offset: event.target.value })
  }

  getHits = () => {
    axios({
      method: 'get',
      url: 'http://localhost:8080/Service1/stats?limit=' + this.state.limit + "&offset=" + this.state.offset,
    })
      .then(response => {
        //handle success
        console.log(response.data);
        let data = JSON.stringify(response.data,null,2);
        console.log(data);
        this.setState({statData:data});
      })
      .catch(function (response) {
        //handle error
        console.log(response);
      });
  }

  hitResults = null;


  render() {
    return (
      <div>
        <Row>
          <Col xs={4}></Col>
          <Col xs={4} className={classNames(appStyles["bg-color-blue"], appStyles["color-white"], appStyles["app-text-center"])}><label className={appStyles["text-size-large"]}>Card Verifier</label></Col>
          <Col xs={4}></Col>
        </Row>
        <Row className={appStyles["paddingTop1p"]}>
          <Col xs={1}></Col>
          <Col xs={5}>
            <Row className={appStyles["paddingTop1p"]}>
              <Col xs={1}></Col>
              <Col xs={4}>Card Number:</Col>
              <Col xs={5}><input type="text" onChange={this.setCardNumber}></input></Col>
              <Col xs={2}></Col>
            </Row>
          </Col>
          <Col xs={5}>
            <Row className={appStyles["paddingTop1p"]}>
              <Col xs={1}></Col>
              <Col xs={2}>Limit:</Col>
              <Col xs={3}><input type="text" onChange={this.setLimit}></input></Col>
              <Col xs={3} className={classNames(appStyles["paddingLeft"], appStyles["app-text-center"])}>Offset:</Col>
              <Col xs={2}><input type="text" onChange={this.setOffset}></input></Col>
              <Col xs={1}></Col>
            </Row>
          </Col>
          <Col xs={1}></Col>
        </Row>
        <Row className={appStyles["paddingTop1p"]}>
          <Col xs={1}></Col>
          <Col xs={5}><pre>{this.state.verifyData}</pre></Col>
          <Col xs={5}><pre>{this.state.statData}</pre></Col>
          <Col xs={1}></Col>
        </Row>
        <Row className={appStyles["paddingTop1p"]}>
          <Col xs={2}></Col>
          <Col xs={3}><Button variant="secondary" onClick={this.verifyCard} className={classNames(appStyles["width-100p"])}>Verify Card</Button></Col>
          <Col xs={2}></Col>
          <Col xs={3}><Button variant="secondary" onClick={this.getHits} className={classNames(appStyles["width-100p"])}>Verify Card</Button></Col>
          <Col xs={2}></Col>
        </Row>
      </div>
    );
  }
}

export default App;
