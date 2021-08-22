import './Pass.scss';
import {Component} from "react";
import {Button, Col, Radio, Row, Card} from 'antd';
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
                                footer
                            >
                                <Row>
                                    <Col span={12}>
                                        <b>Nimi:</b>
                                        <p>Aleksandr Tsernoh</p>
                                    </Col>
                                    <Col span={12}>
                                        <b className="txtR">Kuupaev:</b>
                                        <p className="txtR">2021-04-30</p>
                                    </Col>
                                    <Col span={12}>
                                        <b>Vaktsiin:</b>
                                        <p>Comirnaty</p>
                                    </Col>
                                    <Col span={12}>
                                        <b className="txtR">Doose:</b>
                                        <p className="txtR">2/2</p>
                                    </Col>
                                    <Col span={24}>
                                        <p className="footer">Kehtib Eestis ja teistes Euroopa riikides!</p>
                                    </Col>
                                </Row>
                            </Card>
                        </div>
                    </Col>
                    <Col md={12} sm={24}>
                        <div className="column-content save-buttons">
                            <Button type="primary" shape="round" icon={<DownloadOutlined />} size={"large"}>
                                Save to Google pay
                            </Button>
                            <Button type="primary" shape="round" icon={<DownloadOutlined />} size={"large"}>
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
