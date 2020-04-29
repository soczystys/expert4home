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

export class Root extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userType: UserType.NONE
        }
    }

    componentDidMount() {
        if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
            this.setState(prevState => {
                return {
                    ...prevState,
                    userType: UserType.CLIENT
                }
            })
        } else {
            fetch('/api/users/current')
                .then(res => { res.text().then(body => {
                    fetch("/api/users/secure/" + body)
                        .then(res => res.json())
                        .then((data) => {
                            this.setState(prevState => {
                                return {
                                    ...prevState,
                                    userType: (data.expertMode === true ? UserType.EXPERT : UserType.CLIENT)
                                }
                            })
                        })
                    })
                })
                .catch(console.log)
        }
    }

    render() {
        return (
            <Router>
                <Switch>
                    <Route path="/app/orders">
                        <Navigation title={"Expert4Home | Zlecenia"} content={<OrdersView userType={this.state.userType} />} userType={this.state.userType} />
                    </Route>
                    <Route path="/app/profile">
                        <Navigation title={"Expert4Home | Profil"} content={<ProfileView userType={this.state.userType} />} userType={this.state.userType} />
                    </Route>
                    <Route path="/app/search">
                        <Navigation title={"Expert4Home | Specjaliści"} content={<SearchView userType={this.state.userType} />} userType={this.state.userType} />
                    </Route>
                    {/*
                    TODO remove/modify navigation
                    Added navigation for sample routing, but after making home screen remove it(in project home screen
                    not contain nav)!
                    */}
                    <Route path="/">
                        <Navigation title={"Expert4Home | Specjaliści"} content={<HomeView />} userType={this.state.userType} />
                    </Route>
                </Switch>
            </Router>
        );
    }
}
