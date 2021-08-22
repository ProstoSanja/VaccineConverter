import './Footer.scss';
import {Button, Col, Row} from "antd";

function Footer() {
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
                        <Button type="primary" shape="round" size={"large"}>
                            Loo mobiilne pass
                        </Button>
                    </div>
                </Col>
            </Row>
        </div>
    );
}

export default Footer;
