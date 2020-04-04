import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuIcon from '@material-ui/icons/Menu';
import IconButton from "@material-ui/core/IconButton";
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import ListAltIcon from '@material-ui/icons/ListAlt';
import PersonOutlineIcon from '@material-ui/icons/PersonOutline';
import SearchIcon from '@material-ui/icons/Search';
import clsx from "clsx";
import {UserType} from "../views/Root";

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        marginLeft: -drawerWidth,
    },
    contentShift: {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        [theme.breakpoints.up('sm')]: {
            marginLeft: 0
        }
    },
    toolbar: theme.mixins.toolbar,
}));

function ListItemLink(props) {
    return <ListItem button component="a" {...props} />;
}

function NavigationOptionList(props) {
    if (props.userType === UserType.CLIENT) {
        return (
            <List>
                <ListItemLink button href={"/app/search"}>
                    <ListItemIcon><SearchIcon/></ListItemIcon>
                    <ListItemText primary={"Specjaliści"}/>
                </ListItemLink>
                <ListItemLink button href={"/app/orders"}>
                    <ListItemIcon><ListAltIcon/></ListItemIcon>
                    <ListItemText primary={"Zlecenia"}/>
                </ListItemLink>
                <Divider/>
                <ListItemLink button href={"/logout"}>
                    <ListItemIcon><ExitToAppIcon/></ListItemIcon>
                    <ListItemText primary={"Wyloguj"}/>
                </ListItemLink>
            </List>
        );
    } else if (props.userType === UserType.EXPERT) {
        return (
            <List>
                <ListItemLink button href={"/app/profile"}>
                    <ListItemIcon><PersonOutlineIcon/></ListItemIcon>
                    <ListItemText primary={"Profil"}/>
                </ListItemLink>
                <ListItemLink button href={"/app/orders"}>
                    <ListItemIcon><ListAltIcon/></ListItemIcon>
                    <ListItemText primary={"Zlecenia"}/>
                </ListItemLink>
                <Divider/>
                <ListItemLink button href={"/logout"}>
                    <ListItemIcon><ExitToAppIcon/></ListItemIcon>
                    <ListItemText primary={"Wyloguj"}/>
                </ListItemLink>
            </List>
        );
    }

    return <List></List>;
}

// TODO add elevation to Drawer
export default function Navigation(props) {
    const classes = useStyles();
    const [isDrawerOpen, setIsDrawerOpen] = React.useState(true);

    const switchDrawer = () => {
        setIsDrawerOpen(!isDrawerOpen);
    };

    return (
        <div className={classes.root}>
            <CssBaseline/>
            <AppBar position="fixed" className={classes.appBar}>
                <Toolbar>
                    <IconButton
                        className={classes.menuButton}
                        color="inherit"
                        edge="start"
                        onClick={switchDrawer}
                    >
                        <MenuIcon/>
                    </IconButton>
                    <Typography variant="h6" noWrap>
                        {props.title}
                    </Typography>
                </Toolbar>
            </AppBar>
            <Drawer
                variant="persistent"
                open={isDrawerOpen}
                className={classes.drawer}
                display={{md: 'block', lg: 'none'}}
                classes={{
                    paper: classes.drawerPaper,
                }}
            >
                <div className={classes.toolbar}/>
                <NavigationOptionList userType={props.userType}/>
            </Drawer>
            <main className={clsx(classes.content, {[classes.contentShift]: isDrawerOpen})}>
                <div className={classes.toolbar}/>
                {props.content}
            </main>
        </div>
    );
}
