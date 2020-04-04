import React from "react";
import {UserType} from "./Root";

// Components
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import Box from "@material-ui/core/Box";
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

// Icons
import CheckIcon from '@material-ui/icons/Check';
import ClearIcon from '@material-ui/icons/Clear';
import ChatBubbleOutlineIcon from '@material-ui/icons/ChatBubbleOutline';

// Styles
import clsx from "clsx";
import withStyles from "@material-ui/core/styles/withStyles";
import {red, blue, purple} from "@material-ui/core/colors";

const styles = theme => ({
    OrderCard: {
        marginBottom: 12,
        '&:last-of-type': {
            marginBottom: 0
        }
    },
    OrderCardDescription: {
        maxHeight: 180,
        padding: 4,
        marginTop: 4,
        border: "solid 1px rgba(0,0,0,.08)",
        borderRadius: 4,
        overflowY: "auto",
        [theme.breakpoints.down('sm')]: {
            maxHeight: 140
        }
    },
    OrderCardComment: {
        maxHeight: 90,
        padding: 4,
        marginTop: 4,
        border: "solid 1px rgba(0,0,0,.08)",
        borderRadius: 4,
        overflowY: "auto",
        [theme.breakpoints.down('sm')]: {
            maxHeight: 70
        }
    },
    OrderCardControls: {
        display: "flex",
        [theme.breakpoints.down('sm')]: {
            marginLeft: "67%",
            borderTopLeftRadius: 4,
            overflow: "hidden"
        },
        [theme.breakpoints.down("xs")]: {
            marginLeft: "0"
        }
    },

    orderCardControlsButton: {
        borderRadius: 0,
        [theme.breakpoints.down('sm')]: {
            height: 35,
            flexGrow: 1
        },
        [theme.breakpoints.up('md')]: {
            width: 50,
            height: "100%"
        }
    },
    orderCardControlsButtonAccept: {
        color: theme.palette.getContrastText(blue[500]),
        backgroundColor: blue[500],
        '&:hover': {
            backgroundColor: blue[700],
        }
    },
    orderCardControlsButtonReject: {
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

const OrderState = {
    CREATED: 'CREATED',
    ACCEPTED: 'ACCEPTED',
    REJECTED: 'REJECTED',
    COMPLETED: 'COMPLETED',
    COMMENTED: 'COMMENTED'
};

export class OrdersView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userType: UserType.NONE,
            orders: [],
            comment: "",
            commentedOrder: null,
            isCommentDialogVisible: false
        };
    }

    componentDidMount() {
        // TODO call api
        fetch('/test/orders.json')
            .then(res => res.json())
            .then((data) => {
                this.setState({
                    userType: data.userType,
                    orders: data.orders,
                    comment: "",
                    commentedOrder: null,
                    isCommentDialogVisible: false
                })
            })
            .catch(console.log)
    }

    onAcceptOrder(order) {
        order.state = OrderState.ACCEPTED;
        this.forceUpdate()

        // TODO call api
    }

    onRejectOrder(order) {
        order.state = OrderState.REJECTED;
        this.forceUpdate()

        // TODO call api
    }

    onCommentOrder(order) {
        this.setState(prevState => {
            return{
                ...prevState,
                commentedOrder: order
            }
        });

        this.onCommentDialogOpen()
    }

    onCommentDialogOpen() {
        this.setState(prevState => {
            return{
                ...prevState,
                isCommentDialogVisible: true
            }
        })
    }

    onCommentDialogValueChanged(value) {
        this.setState(prevState => {
            return{
                ...prevState,
                comment: value
            }
        })
    }

    onCommentDialogClose(confirmed) {
        if (confirmed) {
            this.state.commentedOrder.state = OrderState.COMMENTED;
            this.state.commentedOrder.comment = this.state.comment;
            this.forceUpdate()

            // TODO call api
        }

        this.setState(prevState => {
            return{
                ...prevState,
                isCommentDialogVisible: false
            }
        });
    }

    render() {
        const { classes } = this.props;
        return [
            this.renderCommentDialog(classes),
            this.state.orders.map((order) => {
                return order.state === OrderState.REJECTED ? null :
                    <Card className={classes.OrderCard}>
                        <div style={{ width: '100%' }}>
                            <Box display={{ xs: 'none', md: 'flex' }}>
                                <Box p={1}>
                                    <Avatar src={order.avatar}/>
                                </Box>
                                <Box p={1} flexGrow={1}>
                                    {this.renderOrderCardDescription(classes, order)}
                                </Box>
                                {this.renderOrderCardControls(classes, order)}
                            </Box>
                            <Box display={{ xs: 'flex', md: 'none' }} flexDirection="column">
                                <Box display="flex">
                                    <Box p={1}>
                                        <Avatar src={order.avatar}/>
                                    </Box>
                                    <Box p={1} flexGrow={1}>
                                        {this.renderOrderCardDescription(classes, order)}
                                    </Box>
                                </Box>
                                {this.renderOrderCardControls(classes, order)}
                            </Box>
                        </div>
                    </Card>
                }
            )
        ];
    }

    renderOrderCardDescription(classes, order) {
        return (
            <Box display="flex" flexDirection="column">
                <Box display="flex">
                    <span>
                    {
                        new Intl.DateTimeFormat("pl-PL", {
                            year: "numeric",
                            month: "long",
                            day: "2-digit",
                            hour: "numeric",
                            minute: "numeric"
                        }).format(new Date(order.timestamp))
                    }
                    </span>
                    <Box flexGrow={1}/>
                    <span style={{color: "rgba(0,0,0,.51)"}}>
                        {
                            order.state === OrderState.CREATED && "Utworzone"
                        }
                        {
                            order.state === OrderState.ACCEPTED && "Zaakceptowane"
                        }
                        {
                            order.state === OrderState.REJECTED && "Odrzucone"
                        }
                        {
                            (order.state === OrderState.COMPLETED || order.state === OrderState.COMMENTED) && "Zakończone"
                        }
                    </span>
                </Box>
                <span>{order.contact}</span>
                <Box className={classes.OrderCardDescription}>
                    {
                        order.description.split('\n').map(function(item, key) {
                            return (
                                <span key={key}>
                                    {item}
                                    <br/>
                                </span>
                            )
                        })
                    }
                </Box>
                {
                    order.comment !== "" &&
                    <Box className={classes.OrderCardComment}>
                        {
                            order.comment.split('\n').map(function(item, key) {
                            return (
                                <span key={key}>
                                    {item}
                                    <br/>
                                </span>
                                )
                            })
                        }
                    </Box>
                }
            </Box>
        );
    }

    renderOrderCardControls(classes, order) {
        return (
            <Box className={classes.OrderCardControls}>
                {
                    this.state.userType === UserType.EXPERT && order.state === OrderState.CREATED &&
                    <Button

                        className={clsx(classes.orderCardControlsButton, classes.orderCardControlsButtonAccept)}
                        disableElevation
                        onClick={() => this.onAcceptOrder(order)}>
                        <CheckIcon/>
                    </Button>
                }
                {
                    this.state.userType === UserType.EXPERT && order.state === OrderState.CREATED &&
                    <Button
                        className={clsx(classes.orderCardControlsButton, classes.orderCardControlsButtonReject)}
                        disableElevation
                        onClick={() => this.onRejectOrder(order)}>
                        <ClearIcon/>
                    </Button>
                }
                {
                    this.state.userType === UserType.CLIENT && order.state === OrderState.COMPLETED &&
                    <Button
                        className={clsx(classes.orderCardControlsButton, classes.orderCardControlsButtonComment)}
                        disableElevation
                        onClick={() => this.onCommentOrder(order)}>
                        <ChatBubbleOutlineIcon/>
                    </Button>
                }
            </Box>
        );
    }

    renderCommentDialog(classes) {
        return (
            <Dialog open={this.state.isCommentDialogVisible} onClose={()=>this.onCommentDialogClose(false)}>
                <DialogTitle>Komentarz</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Wpisz komentarz co do jakości wykonania zlecenia przez specjalistę. Komentarz pojawi się na profilu specjalisty.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        fullWidth
                        multiline
                        rowsMax="5"
                        value={this.state.comment}
                        onChange={(event) => this.onCommentDialogValueChanged(event.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>this.onCommentDialogClose(false)} color="primary">
                        Anuluj
                    </Button>
                    <Button onClick={()=>this.onCommentDialogClose(true)} color="primary">
                        Potwierdź
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}

export default withStyles(styles, { withTheme: true })(OrdersView);