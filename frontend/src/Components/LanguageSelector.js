import './LanguageSelector.scss';
import {Component} from "react";
import {Dropdown, Menu} from 'antd';
import {Context} from "./LanguageWrapper";
import {DownOutlined} from "@ant-design/icons";

class LanguageSelector extends Component {

    flagmappings = {
        "en": "🇬🇧",
        "et": "🇪🇪"
    }

    render() {
        console.log(this.context);


        const menu =
            <Menu onClick={this.context.selectLanguage}>
                <Menu.Item key="en">{this.flagmappings.en}</Menu.Item>
                <Menu.Item key="et">{this.flagmappings.et}</Menu.Item>
            </Menu>;

        return (
            <Dropdown overlay={menu}>
                <div><DownOutlined style={{ fontSize: '16px', verticalAlign: "0.125em"}}/>{this.flagmappings[this.context.locale]}</div>
            </Dropdown>
        );
    }
}

LanguageSelector.contextType = Context;

export default LanguageSelector;
