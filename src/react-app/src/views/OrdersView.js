import React from "react";
import {UserType} from "./Root";
import {makeStyles} from '@material-ui/core/styles';
import Card from "@material-ui/core/Card";
import Avatar from "@material-ui/core/Avatar";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import {red} from "@material-ui/core/colors";
import clsx from "clsx";
import Button from "@material-ui/core/Button";
import blue from "@material-ui/core/colors/blue";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import indigo from "@material-ui/core/colors/indigo";
import ChatBubbleOutlineIcon from '@material-ui/icons/ChatBubbleOutline';
import CheckIcon from '@material-ui/icons/Check';
import ClearIcon from '@material-ui/icons/Clear';
import purple from "@material-ui/core/colors/purple";

const theme = createMuiTheme({
    palette: {
        primary: indigo,
    },
});

const useStyles = makeStyles({
    orderCard: {
        marginBottom: 16
    },
    orderCardAvatar: {
        margin: 8
    },
    orderCardContent: {
        marginTop: 8,
        marginBottom: 8
    },
    orderCardDescription: {
        marginTop: 4,
        padding: 4
    },
    orderCardControls: {
        height: "100%",
        marginLeft: 8
    },
    orderCardControlsButton: {
        width: 50,
        borderRadius: 0
    },
    orderCardControlsButtonAccept: {
        color: theme.palette.getContrastText(blue[500]),
        backgroundColor: blue[500],
        '&:hover': {
            backgroundColor: blue[700],
        }
    },
    orderCardControlsButtonDismiss: {
        color: theme.palette.getContrastText(red[500]),
        backgroundColor: red[500],
        '&:hover': {
            backgroundColor: red[700],
        }
    },
    orderCardControlsButtonComment: {
        color: theme.palette.getContrastText(purple[500]),
        backgroundColor: purple[500],
        '&:hover': {
            backgroundColor: purple[700],
        }
    }
});

export default function OrdersView() {
    const classes = useStyles();

    // TODO get user type from api
    let userType = UserType.CLIENT;

    // TODO get order list from api
    let orderList = [
        {
            avatar: "https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            timestamp: new Date(2020, 2, 15, 12, 37, 15),
            contact: "jan.kowalski@gmail.com",
            description: "Opis oferty Jana Kowalskiego"
        },
        {
            avatar: "https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            timestamp: new Date(2020, 1, 8, 17, 58, 10),
            contact: "+48 606 745 235",
            description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed pulvinar elit eget erat " +
                "sollicitudin vulputate. Sed sapien elit, efficitur a mi nec, imperdiet pharetra leo. Orci varius " +
                "natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam tempor urna " +
                "nulla, vitae euismod metus ultrices in. Praesent eget fringilla dolor. Nulla facilisi. Morbi " +
                "eleifend, turpis eu aliquet ullamcorper, odio mi maximus nisl, non consectetur enim velit at odio. " +
                "Vestibulum vestibulum, tellus eget varius iaculis, mi enim posuere purus, non efficitur elit enim " +
                "vitae nisi. Nullam non eros felis. Nulla sed lacinia elit, ac ornare nisi. Mauris vitae nisl sed " +
                "orci pretium suscipit. Curabitur bibendum felis ac sollicitudin viverra."
        },
        {
            avatar: "https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            timestamp: new Date(2020, 2, 24, 10, 13, 48),
            contact: "agata.mydelko@gmail.com",
            description: "Opis oferty Agaty Mydełko"
        }
    ];

    return (
        orderList.map((order) =>
            <Card className={classes.orderCard}>
                <Grid
                    container
                    direction="row"
                    alignItems="stretch"
                >
                    <Avatar
                        className={classes.orderCardAvatar}
                        src={order.avatar}/>
                    <Grid item xs>
                        <Grid container direction="column" className={classes.orderCardContent}>
                            <span>
                                {new Intl.DateTimeFormat("pl-PL", {
                                    year: "numeric",
                                    month: "long",
                                    day: "2-digit",
                                    hour: "numeric",
                                    minute: "numeric"
                                }).format(order.timestamp)}
                            </span>
                            <span>{order.contact}</span>
                            <Paper variant="outlined" className={classes.orderCardDescription}>
                                {order.description}
                            </Paper>
                        </Grid>
                    </Grid>
                    <Grid item>
                        <Grid
                            className={classes.orderCardControls}
                            container
                            direction="row"
                        >
                            <Button
                                className={clsx(classes.orderCardControlsButton, classes.orderCardControlsButtonAccept)}
                                disableElevation>
                                <CheckIcon/>
                            </Button>
                            <Button
                                className={clsx(classes.orderCardControlsButton, classes.orderCardControlsButtonDismiss)}
                                disableElevation>
                                <ClearIcon/>
                            </Button>
                            <Button
                                className={clsx(classes.orderCardControlsButton, classes.orderCardControlsButtonComment)}
                                disableElevation>
                                <ChatBubbleOutlineIcon/>
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Card>
        ));
}
