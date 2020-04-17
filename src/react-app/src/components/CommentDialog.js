import React from "react";

// Components
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import TextField from "@material-ui/core/TextField";
import Box from "@material-ui/core/Box";

export class CommentDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            comment: ""
        };
    }

    onCommentValueChanged(value) {
        this.setState(prevState => {
            return{
                ...prevState,
                comment: value
            }
        })
    }

    onConfirm() {
        if (this.props.onConfirm != null) {
            this.props.onConfirm(this.state.comment);
        }

        this.setState(prevState => {
            return{
                ...prevState,
                comment: ""
            }
        })
    }

    onCancel() {
        if (this.props.onCancel != null) {
            this.props.onCancel();
        }

        this.setState(prevState => {
            return{
                ...prevState,
                comment: ""
            }
        })
    }

    render() {
        return (
            <Dialog open={this.props.visibility} onClose={()=>this.onCancel()}>
                <DialogTitle>Komentarz</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Wpisz komentarz co do jakości wykonania usługi przez specjalistę. Komentarz pojawi się na profilu specjalisty.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        fullWidth
                        multiline
                        rowsMax="5"
                        value={this.state.comment}
                        onChange={(event) => this.onCommentValueChanged(event.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>this.onCancel()} color="primary">
                        Anuluj
                    </Button>
                    <Button disabled={this.state.comment === ""} onClick={()=>this.onConfirm()} color="primary">
                        Potwierdź
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}
