import './Pass.scss';
import {Component} from "react";
import {Alert, Card, Col, Row, Typography} from 'antd';
import {QrcodeOutlined} from "@ant-design/icons";
import {FormattedMessage} from "react-intl";

const { Title } = Typography;

class Pass extends Component {
    render() {
        var greenPass = this.props.greenPass;
        return (
            <div className="App-Pass">
                <Row>
                    <Col className="header" span={24}>
                        <Title><QrcodeOutlined/> <FormattedMessage id="pass_ready" /></Title>
                    </Col>
                    <Col md={12} span={24}>
                        <div className="column-content">
                            <Card
                                hoverable
                                title={<FormattedMessage id="vaccination_pass" />}
                            >
                                <Row>
                                    <Col span={12}>
                                        <b><FormattedMessage id="pass_details_name" /></b>
                                        <p>{greenPass.firstName + " " + greenPass.lastName}</p>
                                    </Col>
                                    <Col span={12}>
                                        <b className="txtR"><FormattedMessage id="pass_details_dob" /></b>
                                        <p className="txtR">{greenPass.dateOfBirth}</p>
                                    </Col>
                                    <Col span={8}>
                                        <b><FormattedMessage id="pass_details_vaccine" /></b>
                                        <p>{greenPass.vaccineType}</p>
                                    </Col>
                                    <Col span={8}>
                                        <b className="txtM"><FormattedMessage id="pass_details_doses" /></b>
                                        <p className="txtM">{greenPass.dosesAdministered}</p>
                                    </Col>
                                    <Col span={8}>
                                        <b className="txtR"><FormattedMessage id="pass_details_dov" /></b>
                                        <p className="txtR">{greenPass.dateOfPass}</p>
                                    </Col>
                                    <Col span={24}>
                                        <Alert message={<FormattedMessage id="pass_available_in_eu" />} type="success" showIcon/>
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
