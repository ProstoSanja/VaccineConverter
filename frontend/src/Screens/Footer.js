import './Footer.scss';
import {Button} from "antd";
import {QrcodeOutlined, ArrowRightOutlined} from '@ant-design/icons';
import React, {Component} from "react";

class Footer extends Component {

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
        fetch('http://localhost:12345/process', {method: "POST", body: formData})
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
                this.props.setStatus("default", error);
            });
    }

    render() {
        return (
            <div className="App-Footer"
                 onDragEnter={this.onDragEnter}
                 onDragOver={this.onDragOver}
                 onDrop={this.onFileDrop}>
                    <p className="footerContent"><QrcodeOutlined /> Tehke valmis oma PDF tõend ja valige...</p>
                    <div className="footerContent">
                        <input type="file" accept="application/pdf" style={{display: "none"}} ref={this.myRef} onChange={this.onFileSelected}/>
                        <Button type="primary" shape="round" size={"large"} onClick={this.onUploadFileClick} >
                            Alusta
                            <ArrowRightOutlined />
                        </Button>
                    </div>
            </div>
        );
    }
}

export default Footer;
