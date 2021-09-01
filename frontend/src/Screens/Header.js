import './Header.scss';
import {Button, Col, Row, Typography} from 'antd';
import {ArrowRightOutlined} from "@ant-design/icons";
import React, {Component} from "react";
import {FormattedMessage} from "react-intl";
import LanguageSelector from "../Components/LanguageSelector";

const { Title, Text } = Typography;

class Header extends Component {

    constructor(props) {
        super(props);
        this.myRef = React.createRef();
    }

    onDragOver = (e) => {
        e.stopPropagation();
        e.preventDefault();
    }

    onDragEnter = (e) => {
        e.stopPropagation();
        e.preventDefault();
    }

    onFileDrop = (e) => {
        e.stopPropagation();
        e.preventDefault();
        if (e.dataTransfer?.items?.length !== 1) {
            this.props.setStatus("default", "Could not find covid pass file");
            return;
        }
        var file = e.dataTransfer.items[0].getAsFile();
        this.uploadToServer(file);
    }

    onUploadFileClick = () => {
        this.myRef.current.click();
    }

    onFileSelected = (e) => {
        if (e.target?.files?.length !== 1) {
            this.props.setStatus("default", "Could not find covid pass file");
            this.myRef.current.value = "";
            return;
        }
        this.uploadToServer(e.target.files[0]);
    }

    uploadToServer = (file) => {
        this.props.setStatus("loading", null);
        let formData = new FormData();

        formData.append("file", file);
        fetch('/process', {method: "POST", body: formData})
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else if (response.status === 500) {
                    return response.json()
                }
                throw new Error("Received broken response");
            })
            .then((result) => {
                this.myRef.current.value = "";
                if (result.message) {
                    this.props.setStatus("default", result.message);
                    return;
                }
                this.props.setPassCallback(result);
            })
            .catch(error => {
                this.myRef.current.value = "";
                this.props.setStatus("default", error.message);
            });
    }

    render() {
        return (
            <div className="App-Header">
                <Row>
                    <Col md={12} span={24}>
                        <div className="column-content col1">
                            <div className="titleHolder">
                                <Title><FormattedMessage id="header_title" /></Title>
                                <LanguageSelector/>
                            </div>
                            <Text><FormattedMessage id="header_subtitle" /></Text>
                            <br/>
                            <Text strong><FormattedMessage id="allowed_documents" /></Text>
                            <ul>
                                <li><FormattedMessage id="covid_pass_name_1" /></li>
                                <li><FormattedMessage id="covid_pass_name_2" /></li>
                                <li><FormattedMessage id="covid_pass_name_3" /></li>
                            </ul>
                            <Text strong><FormattedMessage id="coming_soon_documents" /></Text>
                            <ul>
                                <li><FormattedMessage id="covid_pass_test" /></li>
                                <li><FormattedMessage id="covid_pass_beensick" /></li>
                            </ul>
                            <div className="fg"/>
                            <Text type="secondary" className="footText">
                                <FormattedMessage id="author_is" /> <a href="https://github.com/ProstoSanja">Aleksandr Tsernoh</a>.<br/>
                                <FormattedMessage id="not_saving_data" />
                            </Text>
                        </div>
                    </Col>
                    <Col md={12} span={24}>
                        <div className="column-content col2">
                            <div className="buttonHolder">
                                <LanguageSelector/>
                                <input type="file" accept="application/pdf, image/png, image/jpeg" style={{display: "none"}} ref={this.myRef} onChange={this.onFileSelected}/>
                                <Button type="primary" shape="round" size={"large"}
                                        onDragEnter={this.onDragEnter}
                                        onDragOver={this.onDragOver}
                                        onDrop={this.onFileDrop}
                                        onClick={this.onUploadFileClick}
                                >
                                    <FormattedMessage id="begin" /> <ArrowRightOutlined/>
                                </Button>
                            </div>
                            <div className="img" style={{backgroundImage: 'url("/cover.png")'}}>

                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Header;
