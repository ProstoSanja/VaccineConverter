import './Footer.scss';
import {Button} from "antd";
import {CopyOutlined, ArrowRightOutlined} from '@ant-design/icons';
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
        let formData = new FormData();

        formData.append("file", file);
        fetch('/process', {method: "POST", body: formData})
            .then(response => response.json())
            .then((result) => {
                this.props.setPassCallback(result);
            })
            .catch(error => console.error(error));
    }

    render() {
        return (
            <div className="App-Footer"
                 onDragEnter={this.onDragEnter}
                 onDragOver={this.onDragOver}
                 onDrop={this.onFileDrop}>
                    <p className="footerContent"><CopyOutlined /> Lohistage ome toendit, voi...</p>
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
