import './Header.scss';
import { Row, Col, Typography } from 'antd';

const { Title, Text } = Typography;

function Header() {
    return (
        <div className="App-Header">
            <Row>
                <Col flex={1} span={12}>
                    <div className="column-content">
                        <Title>Laadi oma COVID-19 pass enda telefoni</Title>
                        <Text>Kanna pass enda telefonis ja säästa aega proovides seda leida, avada ja näidata nii et teised seda ka näeksid!</Text>
                        <br/><br/>
                        <Text strong>Lubatud dokumendid:</Text>
                        <ul>
                            <li>Eesti COVID-19 Vaktsineerimispass</li>
                        </ul>
                        <Text strong>Varsti tulevad:</Text>
                        <ul>
                            <li>Eesti COVID-19 Testitõend</li>
                            <li>Eesti COVID-19 Läbipõdemistõend</li>
                            <li>Norra COVID-19 Vaktsineerimispass</li>
                        </ul>
                    </div>
                </Col>
                <Col span={12}>
                    <div className="column-content img" style={{backgroundImage: 'url("/cover.png")'}}>

                    </div>
                </Col>
            </Row>
        </div>
    );
}

export default Header;
