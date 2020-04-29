import React from "react";
import Search from "../components/Search"
import ProfileList from "../components/ProfileList";

class SearchView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            experts : [],
            expertsToView:[],
        }
    }
    filterItems = (e)=>{
        e.preventDefault();
        const input = e.target[0].value;
        this.setState((prevState)=>{
           const expertsToView = prevState.experts.filter((expert)=>{
                const keys = Object.keys(expert);
                for(const key of keys){
                    if(expert[key].toLowerCase().includes(input.toLowerCase())){
                        return true;
                    }
                }
           });
           console.log(expertsToView);
           return ({
               experts:prevState.experts,
               expertsToView:expertsToView
           })
        })
      
        }

    componentDidMount() {
        fetch('/test/users.json')
            .then(res => res.json())
            .then(
                (data) => {
                this.setState({
                    experts: data.experts,
                    expertsToView: data.experts
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
            <ProfileList items={this.state.expertsToView}></ProfileList>
        </>)
    }
}
export default SearchView