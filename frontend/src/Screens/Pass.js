import './Pass.scss';
import {Component} from "react";
import {Button, Col, Row, Card, Alert} from 'antd';
import { DownloadOutlined } from '@ant-design/icons';

class Pass extends Component {
    render() {
        var greenPass = this.props.greenPass;
        return (
            <div className="App-Pass">
                <Row>
                    <Col className="header" span={24}>
                        <h1>Nii lihtne see ongi!</h1>
                    </Col>
                    <Col md={12} sm={24}>
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
                    <Col md={12} sm={24}>
                        <div className="column-content save-buttons">
                            <Button type="primary" shape="round" icon={<DownloadOutlined />} size={"large"} href={greenPass.googlePayLink}>
                                Save to Google pay
                            </Button>
                            <Button type="primary" shape="round" icon={<DownloadOutlined />} size={"large"} href={greenPass.applePayLink}>
                                Save to Apple pay
                            </Button>
                        </div>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Pass;
