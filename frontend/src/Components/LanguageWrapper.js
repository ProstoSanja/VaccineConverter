import React, {useState} from 'react';
import {IntlProvider} from 'react-intl';
import English from './../lang/en.json';
import Estonian from './../lang/et.json';

export const Context = React.createContext();

let local = navigator.language.toLowerCase();
let lang;

if (local && (local.indexOf('et') !== -1 || local.indexOf('ee') !== -1)) {
    local = "et";
    lang = Estonian;
} else {
    local = "en";
    lang = English;
}

const LanguageWrapper = (props) => {
    const [locale, setLocale] = useState(local);
    const [messages, setMessages] = useState(lang);

    function selectLanguage(newLang) {
        console.log(newLang);
        newLang = newLang.key
        setLocale(newLang);

        if (newLang.indexOf('et') !== -1 || newLang.indexOf('ee') !== -1) {
            setMessages(Estonian);
        } else {
            setMessages(English);
        }
    }

    return (
        <Context.Provider value = {{locale, selectLanguage}}>
            <IntlProvider messages={messages} locale={locale}>
                {props.children}
            </IntlProvider>
        </Context.Provider>
    );
}

export default LanguageWrapper;
