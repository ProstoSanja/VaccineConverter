import './App.css';
import {Component} from "react";
import Header from "./Screens/Header";
import Footer from "./Screens/Footer";
import GreenPass from "./classes/GreenPass";
import Pass from "./Screens/Pass";

class App extends Component {

    state = {
        greenPass: null//2//new GreenPass()
    }

    setPassCallback = (item) => {
        this.setState({
            greenPass: item,
        })
    }

    render() {
        const greenPass = this.state.greenPass;
        return (
            <div className="App">
                {greenPass ? null : <Header/>}
                {greenPass ? null : <Footer setPassCallback={this.setPassCallback}/>}
                {greenPass ? <Pass greenPass={greenPass}/> : null}
            </div>
        );
    }


}

export default App;
