import './Pass.scss';
import {Component} from "react";
import { Button, Radio } from 'antd';
import { DownloadOutlined } from '@ant-design/icons';

class Pass extends Component {
    render() {
        var greenPass = this.props.greenPass;
        return (
            <div className="App-Pass">
                <div className="left">
                    <div className="Card">
                        <p>Aleksandr</p>
                        <p>Tsernoh</p>
                        <p>COVID-19</p>
                    </div>
                </div>
                <div className="right">
                    <Button type="primary" shape="round" icon={<DownloadOutlined />} size={"large"}>
                        Save to Google pay
                    </Button>
                    <Button type="primary" shape="round" icon={<DownloadOutlined />} size={"large"}>
                        Save to Apple pay
                    </Button>
                </div>
            </div>
        );
    }
}

export default Pass;
