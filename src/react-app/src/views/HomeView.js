import React from "react";
import {UserType} from "./Root";
import Search from "../components/Search";
import ProfileList from "../components/ProfileList";
import { Link } from 'react-router-dom'


class HomeView extends React.Component{

    constructor() {
        super();
        this.state={
            experts:[],
        }
    }
    componentDidMount() {
        fetch('/test/users.json')
            .then(res => res.json())
            .then(
                (data) => {
                    this.setState({
                        experts: [data.experts[0],data.experts[1],data.experts[2]]
                    });
                },
                (error)=>{
                    console.log(error);
                });
    }
    passInput(e){
        e.preventDefault();
        console.log(e.target[0].value);
    }

    render() {
        return (<>
            <Search isInHome={true} onClick={this.passInput} ></Search>
            <ProfileList items={this.state.experts}></ProfileList>

        </>);
    }
}
export default HomeView;