import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import Home from "./home"
import Navigation from "./navigation";
import Orders from './orders'
import Profile from "./profile";
import Search from "./search";

export const UserType = {
    NONE: 'NONE',
    CLIENT: 'CLIENT',
    EXPERT: 'EXPERT'
};

export default function App() {
    // TODO get user type from api
    let userType = UserType.CLIENT;

    return (
        <Router>
            <Switch>
                <Route path="/app/orders">
                    <Navigation title={"Expert4Home | Zlecenia"} content={<Orders />}  userType={userType} />
                </Route>
                <Route path="/app/profile">
                    <Navigation title={"Expert4Home | Profil"} content={<Profile />} userType={userType} />
                </Route>
                <Route path="/app/search">
                    <Navigation title={"Expert4Home | Specjaliści"} content={<Search />}  userType={userType} />
                </Route>
                <Route path="/">
                    <Home />
                </Route>
            </Switch>
        </Router>
    );
}
