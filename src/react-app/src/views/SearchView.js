import React from "react";
import Search from "../components/Search"
import ProfileList from "../components/ProfileList";
import { Pagination } from '@material-ui/lab';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';

const styles = theme => ({
    ul: {
        alignItems: 'center',
    },
});

class SearchView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            experts : [],
            expertsToView:[],
            expertsPerPage: 9,
            numOfPages:1,
            isFiltered:false
        }
    }
    onChange = (e)=>{
        e.preventDefault();
        const input = e.target.value;
        this.filterItems(input);
        }
    onSubmit = (e)=>{
        e.preventDefault();
        const input = e.target[0].value;
        this.filterItems(input);
    }
    filterItems = (input)=>{this.setState((prevState)=>{
            const expertsToView = prevState.experts.filter((expert)=>{
                const keys = Object.keys(expert);
                for(const key of keys){
                    if(expert[key].toLowerCase().includes(input.toLowerCase())){
                        return true;
                    }
                }
            });
            return ({
                experts:prevState.experts,
                expertsToView:input?expertsToView:expertsToView.slice(0,prevState.expertsPerPage),
                expertsPerPage:prevState.expertsPerPage,
                isFiltered: input ? true : false
            })
        })

    }
    handlePagination = (event,page)=>{
        this.setState((prevState)=>{
            const expertsToView = prevState.experts.slice((page-1)*prevState.expertsPerPage,(page-1)*prevState.expertsPerPage + prevState.expertsPerPage);
            return ({
                experts:prevState.experts,
                expertsToView:expertsToView,
                expertsPerPage:prevState.expertsPerPage

            })
        })
    }

    componentDidMount() {
        fetch('/test/users.json')
            .then(res => res.json())
            .then(
                (data) => {
                this.setState((prevState)=>{
                    return(
                        {
                            experts: data.experts,
                            expertsToView: data.experts.slice(0,prevState.expertsPerPage),
                            expertsPerPage: prevState.expertsPerPage,
                            numOfPages: Math.ceil(data.experts.length/prevState.expertsPerPage)
                        }
                )

                });
            },
                (error)=>{
                    console.log(error);
                });
    }
    render(){
        const {classes} = this.props;
        return(
        <>
            <Search onChange={this.onChange} onSubmit={this.onSubmit}></Search>
            <ProfileList items={this.state.expertsToView}></ProfileList>
            <Grid container justify = "center">
                {!this.state.isFiltered ? <Pagination count={this.state.numOfPages} onChange={this.handlePagination}/> : null}

            </Grid>
        </>)
    }
}
export default withStyles(styles)(SearchView)