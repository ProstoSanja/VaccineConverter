import './LanguageSelector.scss';
import {Component} from "react";
import emojiSupport, {DetectionTypes} from 'detect-emoji-support';
import ReactCountryFlag from "react-country-flag"
import {Dropdown, Menu} from 'antd';
import {Context} from "./LanguageWrapper";
import {DownOutlined} from "@ant-design/icons";

class LanguageSelector extends Component {


    constructor(props, context) {
        super(props, context);
        if (emojiSupport(DetectionTypes.FLAGS)) {
            this.flagmappings = {
                "en": "🇬🇧",
                "et": "🇪🇪"
            }
        } else {
            this.flagmappings = {
                "en": <ReactCountryFlag countryCode="GB" svg />,
                "et": <ReactCountryFlag countryCode="EE" svg />,
            }
        }
    }

    render() {
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
