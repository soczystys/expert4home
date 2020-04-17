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

// TODO auto fill contact
export class CreateOrderDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            timestamp: this.getDefaultTimestamp(),
            contact: "",
            description: ""
        };
    }

    validateValues() {
        let timestamp = new Date(this.state.timestamp);
        let fiveMinutesFromNowTimestamp = new Date(new Date().getTime() + 5 * 60 * 1000);

        return this.state.timestamp !== "" &&
            (timestamp >= fiveMinutesFromNowTimestamp) &&
            this.state.contact !== "";
    }

    getDefaultTimestamp() {
        let tomorrowTimestamp = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
        tomorrowTimestamp.setHours(12, 0);
        return tomorrowTimestamp.getFullYear() + "-" +
            (tomorrowTimestamp.getMonth() + 1 > 9 ? "" : "0") + (tomorrowTimestamp.getMonth() + 1) + "-" +
            (tomorrowTimestamp.getDate() > 9 ? "" : "0") + tomorrowTimestamp.getDate() + "T" +
            (tomorrowTimestamp.getHours() > 9 ? "" : "0") + tomorrowTimestamp.getHours() + ":" +
            (tomorrowTimestamp.getMinutes() > 9 ? "" : "0") + tomorrowTimestamp.getMinutes();
    }

    onTimestampValueChanged(value) {
        this.setState(prevState => {
            return{
                ...prevState,
                timestamp: value
            }
        })
    }

    onContactValueChanged(value) {
        this.setState(prevState => {
            return{
                ...prevState,
                contact: value
            }
        })
    }

    onDescriptionValueChanged(value) {
        this.setState(prevState => {
            return{
                ...prevState,
                description: value
            }
        })
    }

    onConfirm() {
        if (this.props.onConfirm != null) {
            this.props.onConfirm({
                timestamp: new Date(this.state.timestamp),
                contact: this.state.contact,
                description: this.state.description
            });
        }

        this.setState(prevState => {
            return{
                ...prevState,
                timestamp: this.getDefaultTimestamp(),
                contact: "",
                description: ""
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
                timestamp: this.getDefaultTimestamp(),
                contact: "",
                description: ""
            }
        })
    }

    render() {
        return (
            <Dialog open={this.props.visibility} onClose={()=>this.onCancel()}>
                <DialogTitle>Nowe zlecenie</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Uzupełnij dane zlecenia. Po naciśnięciu przycisku "potwierdź" zostanie ono wysłane do specjalisty.
                        Możesz odnaleźć je później w zakładce "zlecenia".
                    </DialogContentText>
                    <Box display="flex" flexDirection="row">
                        <Box flexGrow={2}>
                            <TextField
                                fullWidth
                                type="datetime-local"
                                label="Termin"
                                value={this.state.timestamp}
                                onChange={(event) => this.onTimestampValueChanged(event.target.value)}
                            />
                        </Box>
                        <Box flexGrow={1}>
                        </Box>
                        <Box flexGrow={2}>
                            <TextField
                                fullWidth
                                label="Kontakt"
                                value={this.state.contact}
                                onChange={(event) => this.onContactValueChanged(event.target.value)}
                            />
                        </Box>
                    </Box>
                    <TextField
                        fullWidth
                        multiline
                        rowsMax="5"
                        label="Opis zlecenia"
                        value={this.state.description}
                        onChange={(event) => this.onDescriptionValueChanged(event.target.value)}
                        style={{marginTop: 16}}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>this.onCancel()} color="primary">
                        Anuluj
                    </Button>
                    <Button disabled={!this.validateValues()} onClick={()=>this.onConfirm()} color="primary">
                        Potwierdź
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}
