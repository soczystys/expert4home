import React from "react";
import {UserType} from "./Root";
import Search from "../components/Search";
import ProfileList from "../components/ProfileList";

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
    render() {
        return (<>
            <Search></Search>
            <ProfileList items={this.state.experts}></ProfileList>

        </>);
    }
}
export default HomeView;