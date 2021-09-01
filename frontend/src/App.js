import './App.scss';
import {Component} from "react";
import Header from "./Screens/Header";
import Pass from "./Screens/Pass";
import {Button, Modal, Spin} from 'antd';
import {FormattedMessage} from "react-intl";

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
                {greenPass ? <Pass greenPass={greenPass}/> : <Header setPassCallback={this.setPassCallback} setStatus={this.setStatus}/>}
                <Modal title={<FormattedMessage id="processing_pass" />} visible={status==="loading"} footer={[]} closable={false}>
                    <Spin />
                </Modal>
                <Modal title={<FormattedMessage id="error_occurred" />} visible={error != null} closable={false} footer={[
                    <Button key="back" onClick={() => {this.setState({error: null})}}>
                        <FormattedMessage id="back" />
                    </Button>
                ]}>
                    <p>{error}</p>
                </Modal>
            </div>
        );
    }


}

export default App;
