import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import HomeView from "./HomeView"
import Navigation from "../components/Navigation";
import OrdersView from './OrdersView'
import ProfileView from "./ProfileView";
import SearchView from "./SearchView";

export const UserType = {
    NONE: 'NONE',
    CLIENT: 'CLIENT',
    EXPERT: 'EXPERT'
};

export default function Root() {
    // TODO get user type from api
    let userType = UserType.CLIENT;

    return (
        <Router>
            <Switch>
                <Route path="/app/orders">
                    <Navigation title={"Expert4Home | Zlecenia"} content={<OrdersView />} userType={userType} />
                </Route>
                <Route path="/app/profile">
                    <Navigation title={"Expert4Home | Profil"} content={<ProfileView />} userType={userType} />
                </Route>
                <Route path="/app/search">
                    <Navigation title={"Expert4Home | Specjaliści"} content={<SearchView />} userType={userType} />
                </Route>
                {/*
                TODO remove/modify navigation
                Added navigation for sample routing, but after making home screen remove it(in project home screen
                not contain nav)!
                */}
                <Route path="/">
                    <Navigation title={"Expert4Home | Specjaliści"} content={<HomeView />} userType={userType} />
                </Route>
            </Switch>
        </Router>
    );
}
