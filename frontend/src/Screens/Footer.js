import './Footer.scss';
import {Button, Col, Row, Upload, message} from "antd";
import { InboxOutlined } from '@ant-design/icons';

const { Dragger } = Upload;

function Footer() {

    const uploadProps = {
        multiple: false,
        accept: "pdf"
    }

    return (
        <div className="App-Footer">
            <Row>
                <Col md={12} sm={24}>
                    <div className="column-content">
                        <h2>Laadi ulesse oma COVID toend, ja saa mobiilse passi sekunditega</h2>
                    </div>
                </Col>
                <Col md={12} sm={24}>
                    <div className="column-content">
                        <Dragger {...uploadProps}>
                            <p className="ant-upload-drag-icon">
                                <InboxOutlined />
                            </p>
                            <p className="ant-upload-text">Vajutage et valida faili laadimiseks</p>
                        </Dragger>
                    </div>
                </Col>
            </Row>
        </div>
    );
}

export default Footer;
