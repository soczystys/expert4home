import React from 'react';
import InputBase from '@material-ui/core/InputBase';
import {makeStyles} from "@material-ui/core/styles";
import Paper from '@material-ui/core/Paper';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
;
const useStyles = makeStyles(theme => ({
    root: {
        padding: '2px 4px',
        display: 'flex',
        alignItems: 'center',
        width: "90%",
        maxWidth:"1024px",
        margin:"40px auto",
        height:70,
    },
    input: {
        marginLeft: theme.spacing(1),
        flex: 1,
    },
    iconButton: {
        padding: 10,
    },

}));

const Search = () => {
    const classes = useStyles();
    return (
        <>
            <Paper component="form" className={classes.root}>
                <InputBase
                    className={classes.input}
                    placeholder="Wpisz szukaną frazę"
                    inputProps={{'label': 'Wpisz szukaną frazę'}}
                />
                <IconButton type="submit" className={classes.iconButton} aria-label="search">
                    <SearchIcon/>
                </IconButton>

            </Paper>

        </>
    )
};

export default Search;