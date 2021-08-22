import './Header.scss';
import { Row, Col, Typography } from 'antd';

const { Title, Paragraph, Text, Link } = Typography;

function Header() {
    return (
        <div className="App-Header">
            <Row>
                <Col flex={1} span={12}>
                    <div className="column-content">
                        <Title>Laadi oma COVID passi enda telefoni</Title>
                        <Text>Kanna passi enda telefonis ja lopeta aega raiskamist, proovides seda leida, avada ja naidata nii et teised ka seda naeksid!</Text>
                        <br/><br/>
                        <Text strong>Lubatud dokumendid:</Text>
                        <ul>
                            <li>Eesti COVID-19 pass</li>
                            <li>Norra COVID-19 pass</li>
                        </ul>
                    </div>
                </Col>
                <Col span={12}>
                    <div className="column-content">
                        <video/>
                    </div>
                </Col>
            </Row>
        </div>
    );
}

export default Header;
