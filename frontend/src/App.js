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

    render() {
        const greenPass = this.state.greenPass;
        return (
            <div className="App">
                {greenPass ? null : <Header/>}
                {greenPass ? null : <Footer/>}
                {greenPass ? <Pass greenPass={greenPass}/> : null}
            </div>
        );
    }


}

export default App;
