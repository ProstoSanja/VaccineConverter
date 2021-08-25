import './Header.scss';
import {Row, Col, Typography, Button} from 'antd';
import {ArrowRightOutlined} from "@ant-design/icons";
import React, {Component} from "react";

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
            alert("Multiple files uploaded");
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
            alert("Multiple files uploaded");
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
                throw response;
            })
            .then((result) => {
                if (result.message) {
                    this.props.setStatus("default", result.message);
                    return;
                }
                this.props.setPassCallback(result);
            })
            .catch(error => {
                this.props.setStatus("default", error.message);
            });
    }

    render() {
        return (
            <div className="App-Header">
                <Row>
                    <Col md={12} span={24}>
                        <div className="column-content col1">
                            <Title>Laadi oma COVID-19 pass enda telefoni</Title>
                            <Text>Kanna pass enda telefonis ja säästa aega proovides seda leida, avada ja näidata nii et
                                teised seda ka näeksid!</Text>
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
                    <Col md={12} span={24}>
                        <div className="column-content col2">
                            <div className="buttonHolder">
                                <input type="file" accept="application/pdf" style={{display: "none"}} ref={this.myRef} onChange={this.onFileSelected}/>
                                <Button type="primary" shape="round" size={"large"}
                                        onDragEnter={this.onDragEnter}
                                        onDragOver={this.onDragOver}
                                        onDrop={this.onFileDrop}
                                        onClick={this.onUploadFileClick}
                                >
                                    Alusta
                                    <ArrowRightOutlined/>
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
