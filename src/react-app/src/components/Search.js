import React from 'react';
import InputBase from '@material-ui/core/InputBase';
import Paper from '@material-ui/core/Paper';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import { withStyles } from '@material-ui/core/styles';
import { Link } from 'react-router-dom'


const styles = theme => ({
    root: {
        padding: '2px 4px',
        display: 'flex',
        alignItems: 'center',
        width: "90%",
        maxWidth: "1024px",
        margin: "40px auto",
        height: 70,
    },
    input: {
        marginLeft: theme.spacing(1),
        flex: 1,
    },
    iconButton: {
        padding: 10,
    },

});

export class Search extends React.Component{
    constructor(props) {
        super(props);
    }

    render(){
        const {classes} = this.props;
        return (
            <>
                <Paper component="form" className={classes.root} onSubmit={this.props.onSubmit}>
                        <InputBase
                            className={classes.input}
                            placeholder="Wpisz szukaną frazę"
                            inputProps={{'label': 'Wpisz szukaną frazę'}}
                            onChange={this.props.onChange}
                        />
                        {this.props.isInHome ? (<IconButton component={Link} to="/app/search" type="submit" className={classes.iconButton} aria-label="search">
                            <SearchIcon/>
                        </IconButton>):(<IconButton type="submit" className={classes.iconButton} aria-label="search">
                                <SearchIcon/>
                            </IconButton>)}

                </Paper>

            </>
        )
    }

};

export default withStyles(styles)(Search);
