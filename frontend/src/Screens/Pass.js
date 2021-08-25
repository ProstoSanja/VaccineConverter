import './Pass.scss';
import {Component} from "react";
import {Col, Row, Card, Alert, Typography} from 'antd';
import {QrcodeOutlined} from "@ant-design/icons";

const { Title } = Typography;

class Pass extends Component {
    render() {
        var greenPass = this.props.greenPass;
        return (
            <div className="App-Pass">
                <Row>
                    <Col className="header" span={24}>
                        <Title><QrcodeOutlined/> Pass on valmis!</Title>
                    </Col>
                    <Col md={12} span={24}>
                        <div className="column-content">
                            <Card
                                hoverable
                                title="COVID-19 Immuniseerimispass"
                            >
                                <Row>
                                    <Col span={12}>
                                        <b>NIMI</b>
                                        <p>{greenPass.firstName + " " + greenPass.lastName}</p>
                                    </Col>
                                    <Col span={12}>
                                        <b className="txtR">SUNNIPAEV</b>
                                        <p className="txtR">{greenPass.dateOfBirth}</p>
                                    </Col>
                                    <Col span={8}>
                                        <b>VAKTSIIN</b>
                                        <p>{greenPass.vaccineType}</p>
                                    </Col>
                                    <Col span={8}>
                                        <b className="txtM">DOOSI</b>
                                        <p className="txtM">{greenPass.dosesAdministered}</p>
                                    </Col>
                                    <Col span={8}>
                                        <b className="txtR">KUUPAEV</b>
                                        <p className="txtR">{greenPass.dateOfPass}</p>
                                    </Col>
                                    <Col span={24}>
                                        <Alert message="Kehtib Eestis ja teistes Euroopa riikides!" type="success" showIcon/>
                                    </Col>
                                </Row>
                            </Card>
                        </div>
                    </Col>
                    <Col md={12} span={24}>
                        <div className="column-content save-buttons">
                            <a style={{backgroundImage:'url("save_to_phone.svg")'}} className='payBut gPay' href={greenPass.googlePayLink} target={"_blank"} rel="noreferrer" >

                            </a>
                            <a style={{backgroundImage:'url("save_to_apple.svg")'}} className='payBut aPay' href={greenPass.applePayLink} target={"_blank"} rel="noreferrer" >

                            </a>
                        </div>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Pass;
