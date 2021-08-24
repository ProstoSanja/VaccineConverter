import './App.scss';
import {Component} from "react";
import Header from "./Screens/Header";
import Footer from "./Screens/Footer";
import Pass from "./Screens/Pass";
import { Modal, Button, Spin } from 'antd';

class App extends Component {

    state = {
        greenPass: null,
        status: "default",
        error: null
    }

    setPassCallback = (item) => {
        this.setState({
            greenPass: item,
            status: "default",
        })
    }

    setStatus = (status, error) => {
        this.setState({
            status: status,
            error: error
        })
    }

    render() {
        const {greenPass, status, error} = this.state;
        return (
            <div className="App">
                {greenPass ? null : <Header/>}
                {greenPass ? null : <Footer setPassCallback={this.setPassCallback} setStatus={this.setStatus}/>}
                {greenPass ? <Pass greenPass={greenPass}/> : null}
                <Modal title="Töötleme teie passi..." visible={status==="loading"} footer={[]} closable={false}>
                    <Spin />
                </Modal>
                <Modal title="Tekkis viga" visible={error != null} footer={[
                    <Button key="back" onClick={() => {this.setState({error: null})}}>
                        Tagasi
                    </Button>
                ]}>
                    <p>{error}</p>
                </Modal>
            </div>
        );
    }


}

export default App;
