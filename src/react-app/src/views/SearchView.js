import React from "react";
import Search from "../components/Search"
import ProfileList from "../components/ProfileList";

class SearchView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            experts : []
        }
    }
    filterItems = (e)=>{
        e.preventDefault();
        console.log("jello")
    }
    componentDidMount() {
        fetch('/test/users.json')
            .then(res => res.json())
            .then(
                (data) => {
                this.setState({
                    experts: data.experts,
                });
            },
                (error)=>{
                    console.log(error);
                });
    }
    render(){
        return(
        <>
            <Search onClick={this.filterItems}></Search>
            <ProfileList items={this.state.experts}></ProfileList>
        </>)
    }
}
export default SearchView