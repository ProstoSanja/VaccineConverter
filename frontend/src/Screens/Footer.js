import './Footer.scss';
import {Button, Col, Row, Upload, message} from "antd";
import {CopyOutlined, DownloadOutlined, ArrowRightOutlined} from '@ant-design/icons';
import {Component} from "react";

const { Dragger } = Upload;

class Footer extends Component {

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

    uploadToServer = (file) => {
        let formData = new FormData();

        formData.append("file", file);
        fetch('http://localhost:12345/process', {method: "POST", body: formData})
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
                        <Button type="primary" shape="round" size={"large"}>
                            Alusta
                            <ArrowRightOutlined />
                        </Button>
                    </div>
            </div>
        );
    }
}

export default Footer;
